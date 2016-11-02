package me.theyinspire.projects.sorting.execution.impl;

import me.theyinspire.projects.sorting.execution.*;
import me.theyinspire.projects.sorting.execution.api.Bean;
import me.theyinspire.projects.sorting.execution.api.Qualifier;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:57 PM)
 */
@SuppressWarnings("WeakerAccess")
public class DefaultApplicationContext implements ApplicationContext {

    private final Class<?> configuration;
    private final Map<Class<?>, List<BeanDefinition>> beans;

    public DefaultApplicationContext(Class<?> configuration) {
        this(configuration, getEnvironment());
    }

    private static ConfigurableEnvironment getEnvironment() {
        final ConfigurableEnvironment environment = new ConfigurableEnvironment();
        final Properties properties = System.getProperties();
        final Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            final String property = (String) propertyNames.nextElement();
            environment.setProperty(property, properties.getProperty(property));
        }
        return environment;
    }

    public DefaultApplicationContext(Class<?> configuration, Environment environment) {
        this.configuration = configuration;
        beans = new HashMap<>();
        load(environment);
    }

    private void load(Environment environment) {
        final Object configurationInstance;
        try {
            configurationInstance = configuration.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to instantiate configuration class: " + configuration);
        }
        final List<RawBean> definitions = new ArrayList<>(collectDerivedDefinitions(configurationInstance));
        definitions.add(0, new PredefinedRawBean(environment));
        definitions.add(0, new PredefinedRawBean(this));
        while (!definitions.isEmpty()) {
            DependencyResolution dependencyResolution = null;
            for (RawBean candidate : definitions) {
                boolean resolved = true;
                final List<BeanDefinition> resolutions = new ArrayList<>();
                for (BeanDependency dependency : candidate.getDependencies()) {
                    final BeanDefinition resolution = resolve(dependency);
                    if (resolution == null) {
                        resolved = false;
                        break;
                    } else {
                        resolutions.add(resolution);
                    }
                }
                if (resolved) {
                    dependencyResolution = new DependencyResolution(candidate, resolutions);
                    break;
                }
            }
            if (dependencyResolution == null) {
                throw new IllegalStateException("Circular dependency among beans: " + definitions.stream().map(RawBean::getName).collect(Collectors.toSet()));
            }
            final RawBean rawBean = dependencyResolution.getBean();
            definitions.remove(rawBean);
            final BeanFactory beanFactory = rawBean.getBeanCreator().getBeanFactory(dependencyResolution.getResolutions());
            final BeanFactory delegatingFactory;
            switch (rawBean.getScope()) {
                case PROTOTYPE:
                    delegatingFactory = new PrototypeBeanFactory(beanFactory);
                    break;
                case SINGLETON:
                    delegatingFactory = new SingletonBeanFactory(beanFactory);
                    break;
                default:
                    throw new IllegalStateException("Unknown bean scope: " + rawBean.getScope());
            }
            final BeanDefinition definition = new ImmutableBeanDefinition(rawBean.getName(), rawBean.getType(), delegatingFactory);
            if (!beans.containsKey(definition.getType())) {
                beans.put(definition.getType(), new ArrayList<>());
            }
            beans.get(definition.getType()).add(definition);
        }
    }

    private BeanDefinition resolve(BeanDependency dependency) {
        BeanDefinition value = null;
        for (Class<?> beanType : beans.keySet()) {
            if (dependency.getType().isAssignableFrom(beanType)) {
                for (BeanDefinition definition : beans.get(beanType)) {
                    if (dependency.getName() == null || dependency.getName().equals(definition.getName())) {
                        if (value == null) {
                            value = definition;
                        } else {
                            throw new IllegalArgumentException("Multiple beans satisfy dependency: [" + dependency.getName() + "," + dependency.getType() + "]");
                        }
                    }
                }
            }
        }
        return value;
    }

    private List<RawBean> collectDerivedDefinitions(Object configurationInstance) {
        return Arrays.stream(configuration.getMethods())
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .map(method -> new ImmutableRawBean(method.getName(), method.getReturnType(), method.getAnnotation(Bean.class).scope(), getDependencies(method), new DefaultBeanCreator(method, configurationInstance)))
                .collect(Collectors.toList());
    }

    private List<BeanDependency> getDependencies(Method method) {
        final List<BeanDependency> dependencies = new ArrayList<>();
        final Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            final Class<?> parameterType = parameterTypes[i];
            dependencies.add(new ImmutableBeanDependency(getQualifier(method, i), parameterType));
        }
        return dependencies;
    }

    private String getQualifier(Method method, int parameter) {
        return Arrays.stream(method.getParameterAnnotations()[parameter])
                .filter(annotation -> annotation.annotationType().equals(Qualifier.class))
                .map(annotation -> (Qualifier) annotation)
                .map(Qualifier::value)
                .findFirst().orElse(null);
    }

    @Override
    public <E> Map<String, E> getBeansOfType(Class<E> type) {
        final Map<String, E> map = new HashMap<>();
        //noinspection unchecked
        beans.keySet().stream().filter(type::isAssignableFrom).forEach(beanType -> {
            final List<BeanDefinition> definitions = beans.get(beanType);
            for (BeanDefinition definition : definitions) {
                //noinspection unchecked
                map.put(definition.getName(), (E) definition.getBeanFactory().getInstance());
            }
        });
        return map;
    }

    @Override
    public <E> E getBean(Class<E> type) {
        final Map<String, E> beans = getBeansOfType(type);
        if (beans.size() == 1) {
            return beans.values().iterator().next();
        } else if (beans.isEmpty()) {
            throw new IllegalArgumentException("No beans of this type were found: " + type);
        } else {
            throw new IllegalArgumentException(beans.size() + " beans of this type were found: " + beans.keySet());
        }
    }

    @Override
    public <E> E getBean(Class<E> type, String name) {
        final Map<String, E> beans = getBeansOfType(type);
        if (beans.isEmpty() || !beans.containsKey(name)) {
            throw new IllegalArgumentException("No beans of this type were found: " + type);
        } else {
            return beans.get(name);
        }
    }

    private static class DependencyResolution {

        private final RawBean bean;
        private final List<BeanDefinition> resolutions;

        private DependencyResolution(RawBean bean, List<BeanDefinition> resolutions) {
            this.bean = bean;
            this.resolutions = resolutions;
        }

        public RawBean getBean() {
            return bean;
        }

        public List<BeanDefinition> getResolutions() {
            return resolutions;
        }

    }

}

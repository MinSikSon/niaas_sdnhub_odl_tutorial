/*
* Generated file
*
* Generated from: yang module name: netconf-exercise-impl yang module local name: netconf-exercise-impl
* Generated by: org.opendaylight.controller.config.yangjmxgenerator.plugin.JMXGenerator
* Generated at: Tue Jun 21 13:58:44 KST 2016
*
* Do not modify this file unless it is present under src/main directory
*/
package org.sdnhub.odl.tutorial.netconf.exercise.impl;
@org.opendaylight.yangtools.yang.binding.annotations.ModuleQName(namespace = "urn:sdnhub:odl:tutorial:netconf-exercise:netconf-exercise-impl", name = "netconf-exercise-impl", revision = "2015-08-31")

public abstract class AbstractTutorialNetconfExerciseModuleFactory implements org.opendaylight.controller.config.spi.ModuleFactory {
    public static final java.lang.String NAME = "netconf-exercise-impl";

    private static final java.util.Set<Class<? extends org.opendaylight.controller.config.api.annotations.AbstractServiceInterface>> serviceIfcs;

    @Override
    public final String getImplementationName() {
        return NAME;
    }

    static {
        java.util.Set<Class<? extends org.opendaylight.controller.config.api.annotations.AbstractServiceInterface>> serviceIfcs2 = new java.util.HashSet<Class<? extends org.opendaylight.controller.config.api.annotations.AbstractServiceInterface>>();
        serviceIfcs = java.util.Collections.unmodifiableSet(serviceIfcs2);
    }

    @Override
    public final boolean isModuleImplementingServiceInterface(Class<? extends org.opendaylight.controller.config.api.annotations.AbstractServiceInterface> serviceInterface) {
        for (Class<?> ifc: serviceIfcs) {
            if (serviceInterface.isAssignableFrom(ifc)){
                return true;
            }
        }
        return false;
    }

    @Override
    public java.util.Set<Class<? extends org.opendaylight.controller.config.api.annotations.AbstractServiceInterface>> getImplementedServiceIntefaces() {
        return serviceIfcs;
    }

    @Override
    public org.opendaylight.controller.config.spi.Module createModule(String instanceName, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.osgi.framework.BundleContext bundleContext) {
        return instantiateModule(instanceName, dependencyResolver, bundleContext);
    }

    @Override
    public org.opendaylight.controller.config.spi.Module createModule(String instanceName, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.controller.config.api.DynamicMBeanWithInstance old, org.osgi.framework.BundleContext bundleContext) throws Exception {
        org.sdnhub.odl.tutorial.netconf.exercise.impl.TutorialNetconfExerciseModule oldModule = null;
        try {
            oldModule = (org.sdnhub.odl.tutorial.netconf.exercise.impl.TutorialNetconfExerciseModule) old.getModule();
        } catch(Exception e) {
            return handleChangedClass(old);
        }
        org.sdnhub.odl.tutorial.netconf.exercise.impl.TutorialNetconfExerciseModule module = instantiateModule(instanceName, dependencyResolver, oldModule, old.getInstance(), bundleContext);
        module.setBindingAwareBroker(oldModule.getBindingAwareBroker());
        module.setRpcRegistry(oldModule.getRpcRegistry());
        module.setDataBroker(oldModule.getDataBroker());
        module.setNotificationService(oldModule.getNotificationService());

        return module;
    }

    public org.sdnhub.odl.tutorial.netconf.exercise.impl.TutorialNetconfExerciseModule instantiateModule(String instanceName, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.sdnhub.odl.tutorial.netconf.exercise.impl.TutorialNetconfExerciseModule oldModule, java.lang.AutoCloseable oldInstance, org.osgi.framework.BundleContext bundleContext) {
        return new org.sdnhub.odl.tutorial.netconf.exercise.impl.TutorialNetconfExerciseModule(new org.opendaylight.controller.config.api.ModuleIdentifier(NAME, instanceName), dependencyResolver, oldModule, oldInstance);
    }

    public org.sdnhub.odl.tutorial.netconf.exercise.impl.TutorialNetconfExerciseModule instantiateModule(String instanceName, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.osgi.framework.BundleContext bundleContext) {
        return new org.sdnhub.odl.tutorial.netconf.exercise.impl.TutorialNetconfExerciseModule(new org.opendaylight.controller.config.api.ModuleIdentifier(NAME, instanceName), dependencyResolver);
    }

    public org.sdnhub.odl.tutorial.netconf.exercise.impl.TutorialNetconfExerciseModule handleChangedClass(org.opendaylight.controller.config.api.DynamicMBeanWithInstance old) throws Exception {
        throw new UnsupportedOperationException("Class reloading is not supported");
    }

    @Override
    public java.util.Set<org.sdnhub.odl.tutorial.netconf.exercise.impl.TutorialNetconfExerciseModule> getDefaultModules(org.opendaylight.controller.config.api.DependencyResolverFactory dependencyResolverFactory, org.osgi.framework.BundleContext bundleContext) {
        return new java.util.HashSet<org.sdnhub.odl.tutorial.netconf.exercise.impl.TutorialNetconfExerciseModule>();
    }

}

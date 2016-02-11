package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.netconf.exercise.netconf.exercise.impl.rev150831.modules.module.configuration;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.netconf.exercise.netconf.exercise.impl.rev150831.modules.module.configuration.netconf.exercise.impl.BindingAwareBroker;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.netconf.exercise.netconf.exercise.impl.rev150831.modules.module.configuration.netconf.exercise.impl.RpcRegistry;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.modules.module.Configuration;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.netconf.exercise.netconf.exercise.impl.rev150831.modules.module.configuration.netconf.exercise.impl.NotificationService;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.netconf.exercise.netconf.exercise.impl.rev150831.modules.module.configuration.netconf.exercise.impl.DataBroker;
import org.opendaylight.yangtools.yang.binding.Augmentable;


/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;netconf-exercise-impl&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/netconf-exercise.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * case netconf-exercise-impl {
 *     container binding-aware-broker {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (urn:sdnhub:odl:tutorial:netconf-exercise:netconf-exercise-impl?revision=2015-08-31)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 *     container data-broker {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (urn:sdnhub:odl:tutorial:netconf-exercise:netconf-exercise-impl?revision=2015-08-31)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 *     container notification-service {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (urn:sdnhub:odl:tutorial:netconf-exercise:netconf-exercise-impl?revision=2015-08-31)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 *     container rpc-registry {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (urn:sdnhub:odl:tutorial:netconf-exercise:netconf-exercise-impl?revision=2015-08-31)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;netconf-exercise-impl/modules/module/configuration/(urn:sdnhub:odl:tutorial:netconf-exercise:netconf-exercise-impl?revision=2015-08-31)netconf-exercise-impl&lt;/i&gt;
 *
 */
public interface NetconfExerciseImpl
    extends
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.netconf.exercise.netconf.exercise.impl.rev150831.modules.module.configuration.NetconfExerciseImpl>,
    Configuration
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.cachedReference(org.opendaylight.yangtools.yang.common.QName.create("urn:sdnhub:odl:tutorial:netconf-exercise:netconf-exercise-impl","2015-08-31","netconf-exercise-impl"));

    BindingAwareBroker getBindingAwareBroker();
    
    DataBroker getDataBroker();
    
    NotificationService getNotificationService();
    
    RpcRegistry getRpcRegistry();

}


package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.acl.impl.rev150722.modules.module.configuration;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.acl.impl.rev150722.modules.module.configuration.acl.impl.RpcRegistry;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.modules.module.Configuration;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.acl.impl.rev150722.modules.module.configuration.acl.impl.NotificationService;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.acl.impl.rev150722.modules.module.configuration.acl.impl.DataBroker;
import org.opendaylight.yangtools.yang.binding.Augmentable;


/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;acl-impl&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/acl-impl.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * case acl-impl {
 *     container data-broker {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (urn:sdnhub:odl:tutorial:acl:acl-impl?revision=2015-07-22)type {
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
 *             refine (urn:sdnhub:odl:tutorial:acl:acl-impl?revision=2015-07-22)type {
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
 *             refine (urn:sdnhub:odl:tutorial:acl:acl-impl?revision=2015-07-22)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;acl-impl/modules/module/configuration/(urn:sdnhub:odl:tutorial:acl:acl-impl?revision=2015-07-22)acl-impl&lt;/i&gt;
 *
 */
public interface AclImpl
    extends
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.acl.impl.rev150722.modules.module.configuration.AclImpl>,
    Configuration
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.cachedReference(org.opendaylight.yangtools.yang.common.QName.create("urn:sdnhub:odl:tutorial:acl:acl-impl","2015-07-22","acl-impl"));

    DataBroker getDataBroker();
    
    NotificationService getNotificationService();
    
    RpcRegistry getRpcRegistry();

}


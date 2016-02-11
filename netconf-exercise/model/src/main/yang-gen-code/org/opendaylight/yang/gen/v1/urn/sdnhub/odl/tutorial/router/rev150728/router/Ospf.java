package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router;
import java.util.List;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yangtools.yang.binding.Identifiable;


/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;router&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/router.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * list ospf {
 *     key "process-id"
 *     leaf process-id {
 *         type uint32;
 *     }
 *     list networks {
 *         key "subnet-ip"
 *         leaf subnet-ip {
 *             type string;
 *         }
 *         leaf area-id {
 *             type uint32;
 *         }
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;router/router/ospf&lt;/i&gt;
 *
 * &lt;p&gt;To create instances of this class use {@link org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.OspfBuilder}.
 * @see org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.OspfBuilder
 * @see org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.OspfKey
 *
 */
public interface Ospf
    extends
    ChildOf<Router>,
    Augmentable<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.Ospf>,
    Identifiable<OspfKey>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.cachedReference(org.opendaylight.yangtools.yang.common.QName.create("urn:sdnhub:odl:tutorial:router","2015-07-28","ospf"));

    java.lang.Long getProcessId();
    
    List<Networks> getNetworks();
    
    /**
     * Returns Primary Key of Yang List Type
     *
     */
    OspfKey getKey();

}


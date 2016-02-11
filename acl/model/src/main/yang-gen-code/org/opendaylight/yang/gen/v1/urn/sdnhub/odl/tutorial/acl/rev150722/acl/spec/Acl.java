package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.acl.spec;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.PortNumber;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yangtools.yang.binding.Identifiable;


/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;acl&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/acl.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * list acl {
 *     key "destination"
 *     leaf destination {
 *         type string;
 *     }
 *     leaf node {
 *         type leafref;
 *     }
 *     leaf ip-addr {
 *         type ipv4-prefix;
 *     }
 *     leaf port {
 *         type port-number;
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;acl/acl-spec/acl&lt;/i&gt;
 *
 * &lt;p&gt;To create instances of this class use {@link org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.acl.spec.AclBuilder}.
 * @see org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.acl.spec.AclBuilder
 * @see org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.acl.spec.AclKey
 *
 */
public interface Acl
    extends
    ChildOf<AclSpec>,
    Augmentable<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.acl.spec.Acl>,
    Identifiable<AclKey>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.cachedReference(org.opendaylight.yangtools.yang.common.QName.create("urn:sdnhub:odl:tutorial:acl","2015-07-22","acl"));

    java.lang.String getDestination();
    
    NodeId getNode();
    
    Ipv4Prefix getIpAddr();
    
    PortNumber getPort();
    
    /**
     * Returns Primary Key of Yang List Type
     *
     */
    AclKey getKey();

}


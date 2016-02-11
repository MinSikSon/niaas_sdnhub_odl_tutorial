package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;
import java.util.List;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.acl.spec.Acl;
import org.opendaylight.yangtools.yang.binding.Augmentable;


/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;acl&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/acl.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * container acl-spec {
 *     list acl {
 *         key "destination"
 *         leaf destination {
 *             type string;
 *         }
 *         leaf node {
 *             type leafref;
 *         }
 *         leaf ip-addr {
 *             type ipv4-prefix;
 *         }
 *         leaf port {
 *             type port-number;
 *         }
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;acl/acl-spec&lt;/i&gt;
 *
 * &lt;p&gt;To create instances of this class use {@link org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpecBuilder}.
 * @see org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpecBuilder
 *
 */
public interface AclSpec
    extends
    ChildOf<AclData>,
    Augmentable<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.cachedReference(org.opendaylight.yangtools.yang.common.QName.create("urn:sdnhub:odl:tutorial:acl","2015-07-22","acl-spec"));

    List<Acl> getAcl();

}


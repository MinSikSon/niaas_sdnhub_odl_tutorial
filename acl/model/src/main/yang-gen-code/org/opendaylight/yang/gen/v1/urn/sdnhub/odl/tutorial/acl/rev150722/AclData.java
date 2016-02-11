package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722;
import org.opendaylight.yangtools.yang.binding.DataRoot;


/**
 * ACL configuration
 *
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;acl&lt;/b&gt;
 * &lt;br&gt;Source path: &lt;i&gt;META-INF/yang/acl.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * module acl {
 *     yang-version 1;
 *     namespace "urn:sdnhub:odl:tutorial:acl";
 *     prefix "acl";
 *
 *     import opendaylight-inventory { prefix "inv"; }
 *     
 *     import yang-ext { prefix "ext"; }
 *     
 *     import ietf-inet-types { prefix "inet"; }
 *     
 *     import ietf-yang-types { prefix "yang"; }
 *     revision 2015-07-22 {
 *         description "ACL configuration
 *         ";
 *     }
 *
 *     container acl-spec {
 *         list acl {
 *             key "destination"
 *             leaf destination {
 *                 type string;
 *             }
 *             leaf node {
 *                 type leafref;
 *             }
 *             leaf ip-addr {
 *                 type ipv4-prefix;
 *             }
 *             leaf port {
 *                 type port-number;
 *             }
 *         }
 *     }
 * }
 * &lt;/pre&gt;
 *
 */
public interface AclData
    extends
    DataRoot
{




    AclSpec getAclSpec();

}


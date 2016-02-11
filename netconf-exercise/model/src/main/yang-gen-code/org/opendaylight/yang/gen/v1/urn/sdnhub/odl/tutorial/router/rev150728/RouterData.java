package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728;
import org.opendaylight.yangtools.yang.binding.DataRoot;
import java.util.List;


/**
 * Router configuration
 *
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;router&lt;/b&gt;
 * &lt;br&gt;Source path: &lt;i&gt;META-INF/yang/router.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * module router {
 *     yang-version 1;
 *     namespace "urn:sdnhub:odl:tutorial:router";
 *     prefix "router";
 *
 *     revision 2015-07-28 {
 *         description "Router configuration
 *         ";
 *     }
 *
 *     list interfaces {
 *         key "id"
 *         leaf id {
 *             type string;
 *         }
 *         leaf ip-address {
 *             type string;
 *         }
 *     }
 *     container router {
 *         list ospf {
 *             key "process-id"
 *             leaf process-id {
 *                 type uint32;
 *             }
 *             list networks {
 *                 key "subnet-ip"
 *                 leaf subnet-ip {
 *                     type string;
 *                 }
 *                 leaf area-id {
 *                     type uint32;
 *                 }
 *             }
 *         }
 *         list bgp {
 *             key "as-number"
 *             leaf as-number {
 *                 type uint32;
 *             }
 *             leaf router-id {
 *                 type string;
 *             }
 *             list neighbors {
 *                 key "as-number"
 *                 leaf as-number {
 *                     type uint32;
 *                 }
 *                 leaf peer-ip {
 *                     type string;
 *                 }
 *             }
 *         }
 *     }
 * }
 * &lt;/pre&gt;
 *
 */
public interface RouterData
    extends
    DataRoot
{




    List<Interfaces> getInterfaces();
    
    Router getRouter();

}


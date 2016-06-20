package org.sms.vendor.test;

import org.opendaylight.yangtools.yang.binding.DataContainer;
import org.opendaylight.yangtools.yang.common.QName;

/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;openflowplugin-extension-nicira-action&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/openflowplugin-extension-nicira-action.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * choice dst-choice {
 *     case dst-nx-arp-sha-case {
 *         leaf nx-arp-sha {
 *             type empty;
 *         }
 *     }
 *     case dst-nx-arp-tha-case {
 *         leaf nx-arp-tha {
 *             type empty;
 *         }
 *     }
 *     case dst-nx-nshc-1-case {
 *         leaf nx-nshc-1-dst {
 *             type empty;
 *         }
 *     }
 *     case dst-nx-nshc-2-case {
 *         leaf nx-nshc-2-dst {
 *             type empty;
 *         }
 *     }
 *     case dst-nx-nshc-3-case {
 *         leaf nx-nshc-3-dst {
 *             type empty;
 *         }
 *     }
 *     case dst-nx-nshc-4-case {
 *         leaf nx-nshc-4-dst {
 *             type empty;
 *         }
 *     }
 *     case dst-nx-nsi-case {
 *         leaf nx-nsi-dst {
 *             type empty;
 *         }
 *     }
 *     case dst-nx-nsp-case {
 *         leaf nx-nsp-dst {
 *             type empty;
 *         }
 *     }
 *     case dst-nx-reg-case {
 *         leaf nx-reg {
 *             type identityref;
 *         }
 *     }
 *     case dst-nx-tun-id-case {
 *         leaf nx-tun-id {
 *             type empty;
 *         }
 *     }
 *     case dst-nx-tun-ipv4-dst-case {
 *         leaf nx-tun-ipv4-dst {
 *             type empty;
 *         }
 *     }
 *     case dst-nx-tun-ipv4-src-case {
 *         leaf nx-tun-ipv4-src {
 *             type empty;
 *         }
 *     }
 *     case dst-of-arp-op-case {
 *         leaf of-arp-op {
 *             type empty;
 *         }
 *     }
 *     case dst-of-arp-spa-case {
 *         leaf of-arp-spa {
 *             type empty;
 *         }
 *     }
 *     case dst-of-arp-tpa-case {
 *         leaf of-arp-tpa {
 *             type empty;
 *         }
 *     }
 *     case dst-of-eth-dst-case {
 *         leaf of-eth-dst {
 *             type empty;
 *         }
 *     }
 *     case dst-of-eth-src-case {
 *         leaf of-eth-src {
 *             type empty;
 *         }
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;openflowplugin-extension-nicira-action/dst-choice-grouping/dst-choice&lt;/i&gt;
 *
 */
public interface SMS_DstChoice 
	extends 
	DataContainer 
{
	public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName
			.cachedReference(org.opendaylight.yangtools.yang.common.QName
					.create("urn:opendaylight:openflowplugin:extension:nicira:action", "2014-07-14", "dst-choice"));
}




  


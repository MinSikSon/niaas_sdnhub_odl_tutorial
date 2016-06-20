package org.sms.vendor.test;

import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;
/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;openflowplugin-extension-nicira-action&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/openflowplugin-extension-nicira-action.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * grouping nxm-nx-reg-grouping {
 *     leaf nx-reg {
 *         type identityref;
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;openflowplugin-extension-nicira-action/nxm-nx-reg-grouping&lt;/i&gt;
 *
 */
public interface SMS_RegGrouping 
 extends DataObject 
 {
	public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName
			.cachedReference(org.opendaylight.yangtools.yang.common.QName.create(
					"urn:opendaylight:openflowplugin:extension:nicira:action", "2014-07-14", "nxm-nx-reg-grouping"));

	java.lang.Class<? extends org.sms.vendor.test.SMS_Reg> getSMS_Reg();
}

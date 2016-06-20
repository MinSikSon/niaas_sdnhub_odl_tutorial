package org.sms.vendor.test;

import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;

/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;openflowplugin-extension-nicira-action&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/openflowplugin-extension-nicira-action.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * case dst-nx-reg-case {
 *     leaf nx-reg {
 *         type identityref;
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;openflowplugin-extension-nicira-action/dst-choice-grouping/dst-choice/dst-nx-reg-case&lt;/i&gt;
 *
 */

public interface SMS_RegCase
	extends 
	DataObject,
	Augmentable<org.sms.vendor.test.SMS_RegCase>, 
	SMS_RegGrouping,
	SMS_DstChoice
{

	public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName
			.cachedReference(org.opendaylight.yangtools.yang.common.QName.create(
					"urn:opendaylight:openflowplugin:extension:nicira:action", "2014-07-14", "dst-nx-reg-case"));

}

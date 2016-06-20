package org.sms.vendor.test;

import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;


/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;openflowplugin-extension-nicira-action&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/openflowplugin-extension-nicira-action.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * grouping range-grouping {
 *     leaf start {
 *         type uint16;
 *     }
 *     leaf end {
 *         type uint16;
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;openflowplugin-extension-nicira-action/range-grouping&lt;/i&gt;
 *
 */
public interface SMS_RangeGrouping
    extends
    DataObject
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.cachedReference(org.opendaylight.yangtools.yang.common.QName.create("urn:opendaylight:openflowplugin:extension:nicira:action","2014-07-14","range-grouping"));

    /**
     * Include value.
     *
     */
    java.lang.Integer getStart();
    
    /**
     * Include value.
     *
     */
    java.lang.Integer getEnd();

}


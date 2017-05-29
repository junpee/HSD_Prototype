/*
 * Automated web application generator
 *
 * Licensed Materials - Property of IBM
 * "Restricted Materials of IBM"
 * IPSC : 6949-63S
 * (C) Copyright IBM Japan, Ltd. 2016 All Rights Reserved.
 * (C) Copyright IBM Corp. 2016 All Rights Reserved.
 * US Government Users Restricted Rights -
 * Use, duplication or disclosure restricted
 * by GSA ADP Schedule Contract with IBM Corp.
 *
 */
package com.ibm.jp.awag2.generator.model.java;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * IntegerのJAXB Adapterクラス。
 *
 */
public class IntegerAdapter extends XmlAdapter<String, Integer> {

	/* (非 Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public String marshal(Integer v) throws Exception {
		if (v != null) {
			return String.valueOf(v);
		} else {
			return null;
		}
	}

	/* (非 Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public Integer unmarshal(String v) throws Exception {
		if (v != null & !v.isEmpty()) {
			return Integer.valueOf(v);
		} else {
			return null;
		}
	}

}

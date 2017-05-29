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
package com.ibm.jp.awag2.common.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * InputDTO・OutputDTOの基底クラス。
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public abstract class DTOBase {
	
	/** AngularJSで使用するID */
	private String id;
	
	/**
	 * AngularJS用のIDを取得する。
	 * 
	 * @return AngularJS用ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * AngularJS用のIDを設定する。
	 * 
	 * @param id
	 *            AngularJS用ID
	 */
	public void setId(String id) {
		this.id = id;
	}	
}

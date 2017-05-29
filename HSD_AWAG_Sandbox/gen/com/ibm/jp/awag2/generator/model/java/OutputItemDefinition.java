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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * API定義の出力項目を表すクラス。
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OutputItemDefinition  extends ItemDefinition {

	/** 従属項目のリスト **/
	@XmlElement(name = "item")
	protected List<OutputItemDefinition> itemDefinitions;
	
	@Override
	public List<? extends ItemDefinition> getItemDefinitions() {
		return this.itemDefinitions;
	}

	@Override
	public void setItemDefinitions(List<? extends ItemDefinition> outputItemDefinitions) {
		this.itemDefinitions = (List<OutputItemDefinition>) outputItemDefinitions;
	}

}

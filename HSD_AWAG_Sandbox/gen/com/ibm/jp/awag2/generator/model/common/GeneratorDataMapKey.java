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
package com.ibm.jp.awag2.generator.model.common;

/**
 * AWAG定義情報の種別を表すenumクラス。
 *
 */
public enum GeneratorDataMapKey {
	INPUT_DATA_GENERATOR_VERSION("inputDataGeneratorVersion"),
	PROJECT("project"),
	USECASE("usecase"),
	SCREEN("screen"),
	EVENT("event"),
	API("apiDefinition"),
	API_ITEMS("itemDefinitions"),
	API_ITEMNAME("itemName"),
	TABLE_CATEGORY("tableCategory"),
	TABLE("tableDefinition");

	private String key;
	
	private GeneratorDataMapKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
}

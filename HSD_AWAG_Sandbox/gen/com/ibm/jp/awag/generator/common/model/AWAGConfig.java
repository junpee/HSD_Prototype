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
package com.ibm.jp.awag.generator.common.model;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * AutomatedWebAppGeneratorの設定ファイルを示すクラス。
 * JAXBでXMLからオブジェクトへ設定値をマップする。
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "config")
public class AWAGConfig {
	
	/** AWAG Version */
	@XmlElement(name = "version")
	private String version;

	/** FactoryClassName */
	@XmlElement(name = "factoryClassName")
	private String factoryClassName;

	@XmlTransient
	private GeneratorConfig generatorConfig;
	
	@XmlTransient
	private Map<String, Object> optionMap;
	
	public String getVersion() {
		return version;
	}
	
	public String getFactoryClassName() {
		return factoryClassName;
	}

	public void setFactoryClassName(String factoryClassName) {
		this.factoryClassName = factoryClassName;
	}

	public GeneratorConfig getGeneratorConfig() {
		return generatorConfig;
	}

	public void setGeneratorConfig(GeneratorConfig generatorConfig) {
		this.generatorConfig = generatorConfig;
	}

	public Map<String, Object> getOptionMap() {
		return optionMap;
	}

	public void setOptionMap(Map<String, Object> optionMap) {
		this.optionMap = optionMap;
	}
}

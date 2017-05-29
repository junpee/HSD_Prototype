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
package com.ibm.jp.awag.generator.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import com.ibm.jp.awag.generator.common.model.GeneratorConfig;

/**
 * AutomatedWebAppGeneratorの設定ファイルを示すクラス。
 * JAXBでXMLからオブジェクトへ設定値をマップする。
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "config")
public class DefaultGeneratorConfig extends GeneratorConfig {

	@XmlElement(name = "xlsFilePath")
	private String xlsFilePath;
	
	/** Excel定義シートマッピング・リスト */
	@XmlElementWrapper(name = "definitionSheetMappings")
	@XmlElement(name = "definitionSheetMapping")
	private List<DefinitionSheetMapping> definitionSheetMappings;

	/** Excel定義シートマッピング */
	@XmlTransient
	private Map<String, String> definitionSheetMap;

	/** Excel定義シートマッピングを示すインナー・クラス */
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class DefinitionSheetMapping {

		/** 定義名 */
		@XmlAttribute(name = "name")
		private String name;
		
		/** セル・アドレス */
		@XmlValue
		private String address;

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
	}

	public String getXlsFilePath() {
		return xlsFilePath;
	}

	public void setXlsFilePath(String xlsFilePath) {
		this.xlsFilePath = xlsFilePath;
	}

	public List<DefinitionSheetMapping> getDefinitionSheetMappings() {
		return definitionSheetMappings;
	}

	public Map<String, String> getDefinitionSheetMap() {

		this.definitionSheetMap = Optional.ofNullable(this.definitionSheetMap).orElseGet(() -> {
			Map<String, String> map = new LinkedHashMap<String, String>();

			for (DefinitionSheetMapping definition : this.definitionSheetMappings) {
				map.put(definition.getName(), definition.getAddress());
			}

			return map;
			});

		return this.definitionSheetMap;
	}

}

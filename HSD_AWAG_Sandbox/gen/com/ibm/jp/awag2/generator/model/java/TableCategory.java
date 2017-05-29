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
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

/**
 * テーブルカテゴリを示すクラス。
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TableCategory {

	/** javaパッケージ */
	@XmlElement
	private String javaPackage;
	
	/** デフォルト・スキーマ */
	@XmlElement
	private String defaultSchema;
	
	/** テーブル定義 */
	@XmlElementWrapper(name = "tableDefinition")
	@XmlElement(name = "table")
	private List<TableDefinition> tableDefinitions;

	/** テーブル定義のMap */
	@XmlTransient
	private Map<String, TableDefinition> tableDefinitionMap;

	/**
	 * @return javaPackage
	 */
	public String getJavaPackage() {
		return javaPackage;
	}

	/**
	 * @param javaPackage セットする javaPackage
	 */
	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}

	/**
	 * defaultSchemaを取得する。
	 * @return defaultSchema
	 */
	public String getDefaultSchema() {
		return defaultSchema;
	}

	/**
	 * defaultSchemaを設定する。
	 * @param defaultSchema 設定するdefaultSchema
	 */
	public void setDefaultSchema(String defaultSchema) {
		this.defaultSchema = defaultSchema;
	}

	/**
	 * @return tableDefinitions
	 */
	public List<TableDefinition> getTableDefinitions() {
		return tableDefinitions;
	}

	/**
	 * @param tableDefinitions セットする tableDefinitions
	 */
	public void setTableDefinitions(List<TableDefinition> tableDefinitions) {
		this.tableDefinitions = tableDefinitions;
	}

	/**
	 * テーブル名とテーブル定義オブジェクトのMapを取得する
	 * @return
	 */
	public Map<String, TableDefinition> getTableDefinitionMap() {
		if (this.tableDefinitionMap == null) {
			this.tableDefinitionMap = this.tableDefinitions.stream().collect(Collectors.toMap(TableDefinition::getName, s -> s));
		}
		return this.tableDefinitionMap;
	}
	
	/**
	 * 従属するテーブル定義の導出項目を設定する。
	 * @param allTableDefinitionMap
	 */
	public void setupDerivationField(Map<String, TableDefinition> allTableDefinitionMap) {
		
		if (this.tableDefinitions != null) {
			for (TableDefinition tableDefinition : this.tableDefinitions) {
				tableDefinition.setupDerivationField(allTableDefinitionMap);
			}
		}
	}
}

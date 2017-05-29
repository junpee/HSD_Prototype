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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.ibm.jp.awag.generator.common.util.Required;
import com.ibm.jp.awag2.generator.model.common.DataType;

/**
 * テーブル定義を示すクラス。
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TableDefinition {
	
	/** エンティティ名（Excel:テーブル名（英字）） */
	@XmlElement
	@Required
	private String name; //IR: aliasName

	/** 表示用エンティティ名 （Excel:テーブル名）*/
	@XmlElement
	private String nameLocal; //IR: name
	
	/** スキーマ名 */
	@XmlElement
	private String schemaName;
	
	/** 物理テーブル名 */
	@XmlElement
	private String tableName; //IR: shortName

	/** Javaパッケージ */
	@XmlElement
	@Required
	private String javaPackage;
	
	/** 最大取得件数 */
	@XmlElement
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer maxResult;
	
	/** テーブル・オプション */
	@XmlElement
	private Map<String, String> options;
	
	/** カラム定義 */
	@XmlElementWrapper(name = "columnDefinition")
	@XmlElement(name = "column")
	private List<ColumnDefinition> columnDefinitions = new ArrayList<>();
	
	/** 関連リソース定義 */
	@XmlElementWrapper(name = "relationDefinition")
	@XmlElement(name = "relation")
	private List<RelationDefinition> relationDefinitions = new ArrayList<>();

	/** 主キー項目定義のリスト */
	@XmlElement
	private List<ColumnDefinition> pkFields;
	
	/** Version型 */
	@XmlElement
	private VersionType versionType;
	
	/** カラム定義のMap */
	@XmlTransient
	private Map<String, ColumnDefinition> columnDefinitionMap;

	/** Versionの型を示すenum */
	public enum VersionType {
		TIMESTAMP("Timestamp"), FLOAT("Long"), NONE("Object");

		private String javaType;

		private VersionType(String javaType) {
			this.javaType = javaType;
		}

		public String getJavaType() {
			return this.javaType;
		}
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name セットする name
	 */
	public void setName(String tableName) {
		this.name = tableName;
	}

	/**
	 * @return schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * @param schemaName セットする schemaName
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	/**
	 * @return nameLocal
	 */
	public String getNameLocal() {
		if (this.nameLocal == null || this.nameLocal.isEmpty()) {
			return this.getName();
		} else {
			return nameLocal;
		}
	}

	/**
	 * @param nameLocal セットする nameLocal
	 */
	public void setNameLocal(String tableNameLocal) {
		this.nameLocal = tableNameLocal;
	}

	/**
	 * tableNameを取得する。
	 * @return tableName
	 */
	public String getTableName() {
		if (this.tableName == null || this.tableName.isEmpty()) {
			return this.getName();
		}  else {
			return this.tableName;
		}
	}

	/**
	 * tableNameを設定する。
	 * @param tableName 設定するtableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getJavaPackage() {
		return javaPackage;
	}

	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
	
	public Integer getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}

	/**
	 * @return columnDefinitions
	 */
	public List<ColumnDefinition> getColumnDefinitions() {
		return columnDefinitions;
	}

	/**
	 * @param columnDefinitions セットする columnDefinitions
	 */
	public void setColumnDefinitions(List<ColumnDefinition> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	/**
	 * @return relationDefinitions
	 */
	public List<RelationDefinition> getRelationDefinitions() {
		return relationDefinitions;
	}

	/**
	 * @param relationDefinitions セットする relationDefinitions
	 */
	public void setRelationDefinitions(List<RelationDefinition> relationDefinitions) {
		this.relationDefinitions = relationDefinitions;
	}

	public List<ColumnDefinition> getPkFields() {
		if (this.pkFields == null) {
			this.setPkFields();
		}
		return this.pkFields;
	}

	/**
	 * PK指定のある項目のリストを構築してフィールドに設定する。
	 */
	public void setPkFields() {
		List<ColumnDefinition> pkFields = new ArrayList<ColumnDefinition>();
		for (ColumnDefinition columnDefinition : columnDefinitions) {
			if (columnDefinition.isPk()) {
				pkFields.add(columnDefinition);
			}
		}
		this.pkFields = pkFields;
	}

	public VersionType getVersionType() {
		if (this.versionType == null) {
			this.setVersionType();
		}
		return this.versionType;
	}

	public void setVersionType(VersionType versionType) {
		this.versionType = versionType;
	}

	public void setVersionType() {
		VersionType versionType = VersionType.NONE;
		
		for (ColumnDefinition columnDefinition : columnDefinitions) {
			if (columnDefinition.isVersion()) {
				if (columnDefinition.getDataType().equals(DataType.TIMESTAMP)) {
					versionType  = VersionType.TIMESTAMP;
				} else if(columnDefinition.getDataType().equals(DataType.FLOAT)) {
					versionType  = VersionType.FLOAT;
				}
			}
		}
		
		this.versionType = versionType;
	}

	/**
	 * カラム名とカラム定義オブジェクトのMapを取得する
	 * @return
	 */
	public Map<String, ColumnDefinition> getColumnDefinitionMap() {
		if (this.columnDefinitionMap == null) {
			this.columnDefinitionMap = this.columnDefinitions.stream().collect(Collectors.toMap(ColumnDefinition::getName, s -> s));
		}
		
		return this.columnDefinitionMap;
	}

	/**
	 * を行う。
	 * @param allTableDefinitionMap
	 */
	public void setupDerivationField(Map<String, TableDefinition> allTableDefinitionMap) {
		this. setUpRelationRelationDefinition(allTableDefinitionMap);
	}

	/**
	 * を行う。
	 * @param allTableDefinitionMap
	 */
	private void setUpRelationRelationDefinition(Map<String, TableDefinition> allTableDefinitionMap) {
		
		if (this.relationDefinitions != null) {
			for (RelationDefinition relationDef : this.relationDefinitions) {
				relationDef.setTableDefinition(allTableDefinitionMap.get(relationDef.getName()));
			}
		}
		
	}
	
}

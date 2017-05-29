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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ibm.jp.awag.generator.common.util.Format;
import com.ibm.jp.awag.generator.common.util.Required;


/**
 * リソース定義ファイルの定義内容を示すクラス。
 *
 */
public class ResourceDefinition {

	/** リソース名英字 */
	@Format(formatType = Format.FormatType.HALF_NUM_CHAR)
	@Required
	private String resourceName;
	
	/** リソース名 */
	@Format(formatType = Format.FormatType.ALL)
	@Required
	private String resourceDisplayName;
	
	/** スキーマ名 */
	@Format(formatType = Format.FormatType.HALF_NUM_CHAR)
	private String schemaName;
	
	/** テーブル名英字 */
	@Format(formatType = Format.FormatType.HALF_CHAR)
	@Required
	private String tableName;
	
	/** パッケージ名 */
	@Format(formatType = Format.FormatType.HALF_CHAR)
	@Required
	private String packageName;
	
	/** リソース・オプション */
	private Map<String, String> options;
	
	/** 最大取得件数 */
	private Integer maxResult;
	
	/** 画面コード出力 */
	@Format(formatType = Format.FormatType.FULL_HIRAGANA)
	private boolean isWebGenEnabled;
	
	/** リソースのデータ項目定義 */
	private List<ColumnDefinition> columnDefinitions;
	
	/** 主キー項目定義のリスト */
	private List<ColumnDefinition> pkFields;
	
	/** 検索パラメータ項目定義のリスト */
	private List<ColumnDefinition> paramFields;
	
	/** リソースに定義されたデータ項目数 */
	private int ColumnNum;
	
	/** 関連リソースの定義 */
	private List<RelationDefinition> relationDefinitions;

	/** 関連リソースの親参照 */
	private ResourceDefinition resourceDefinitionParent;
	
	/** 親リソース名 */
	private String resourceNameParent;
	
	/** Version型 */
	private VersionType versionType; 

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
	
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceDisplayName() {
		return resourceDisplayName;
	}

	public void setResourceDisplayName(String resourceDisplayName) {
		this.resourceDisplayName = resourceDisplayName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public List<ColumnDefinition> getColumnDefinitions() {
		return columnDefinitions;
	}

	public void setColumnDefinitions(List<ColumnDefinition> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
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

	public boolean isWebGenEnabled() {
		return isWebGenEnabled;
	}

	public void setWebGenEnabled(boolean isWebGenEnabled) {
		this.isWebGenEnabled = isWebGenEnabled;
	}

	public List<ColumnDefinition> getPkFields() {
		return pkFields;
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

	public List<ColumnDefinition> getParamFields() {
		return paramFields;
	}

	/**
	 * 検索パラメータ指定のある項目のリストを構築してフィールドに設定する。
	 */
	public void setParamFields() {
		List<ColumnDefinition> paramFields = new ArrayList<ColumnDefinition>();
		for (ColumnDefinition columnDefinition : columnDefinitions) {
			if (columnDefinition.isSearchParam()) {
				paramFields.add(columnDefinition);
			}
		}
		this.paramFields = paramFields;
	}

	public int getColumnNum() {
		return ColumnNum;
	}

	public void setColumnNum(int columnNum) {
		this.ColumnNum = columnNum;
	}

	public List<RelationDefinition> getRelationDefinitions() {
		return relationDefinitions;
	}

	public void setRelationDefinitions(List<RelationDefinition> relationDefinitions) {
		this.relationDefinitions = relationDefinitions;
	}

	public ResourceDefinition getResourceDefinitionParent() {
		return resourceDefinitionParent;
	}

	public void setResourceDefinitionParent(ResourceDefinition resourceDefinitionParent) {
		this.resourceDefinitionParent = resourceDefinitionParent;
	}

	public String getResourceNameParent() {
		return resourceNameParent;
	}

	public void setResourceNameParent(String resourceNameParent) {
		this.resourceNameParent = resourceNameParent;
	}

	public List<ResourceDefinition> getEmbeddedResourceDefinitions() {
		if (this.getRelationDefinitions() != null) {
			return this.getRelationDefinitions().stream().map(s -> s.getResourceDefinition())
					.collect(Collectors.toList());
		} else {
			return null;
		}
	}

	public VersionType getVersionType() {
		return versionType;
	}

	public void setVersionType(VersionType versionType) {
		this.versionType = versionType;
	}
	
}

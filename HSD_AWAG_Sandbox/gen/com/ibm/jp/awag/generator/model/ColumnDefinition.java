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

import java.util.Map;

import com.ibm.jp.awag.generator.common.util.Format;
import com.ibm.jp.awag.generator.common.util.Required;

/**
 * リソース定義シートのデータ項目定義を示すクラス。
 *
 */
public class ColumnDefinition {

	/** パラメータ名 */
	@Format(formatType = Format.FormatType.HALF_NUM_CHAR)
	@Required
	private String fieldName;
	
	/** 画面表示項目名 */
	@Format(formatType = Format.FormatType.ALL)
	@Required
	private String displayName;
	
	/** カラム名（英字） */
	@Format(formatType = Format.FormatType.HALF_NUM_CHAR)
	@Required
	private String columnName;
	
	/** PK */
	private boolean isPk;
	
	/** Null許可 */
	private boolean isAllowedNull;
	
	/** データ型 */
	@Format(formatType = Format.FormatType.ALL)
	@Required
	private DataType dataType;
	
	/** 全体桁数 */
	private int length;
	
	/** 小数桁数 */
	private int fraction;
	
	/** 入力チェック仕様 */
	private FormatValidationDefinition formatValidationRule;
	
	/** 検索パラメータ */
	private boolean isSearchParam;
	
	/** 検索一致条件 */
	private ConditionOperator conditionOperator;
	
	/** ロック用バージョン */
	private boolean isVersion;
	
	/** 表示順序 */
	private int displayOrder;
	
	/** HTML TYPE */
	private HtmlType htmlType;
	
	/** HTML コードリスト ID */
	@Format(formatType = Format.FormatType.HALF_NUM_CHAR)
	private String codeListId;
	
	/** オプション */
	private Map<String, String> options;
	
	/** リソース定義シートのデータ型とJavaのデータ型を紐付けるenum */
	public enum DataType {
		SMALLINT("String"),
		INTEGER("String"),
		BIGINT("String"),
		FLOAT("String"),
		DECIMAL("String"),
		CHAR("String"),
		VARCHAR("String"),
		DATE("String"),
		TIMESTAMP("String");

		private String javaType;

		private DataType(String javaType) {
			this.javaType = javaType;
		}

		public String getJavaType() {
			return this.javaType;
		}
	}

	/** 検索一致条件のenum */
	public enum ConditionOperator {
		EXACT,
		PREFIX,
		PARTIAL
	}

	/** HTML TYPEのenum */
	public enum HtmlType {
		TEXT("text"),
		CHECKBOX("checkbox"),
		RADIO("radio"),
		SELECT("select"),
		TEXTAREA("textarea"),
		OUTPUT("output"),
		SPACER("spacer"),
		NONE("");

		private String htmlType;

		private HtmlType(String htmlType) {
			this.htmlType = htmlType;
		}

		public String getHtmlType() {
			return this.htmlType;
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isPk() {
		return isPk;
	}

	public void setPk(boolean isPk) {
		this.isPk = isPk;
	}

	public boolean isAllowedNull() {
		return isAllowedNull;
	}

	public void setAllowedNull(boolean isAllowedNull) {
		this.isAllowedNull = isAllowedNull;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public FormatValidationDefinition getFormatValidationRule() {
		return formatValidationRule;
	}

	public void setFormatValidationRule(FormatValidationDefinition formatValidationRule) {
		this.formatValidationRule = formatValidationRule;
	}

	public int getFraction() {
		return fraction;
	}

	public void setFraction(int fraction) {
		this.fraction = fraction;
	}

	public boolean isSearchParam() {
		return isSearchParam;
	}

	public void setSearchParam(boolean isSearchParam) {
		this.isSearchParam = isSearchParam;
	}

	public ConditionOperator getConditionOperator() {
		return conditionOperator;
	}

	public void setConditionOperator(ConditionOperator conditionOperator) {
		this.conditionOperator = conditionOperator;
	}

	public boolean isVersion() {
		return isVersion;
	}

	public void setVersion(boolean isVersion) {
		this.isVersion = isVersion;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public HtmlType getHtmlType() {
		return htmlType;
	}

	public void setHtmlType(HtmlType htmlType) {
		this.htmlType = htmlType;
	}

	public String getCodeListId() {
		return codeListId;
	}

	public void setCodeListId(String codeListId) {
		this.codeListId = codeListId;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

}

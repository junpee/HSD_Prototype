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
package com.ibm.jp.awag2.generator.model.web;

import java.util.Map;

import com.ibm.jp.awag.generator.common.util.Format;
import com.ibm.jp.awag.generator.common.util.Required;
import com.ibm.jp.awag2.generator.model.common.DataType;

/**
 * 画面項目定義の画面項目仕様を表すクラス。
 *
 */
public class FieldDefinition {

	/** パラメータ名 */
	@Format(formatType = Format.FormatType.HALF_CHAR)
	@Required
	private String fieldName;

	/** 画面表示項目名 */
	@Format(formatType = Format.FormatType.ALL)
	@Required
	private String displayName;

	/** 表示優先度 */
	private Integer displayPriority;

	/** PK */
	private boolean isPk;

	/** 必須 */
	private boolean isRequired;

	/** データ型 */
	private DataType dataType;

	/** 入力チェック仕様 */
	private FormatValidationDefinition formatValidationRule;

	/** HTML TYPE */
	private HtmlType htmlType;

	/** HTML コードリスト ID */
	@Format(formatType = Format.FormatType.HALF_NUM_CHAR)
	private String codeListId;

	/** オプション */
	private Map<String, String> options;

	/** HTML readonly */
	private boolean isReadonly;

	/** HTMLタグ */
	private String value;

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
		STATIC_TEXT("static_text"),
		SPACER("spacer"),
		BUTTON("button"),
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

	/**
	 * displayPriorityを取得する。
	 * @return displayPriority
	 */
	public Integer getDisplayPriority() {
		return this.displayPriority;
	}

	/**
	 * displayPriorityを設定する。
	 * @param displayPriority 設定するdisplayPriority
	 */
	public void setDisplayPriority(Integer displayPriority) {
		this.displayPriority = displayPriority;
	}

	public boolean isPk() {
		return isPk;
	}

	public void setPk(boolean isPk) {
		this.isPk = isPk;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public DataType getDataType() {
		return dataType;
	}

	public boolean isReadonly() {
		return isReadonly;
	}

	public void setReadonly(boolean isReadonly) {
		this.isReadonly = isReadonly;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public FormatValidationDefinition getFormatValidationRule() {
		return formatValidationRule;
	}

	public void setFormatValidationRule(FormatValidationDefinition formatValidationRule) {
		this.formatValidationRule = formatValidationRule;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

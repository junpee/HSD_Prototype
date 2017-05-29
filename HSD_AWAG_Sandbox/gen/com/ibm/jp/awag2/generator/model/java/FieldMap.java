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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * オブジェクト変換のマッピング情報を示すクラス。
 *
 */
public class FieldMap {

	/** 変換後のフィールド名 */
	private String OutputField;

	/** 変換前のクラス名 */
	private String inputType;

	/** 変換前のフィールド名 */
	private String InputField;

	/** 対象フィールドの多重度が複数か否か */
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	private Boolean multiple;

	/** 対象フィールドのカラム定義 */
	private ColumnDefinition fieldDefinition;

	/** 従属するフィールドのマッピング情報 */
	private Map<String, List<FieldMap>> embeddedMapping = new HashMap<>();
	
	/**
	 * outputFieldを取得する。
	 * @return outputField
	 */
	public String getOutputField() {
		return this.OutputField;
	}

	/**
	 * outputFieldを設定する。
	 * @param outputField 設定するoutputField
	 */
	public void setOutputField(String outputField) {
		this.OutputField = outputField;
	}

	/**
	 * inputBeanを取得する。
	 * @return inputBean
	 */
	public String getInputType() {
		return this.inputType;
	}

	/**
	 * inputBeanを設定する。
	 * @param inputBean 設定するinputBean
	 */
	public void setInputType(String inputBean) {
		this.inputType = inputBean;
	}

	/**
	 * inputFieldを取得する。
	 * @return inputField
	 */
	public String getInputField() {
		return this.InputField;
	}

	/**
	 * inputFieldを設定する。
	 * @param inputField 設定するinputField
	 */
	public void setInputField(String inputField) {
		this.InputField = inputField;
	}

	/**
	 * multipleを取得する。
	 * @return multiple
	 */
	public boolean isMultiple() {
		return Optional.ofNullable(multiple).orElse(false);
	}
	
	public Boolean getMultiple() {
		return multiple;
	}

	/**
	 * multipleを設定する。
	 * @param multiple 設定するmultiple
	 */
	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

	/**
	 * fieldDefinitionを取得する。
	 * @return fieldDefinition
	 */
	public ColumnDefinition getFieldDefinition() {
		return fieldDefinition;
	}

	/**
	 * fieldDefinitionを設定する。
	 * @param fieldDefinition 設定するfieldDefinition
	 */
	public void setFieldDefinition(ColumnDefinition fieldDefinition) {
		this.fieldDefinition = fieldDefinition;
	}

	/**
	 * embeddedMapを取得する。
	 * @return embeddedMapping
	 */
	public Map<String, List<FieldMap>> getEmbeddedMapping() {
		return this.embeddedMapping;
	}

	/**
	 * embeddedMapを設定する。
	 * @param embeddedMapping 設定するembeddedMap
	 */
	public void setEmbeddedMapping(Map<String, List<FieldMap>> embeddedMap) {
		this.embeddedMapping = embeddedMap;
	}
}

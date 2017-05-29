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
package com.ibm.jp.awag.rest.common.logic;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Validationエラー情報を示すクラス。
 *
 */
@XmlRootElement(name = "error")
public class ValidationErrorInfo extends ErrorInfo {

	/** リソース名 */
	protected String resourceName;
	
	/** フィールド名 */
	protected String fieldName;
	
	/** キー値 */
	protected String key;

	/**
	 * エラーコード、エラーメッセージ、リソース名、フィールド名、キー値を指定してインスタンスを初期化する。
	 * @param code エラーコード
	 * @param message エラーメッセージ
	 * @param resourceName リソース名
	 * @param fieldName フィールド名
	 * @param key キー値
	 */
	public ValidationErrorInfo(String code, String message, String resourceName, String fieldName, String key) {
		super(code, message);
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.key = key;
	}

	/**
	 * リソース名を取得する。
	 * @return リソース名
	 */
	@XmlAttribute(name="resource")
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * リソース名を設定する。
	 * @param resourceName リソース名
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * フィールド名を取得する。
	 * @return フィールド名
	 */
	@XmlAttribute(name="field")
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * フィールド名を設定する。
	 * @param fieldName フィールド名
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * キー値を取得する。
	 * @return キー値
	 */
	@XmlAttribute(name="key")
	public String getKey() {
		return key;
	}

	/**
	 * キー値を設定する。
	 * @param key キー値
	 */
	public void setKey(String key) {
		this.key = key;
	}

}

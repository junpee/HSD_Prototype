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
package com.ibm.jp.awag2.common.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * エラー情報を格納するコンテナ・クラス。
 *
 */
@XmlRootElement(name = "errorContainer")
public class ErrorContainer {

	/** エラーコード */
	@XmlAttribute(name="code")
	private String code;

	/** エラーメッセージ */
	@XmlAttribute(name="message")
	private String message;
	
	/** エラー情報オブジェクトのリスト */
	@XmlElement(name="errors")
	private List<ErrorInfo> errors;

	/**
	 * 唯一のコンストラクタ
	 */
	public ErrorContainer() {
		super();
	}
	
	/**
	 * エラーコードを指定してインスタンスを初期化する。
	 * @param code
	 */
	public ErrorContainer(String code) {
		this.code = code;
		
		ResourceBundle messageProp = ResourceBundle.getBundle("AWAGCommonMessages");
		if (code != null) this.message = messageProp.getString(code);
	}
	
	/**
	 * エラーコードとロケールを指定してインスタンスを初期化する。
	 * @param code
	 * @param locale
	 */
	public ErrorContainer(String code, Locale locale) {
		this.code = code;
		
		ResourceBundle messageProp = null;
		if (locale != null) {
			messageProp = ResourceBundle.getBundle("AWAGCommonMessages", locale);
		} else {
			messageProp = ResourceBundle.getBundle("AWAGCommonMessages");
		}
		
		this.message = messageProp.getString(code);
	}
	
	/**
	 * エラーコードを取得する。
	 * @return エラーコード
	 */
	public String getCode() {
		return code;
		}

	/**
	 * エラーメッセージを取得する。
	 * @return エラーメッセージ
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * 詳細なエラー情報を格納したエラー情報オブジェクトのリストを取得する。
	 * @return エラー情報オブジェクトのリスト
	 */
	public List<ErrorInfo> getErrors() {
		return errors;
	}

	/**
	 * 詳細なエラー情報を格納したエラー情報オブジェクトを追加する。
	 * @param errorInfo エラー情報オブジェクト
	 */
	public void addError(ErrorInfo errorInfo)
	{
		if (this.errors == null) {
			this.errors = new ArrayList<ErrorInfo>();
		}
		this.errors.add(errorInfo);
	}
	
	/**
	 * 詳細なエラー情報を格納したエラー情報オブジェクトのリストを追加する。
	 * @param errorList エラー情報オブジェクトのリスト
	 */
	public void addErrors(List<ErrorInfo> errorList) {
		if (this.errors == null) {
			this.errors = new ArrayList<ErrorInfo>();
	}
		this.errors.addAll(errorList);
	}

}

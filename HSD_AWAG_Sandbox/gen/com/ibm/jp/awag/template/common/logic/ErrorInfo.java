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
 * エラー情報を示すクラス。
 *
 */
@XmlRootElement(name = "error")
public class ErrorInfo {

	/** エラーコード */
	protected String code;
	
	/** エラーメッセージ */
	protected String message;
	
	/**
	 * デフォルト・コンストラクタ
	 */
	public ErrorInfo(){}
	
	/**
	 * エラーコード、エラーメッセージを指定してインスタンスを初期化する。
	 * @param code エラーコード
	 * @param message エラーメッセージ
	 */
	public ErrorInfo(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * エラーコード、エラー対象項目名、エラーメッセージを指定してインスタンスを初期化する。
	 * @param code エラーコード
	 * @param item エラー対象項目名
	 * @param message エラーメッセージ
	 */
	public ErrorInfo(String code, String message, String resource) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * エラーコードを取得する。
	 * @return エラーコード
	 */
	@XmlAttribute(name="code")
	public String getCode() {
		return code;
	}
	
	/**
	 * エラーコードを設定する。
	 * @param code エラーコード
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * エラーメッセージを取得する。
	 * @return エラーメッセージ
	 */
	@XmlAttribute(name="message")
	public String getMessage() {
		return message;
	}
	
	/**
	 * エラーメッセージを設定する。
	 * @param message エラーメッセージ
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}

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

import java.util.Locale;

/**
 * チェック例外の基底クラス。
 *
 */
public class ExceptionBase extends Exception {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** エラー情報コンテナ */
	private ErrorContainer ec;

	/**
	 * デフォルト・コンストラクタ。
	 */
	public ExceptionBase() {
		super();
	}
	
	/**
	 * エラーコード、発生原因の例外オブジェクトを指定して、インスタンスを初期化する。
	 * @param errorCode エラーコード
	 * @param cause 発生原因の例外オブジェクト
	 */
	public ExceptionBase(String errorCode, Throwable cause) {
		super(cause);
		this.ec = new ErrorContainer(errorCode);
	}
	
	/**
	 * エラーコード、発生原因の例外オブジェクト、ロケールを指定して、インスタンスを初期化する。
	 * @param errorCode エラーコード
	 * @param cause 発生原因の例外オブジェクト
	 * @param locale ロケール
	 */
	public ExceptionBase(String errorCode, Throwable cause, Locale locale) {
		super(cause);
		this.ec = new ErrorContainer(errorCode, locale);
	}
	
	/**
	 * エラー情報コンテナを指定して、インスタンスを初期化する。
	 * @param ec エラー情報のコンテナ
	 */
	public ExceptionBase(ErrorContainer ec) {
		super();
		this.ec = ec;
	}

	/**
	 * エラー情報コンテナを取得する。
	 * @return エラー情報コンテナ
	 */
	public ErrorContainer getErrorContainer() {
		return this.ec;
	}
}

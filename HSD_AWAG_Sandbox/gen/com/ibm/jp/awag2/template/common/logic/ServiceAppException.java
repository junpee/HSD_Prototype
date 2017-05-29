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
 * Service層で発生した業務エラーを示す例外クラス。
 *
 */
public class ServiceAppException extends ExceptionBase {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルト・コンストラクタ。
	 */
	public ServiceAppException() {
		super();
	}

	/**
	 * エラーコード、発生原因の例外オブジェクトを指定して、インスタンスを初期化する。
	 * @param errorCode エラーコード
	 * @param cause 発生原因の例外オブジェクト
	 */
	public ServiceAppException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}
	
	/**
	 * エラー情報コンテナを指定して、インスタンスを初期化する。
	 * @param ec エラー情報コンテナ
	 */
	public ServiceAppException(ErrorContainer ec) {
		super(ec);
	}

	public ServiceAppException(String errorCode, Exception cause, Locale locale) {
		super(errorCode, cause, locale);
	}
}

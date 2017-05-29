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
 * Service層で発生したDB例外を示す例外クラス。
 *
 */
public class ServiceDBException extends ExceptionBase {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** DBエラータイプ  */
	private DBErrorType errorType;
	
	/** DBエラータイプを示す*/
	public enum DBErrorType {
		OPTIMISTIC_LOCK,
		DUPPLICATE,
		DELETED,
		NOT_FOUND,
		OTHER
	}
	
	/**
	 * DBエラータイプ、エラーコード、発生原因の例外オブジェクトを指定してインスタンスを初期化する。
	 * @param errorType DBエラータイプ
	 * @param errorCode エラーコード
	 * @param cause 発生原因の例外オブジェクト
	 */
	public ServiceDBException(DBErrorType errorType, String errorCode, Throwable cause) {
		super(errorCode, cause);
		this.errorType = errorType;
	}
	
	/**
	 * DBエラータイプ、エラーコード、発生原因の例外オブジェクト、ロケールを指定してインスタンスを初期化する。
	 * @param errorType DBエラータイプ
	 * @param errorCode エラーコード
	 * @param cause 発生原因の例外オブジェクト
	 * @param locale ロケール
	 */
	public ServiceDBException(DBErrorType errorType, String errorCode, Throwable cause, Locale locale) {
		super(errorCode, cause, locale);
		this.errorType = errorType;
	}

	/**
	 * DBエラータイプを取得する。
	 * @return DBエラータイプ
	 */
	public DBErrorType getErrorType() {
		return this.errorType;
	}
}

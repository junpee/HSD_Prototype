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
package com.ibm.jp.awag.generator.common;

/**
 * ジェネレータ例外を示すクラス。
 *
 */
public class GenerateException extends RuntimeException {

	/**
	 * メッセージと発生原因の例外オブジェクトを指定して、インスタンスを初期化する。
	 * @param message
	 * @param cause
	 */
	public GenerateException(String message, Throwable cause) {
		super(message, cause);
	}
}

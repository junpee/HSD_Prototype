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
package com.ibm.jp.awag.rest.common.util;

/**
 * ロガーを生成するファクトリ・クラス。
 *
 */
public class LoggerFactory {
	
	/**
	 * 唯一のコンストラクタ。
	 */
	private LoggerFactory(){}
	
	/**
	 * ロガーを使用するオブジェクトを引数に、ロガーを取得する。
	 * 指定したオブジェクトのクラス名のロガーを取得する。
	 * @param obj ロガーを使用するオブジェクト
	 * @return ロガー
	 */
	public static Logger getLogger(Object obj) {
		return new DefaultLogger(org.slf4j.LoggerFactory.getLogger(obj.getClass().getSimpleName()));
	}
}

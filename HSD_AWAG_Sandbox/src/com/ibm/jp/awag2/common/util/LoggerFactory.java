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
package com.ibm.jp.awag2.common.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ロガーを生成するファクトリ・クラス。
 *
 */
public abstract class LoggerFactory {
	
	/** デフォルトのロガー・ファクトリ・クラス */
	private static final String DEFAULT_LOGGER_FACTORY = "com.ibm.jp.awag2.common.util.DefaultLoggerFactory";
	
	protected static final String DEFAULT_LOGGER_NAME = "NO_NAME_LOGGER";
	
	/** ロガー・ファクトリを格納するConcurrentMap */
	private static ConcurrentMap<String, LoggerFactory> loggerFactoryMap = new ConcurrentHashMap<>();
	
	/**
	 * 唯一のコンストラクタ。
	 */
	protected LoggerFactory(){}
	
	/**
	 * ファクトリ名を指定してロガー・ファクトリのインスタンスを取得する。
	 * @param factoryName ファクトリ名称
	 * @return ロガー・ファクトリのインスタンス
	 */
	public static LoggerFactory getInstance(String factoryName) {
		
		if (factoryName == null || factoryName.isEmpty()) {
			factoryName = DEFAULT_LOGGER_FACTORY;
		}
		
		LoggerFactory factory = loggerFactoryMap.get(factoryName);
		
		if (factory != null) {
			return factory;
		} else {
			try {
				factory = (LoggerFactory) Class.forName(factoryName).newInstance();
				loggerFactoryMap.put(factoryName, factory);
				return factory;
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			return factory;
		}
	}
	
	/**
	 * デフォルト・ロガー・ファクトリのインスタンスを取得します。
	 * @return デフォルト・ロガー・ファクトリのインスタンス
	 */
	public static LoggerFactory getInstance() {
		return getInstance(null);
	}
	
	/**
	 * ロガーのインスタンスを取得します。
	 * ロガー名にはtargetの完全修飾名を使用します。
	 * @param target クラス名をロガー名として使用するclass
	 * @return ロガー・インスタンス
	 */
	public abstract Logger getLogger(Object target);
	
}

/**
 * 
 */
package com.ibm.jp.awag2.common.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;

/**
 * デフォルトのロガー・ファクトリの実装クラス。
 * Log4j2のLoggerを使用する。
 *
 */
public class DefaultLoggerFactory extends LoggerFactory {

	/** ロガーを格納するConcurrentMapオブジェクト */
	private ConcurrentMap<String, Logger> loggerMap = new ConcurrentHashMap<String, Logger>();
	
	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.AbstractLoggerFactory#getLogger(java.lang.Object)
	 */
	@Override
	public Logger getLogger(Object target) {
		
		String logerName = null;
		
		if (target == null) {
			logerName = LoggerFactory.DEFAULT_LOGGER_NAME;
		} else {
			logerName = target.getClass().getName();
		}
		
		Logger logger = loggerMap.get(target.getClass().getName());
		if (logger != null) {
			return logger;
		}

		logger = new DefaultLogger(LogManager.getLogger(logerName));
		loggerMap.putIfAbsent(logerName, new DefaultLogger(LogManager.getLogger(logerName)));
		
		return logger;
	}

}

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

import org.apache.logging.log4j.Logger;

/**
 * デフォルト・ロガー。
 * 全ての処理をlog4j2のLoggerに委譲する。
 */
public class DefaultLogger implements com.ibm.jp.awag2.common.util.Logger {

	/** デフォルト・ロガー（log4j2 Logger） */
	private Logger logger;
	
	/** log4j2 Loggerを指定してインスタンスを初期化する。 */
	public DefaultLogger(Logger logger) {
		this.logger = logger;
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#debug(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void debug(String arg0, Object arg1, Object arg2) {
		logger.debug(arg0, arg1, arg2);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#debug(java.lang.String, java.lang.Object)
	 */
	@Override
	public void debug(String arg0, Object... arg1) {
		logger.debug(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#debug(java.lang.String, java.lang.Object)
	 */
	@Override
	public void debug(String arg0, Object arg1) {
		logger.debug(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#debug(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void debug(String arg0, Throwable arg1) {
		logger.debug(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#debug(java.lang.String)
	 */
	@Override
	public void debug(String arg0) {
		logger.debug(arg0);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#error(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void error(String arg0, Object arg1, Object arg2) {
		logger.error(arg0, arg1, arg2);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#error(java.lang.String, java.lang.Object)
	 */
	@Override
	public void error(String arg0, Object... arg1) {
		logger.error(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#error(java.lang.String, java.lang.Object)
	 */
	@Override
	public void error(String arg0, Object arg1) {
		logger.error(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#error(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void error(String arg0, Throwable arg1) {
		logger.error(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#error(java.lang.String)
	 */
	@Override
	public void error(String arg0) {
		logger.error(arg0);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#getName()
	 */
	@Override
	public String getName() {
		return logger.getName();
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#info(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void info(String arg0, Object arg1, Object arg2) {
		logger.info(arg0, arg1, arg2);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#info(java.lang.String, java.lang.Object)
	 */
	@Override
	public void info(String arg0, Object... arg1) {
		logger.info(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#info(java.lang.String, java.lang.Object)
	 */
	@Override
	public void info(String arg0, Object arg1) {
		logger.info(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#info(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void info(String arg0, Throwable arg1) {
		logger.info(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#info(java.lang.String)
	 */
	@Override
	public void info(String arg0) {
		logger.info(arg0);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#isDebugEnabled()
	 */
	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
	
	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#isErrorEnabled()
	 */
	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#isInfoEnabled()
	 */
	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#isTraceEnabled()
	 */
	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#isWarnEnabled()
	 */
	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#trace(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void trace(String arg0, Object arg1, Object arg2) {
		logger.trace(arg0, arg1, arg2);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#trace(java.lang.String, java.lang.Object)
	 */
	@Override
	public void trace(String arg0, Object... arg1) {
		logger.trace(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#trace(java.lang.String, java.lang.Object)
	 */
	@Override
	public void trace(String arg0, Object arg1) {
		logger.trace(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#trace(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void trace(String arg0, Throwable arg1) {
		logger.trace(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#trace(java.lang.String)
	 */
	@Override
	public void trace(String arg0) {
		logger.trace(arg0);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#warn(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void warn(String arg0, Object arg1, Object arg2) {
		logger.warn(arg0, arg1, arg2);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#warn(java.lang.String, java.lang.Object)
	 */
	@Override
	public void warn(String arg0, Object... arg1) {
		logger.warn(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#warn(java.lang.String, java.lang.Object)
	 */
	@Override
	public void warn(String arg0, Object arg1) {
		logger.warn(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#warn(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warn(String arg0, Throwable arg1) {
		logger.warn(arg0, arg1);
	}

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.util.LoggerIF#warn(java.lang.String)
	 */
	@Override
	public void warn(String arg0) {
		logger.warn(arg0);
	}
}

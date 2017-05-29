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

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * デフォルト・ロガー。
 * 全ての処理をsl4jロガーに委譲する。
 */
public class DefaultLogger implements com.ibm.jp.awag.rest.common.util.Logger {

	/** sl4jロガー */
	private Logger logger;
	
	/** sl4jロガーを指定してインスタンスを初期化する。 */
	public DefaultLogger(Logger logger) {
		this.logger = logger;
	}

	public void debug(Marker arg0, String arg1, Object arg2, Object arg3) {
		logger.debug(arg0, arg1, arg2, arg3);
	}

	public void debug(Marker arg0, String arg1, Object... arg2) {
		logger.debug(arg0, arg1, arg2);
	}

	public void debug(Marker arg0, String arg1, Object arg2) {
		logger.debug(arg0, arg1, arg2);
	}

	public void debug(Marker arg0, String arg1, Throwable arg2) {
		logger.debug(arg0, arg1, arg2);
	}

	public void debug(Marker arg0, String arg1) {
		logger.debug(arg0, arg1);
	}

	public void debug(String arg0, Object arg1, Object arg2) {
		logger.debug(arg0, arg1, arg2);
	}

	public void debug(String arg0, Object... arg1) {
		logger.debug(arg0, arg1);
	}

	public void debug(String arg0, Object arg1) {
		logger.debug(arg0, arg1);
	}

	public void debug(String arg0, Throwable arg1) {
		logger.debug(arg0, arg1);
	}

	public void debug(String arg0) {
		logger.debug(arg0);
	}

	public void error(Marker arg0, String arg1, Object arg2, Object arg3) {
		logger.error(arg0, arg1, arg2, arg3);
	}

	public void error(Marker arg0, String arg1, Object... arg2) {
		logger.error(arg0, arg1, arg2);
	}

	public void error(Marker arg0, String arg1, Object arg2) {
		logger.error(arg0, arg1, arg2);
	}

	public void error(Marker arg0, String arg1, Throwable arg2) {
		logger.error(arg0, arg1, arg2);
	}

	public void error(Marker arg0, String arg1) {
		logger.error(arg0, arg1);
	}

	public void error(String arg0, Object arg1, Object arg2) {
		logger.error(arg0, arg1, arg2);
	}

	public void error(String arg0, Object... arg1) {
		logger.error(arg0, arg1);
	}

	public void error(String arg0, Object arg1) {
		logger.error(arg0, arg1);
	}

	public void error(String arg0, Throwable arg1) {
		logger.error(arg0, arg1);
	}

	public void error(String arg0) {
		logger.error(arg0);
	}

	public String getName() {
		return logger.getName();
	}

	public void info(Marker arg0, String arg1, Object arg2, Object arg3) {
		logger.info(arg0, arg1, arg2, arg3);
	}

	public void info(Marker arg0, String arg1, Object... arg2) {
		logger.info(arg0, arg1, arg2);
	}

	public void info(Marker arg0, String arg1, Object arg2) {
		logger.info(arg0, arg1, arg2);
	}

	public void info(Marker arg0, String arg1, Throwable arg2) {
		logger.info(arg0, arg1, arg2);
	}

	public void info(Marker arg0, String arg1) {
		logger.info(arg0, arg1);
	}

	public void info(String arg0, Object arg1, Object arg2) {
		logger.info(arg0, arg1, arg2);
	}

	public void info(String arg0, Object... arg1) {
		logger.info(arg0, arg1);
	}

	public void info(String arg0, Object arg1) {
		logger.info(arg0, arg1);
	}

	public void info(String arg0, Throwable arg1) {
		logger.info(arg0, arg1);
	}

	public void info(String arg0) {
		logger.info(arg0);
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public boolean isDebugEnabled(Marker arg0) {
		return logger.isDebugEnabled(arg0);
	}

	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	public boolean isErrorEnabled(Marker arg0) {
		return logger.isErrorEnabled(arg0);
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public boolean isInfoEnabled(Marker arg0) {
		return logger.isInfoEnabled(arg0);
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public boolean isTraceEnabled(Marker arg0) {
		return logger.isTraceEnabled(arg0);
	}

	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	public boolean isWarnEnabled(Marker arg0) {
		return logger.isWarnEnabled(arg0);
	}

	public void trace(Marker arg0, String arg1, Object arg2, Object arg3) {
		logger.trace(arg0, arg1, arg2, arg3);
	}

	public void trace(Marker arg0, String arg1, Object... arg2) {
		logger.trace(arg0, arg1, arg2);
	}

	public void trace(Marker arg0, String arg1, Object arg2) {
		logger.trace(arg0, arg1, arg2);
	}

	public void trace(Marker arg0, String arg1, Throwable arg2) {
		logger.trace(arg0, arg1, arg2);
	}

	public void trace(Marker arg0, String arg1) {
		logger.trace(arg0, arg1);
	}

	public void trace(String arg0, Object arg1, Object arg2) {
		logger.trace(arg0, arg1, arg2);
	}

	public void trace(String arg0, Object... arg1) {
		logger.trace(arg0, arg1);
	}

	public void trace(String arg0, Object arg1) {
		logger.trace(arg0, arg1);
	}

	public void trace(String arg0, Throwable arg1) {
		logger.trace(arg0, arg1);
	}

	public void trace(String arg0) {
		logger.trace(arg0);
	}

	public void warn(Marker arg0, String arg1, Object arg2, Object arg3) {
		logger.warn(arg0, arg1, arg2, arg3);
	}

	public void warn(Marker arg0, String arg1, Object... arg2) {
		logger.warn(arg0, arg1, arg2);
	}

	public void warn(Marker arg0, String arg1, Object arg2) {
		logger.warn(arg0, arg1, arg2);
	}

	public void warn(Marker arg0, String arg1, Throwable arg2) {
		logger.warn(arg0, arg1, arg2);
	}

	public void warn(Marker arg0, String arg1) {
		logger.warn(arg0, arg1);
	}

	public void warn(String arg0, Object arg1, Object arg2) {
		logger.warn(arg0, arg1, arg2);
	}

	public void warn(String arg0, Object... arg1) {
		logger.warn(arg0, arg1);
	}

	public void warn(String arg0, Object arg1) {
		logger.warn(arg0, arg1);
	}

	public void warn(String arg0, Throwable arg1) {
		logger.warn(arg0, arg1);
	}

	public void warn(String arg0) {
		logger.warn(arg0);
	}
}

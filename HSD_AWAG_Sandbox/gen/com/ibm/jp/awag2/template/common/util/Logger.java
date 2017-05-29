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

/**
 * Loggerインターフェース。
 *
 */
public interface Logger {

	void debug(String arg0, Object arg1, Object arg2);

	void debug(String arg0, Object... arg1);

	void debug(String arg0, Object arg1);

	void debug(String arg0, Throwable arg1);

	void debug(String arg0);

	void error(String arg0, Object arg1, Object arg2);

	void error(String arg0, Object... arg1);

	void error(String arg0, Object arg1);

	void error(String arg0, Throwable arg1);

	void error(String arg0);

	String getName();
	
	void info(String arg0, Object arg1, Object arg2);

	void info(String arg0, Object... arg1);

	void info(String arg0, Object arg1);

	void info(String arg0, Throwable arg1);

	void info(String arg0);

	boolean isDebugEnabled();

	boolean isErrorEnabled();

	boolean isInfoEnabled();

	boolean isTraceEnabled();

	boolean isWarnEnabled();

	void trace(String arg0, Object arg1, Object arg2);

	void trace(String arg0, Object... arg1);

	void trace(String arg0, Object arg1);

	void trace(String arg0, Throwable arg1);

	void trace(String arg0);

	void warn(String arg0, Object arg1, Object arg2);

	void warn(String arg0, Object... arg1);

	void warn(String arg0, Object arg1);

	void warn(String arg0, Throwable arg1);

	void warn(String arg0);

}
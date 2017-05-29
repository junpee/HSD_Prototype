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

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * メソッドトレース用のログを出力するインターセプター。
 *
 */
@Interceptor
public class MethodTraceLoggingInterceptor {

	/**
	 * 唯一のコンストラクタ。
	 */
	public MethodTraceLoggingInterceptor() {
	}
	
	/**
	 * Interceptしたメソッドの実行前後でログを出力する。
	 * @param ic InvocationContext
	 * @return Interceptメソッドの実行結果
	 * @throws Exception ログ出力、またはInterceptメソッドの実行に失敗した場合
	 */
	@AroundInvoke
	public Object writeMethodTraceLog(InvocationContext ic) throws Exception {
		
		Logger logger = LoggerFactory.getInstance().getLogger(this);
		
		StringBuilder targetMethodName = null;
		
		if (logger.isTraceEnabled()) {
			targetMethodName =  new StringBuilder();
			targetMethodName.append(ic.getTarget().getClass()).append("#").append(ic.getMethod().getName());
			logger.trace(new StringBuilder("[start]").append(targetMethodName).toString());
		}

		Object ret = null;
		ret = ic.proceed();
		
		if (logger.isTraceEnabled()) {
			logger.trace(new StringBuilder("[end]").append(targetMethodName).toString());
		}
		
		return ret;
		
	}

}

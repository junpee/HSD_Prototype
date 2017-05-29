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

import javax.inject.Inject;

import com.ibm.jp.awag2.common.logic.ServiceDBException.DBErrorType;
import com.ibm.jp.awag2.common.resource.RequestContext;
import com.ibm.jp.awag2.common.util.Logger;
import com.ibm.jp.awag2.common.util.LoggerFactory;

/**
 * サービスの基底クラス。
 *
 */
public class AbstractServiceBase {
	
    /** RequestContextオブジェクト */
	@Inject
	private RequestContext requestContext;
	
	/**
	 * Loggerを取得する。
	 * @return Logger
	 */
	protected Logger getLogger() {	
		return LoggerFactory.getInstance().getLogger(this);
	}

	/**
	 * RequestContextオブジェクトを取得する。
	 * @return RequestCOntextオブジェクト
	 */
	protected RequestContext getRequestContext() {
		return requestContext;
	}

	/**
	 * RequestCntextオブジェクトを設定する。
	 * @param requestContext RequestCOntextオブジェクト
	 */
	protected void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * ServiceDBExceptionを生成する。
	 * @param dbErrorType DBエラータイプ
	 * @param errorCode エラーコード
	 * @param cause 発生原因の例外オブジェクト
	 * @return ServiceDBException
	 */
	protected ServiceAppException createServiceAppException(String errorCode, Exception cause) {
		
		ServiceAppException ex = null;
		
		if (requestContext.getLocale() != null) {
			ex = new ServiceAppException(errorCode, cause, requestContext.getLocale());
		} else {
			ex = new ServiceAppException(errorCode, cause);
		}
		
		return ex;
	}
	
	/**
	 * ServiceDBExceptionを生成する。
	 * @param dbErrorType DBエラータイプ
	 * @param errorCode エラーコード
	 * @param cause 発生原因の例外オブジェクト
	 * @return ServiceDBException
	 */
	protected ServiceDBException createServiceDBException(DBErrorType dbErrorType, String errorCode, Exception cause) {
		
		ServiceDBException ex = null;
		
		if (requestContext.getLocale() != null) {
			ex = new ServiceDBException(dbErrorType, errorCode, cause, requestContext.getLocale());
		} else {
			ex = new ServiceDBException(dbErrorType, errorCode, cause);
		}
		
		return ex;
	}
	
}

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
package com.ibm.jp.awag.rest.common.provider;

import java.io.EOFException;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.ibm.jp.awag.rest.common.logic.ErrorContainer;
import com.ibm.jp.awag.rest.common.logic.ErrorInfo;
import com.ibm.jp.awag.rest.common.resource.HTTPStatusConstants;
import com.ibm.jp.awag.rest.common.resource.RequestContext;
import com.ibm.jp.awag.rest.common.util.Logger;
import com.ibm.jp.awag.rest.common.util.LoggerFactory;

/**
 * デフォルトExceptionMapper。
 * 業務例外、DB例外、入力チェック例外以外の例外をResponseオブジェクトにマップする。
 *
 */
@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

	/** HTTP Headerオブジェクト */
    @Context
    private HttpHeaders headers;

    /** RequestContextオブジェクト */
	@Inject
	private RequestContext requestContext;
	
    /**
     * 発生した例外をResponseオブジェクトにマップする。
     */
	@Override
	public Response toResponse(Exception exception) {

		Response res = null;
		ResponseBuilder rb = null;

		Logger logger = LoggerFactory.getLogger(this);
		logger.info("ExceptionMapper catched", exception.getMessage());	
		logger.debug("ExceptionMapper catched", exception);	

		ErrorContainer ec = null;

		String exceptionClassName = exception.getClass().getSimpleName();

		if (exception instanceof WebApplicationException) {
			res = ((WebApplicationException)exception).getResponse();
		} else {
			// JSONフォーマットの異常
			if (exceptionClassName.contains("JsonMappingException") || exceptionClassName.contains("JsonParseException") || exception instanceof IllegalArgumentException) {
				ec = new ErrorContainer("_EDE00001", requestContext.getLocale());
				ec.addError(new ErrorInfo(exceptionClassName, exception.getLocalizedMessage()));
				rb = Response.status(HTTPStatusConstants.BAD_REQUEST);
			}
			// 存在しない項目の入力
			else if (exceptionClassName.contains("UnrecognizedPropertyException")) {
				ec = new ErrorContainer("_EDE00002", requestContext.getLocale());
				ec.addError(new ErrorInfo(exceptionClassName, exception.getLocalizedMessage()));
				rb = Response.status(HTTPStatusConstants.BAD_REQUEST);
			}
			// 不完全なリクエスト・メッセージ
			else if (exception instanceof EOFException) {
				ec = new ErrorContainer("_EDE00003", requestContext.getLocale());
				ec.addError(new ErrorInfo(exceptionClassName, exception.getLocalizedMessage()));
				rb = Response.status(HTTPStatusConstants.BAD_REQUEST);
			// その他の例外
			} else {
				logger.error("ExceptionMapper catched", exception);
				Throwable cause = exception.getCause();
				if (cause != null && cause instanceof PersistenceException) {
					ec = new ErrorContainer("_EDB00008", requestContext.getLocale());
					rb = Response.status(HTTPStatusConstants.INTERNAL_SERVER_ERROR);
				} else {
					ec = new ErrorContainer("_EDE00004", requestContext.getLocale());
					rb = Response.status(HTTPStatusConstants.INTERNAL_SERVER_ERROR);
				}
			}

			rb.type(headers.getMediaType()).entity(ec);
			res = rb.build();
		}

		return res;
	}
}

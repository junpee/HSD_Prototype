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

import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.ibm.jp.awag.rest.common.logic.ServiceDBException;
import com.ibm.jp.awag.rest.common.resource.HTTPStatusConstants;
import com.ibm.jp.awag.rest.common.util.Logger;
import com.ibm.jp.awag.rest.common.util.LoggerFactory;

/**
 * ServiceDBExceptionのExcepptionMapper。
 *
 */
@Provider
public class ServiceDBExceptionMapper implements ExceptionMapper<ServiceDBException> {
	
	/**
	 * ServiceDBException（DB例外）をResponseオブジェクトにマップする。
	 */
	@Override
	public Response toResponse(ServiceDBException exception) {

		Logger logger = LoggerFactory.getLogger(this);
		logger.info("ExceptionMapper catched" + exception.getErrorContainer().getCode() + exception.getErrorContainer().getMessage());	
		if (exception.getErrorContainer().getErrors() != null) {
			logger.debug("ExceptionMapper catched" + exception.getErrorContainer().getErrors().stream().map(s -> s.getCode() + s.getMessage()).collect(Collectors.joining(", ")));	
		}
		logger.debug("ExceptionMapper catched", exception);
		
		int httpStatus;
		
		switch (exception.getErrorType()) {
		case OPTIMISTICLOCK:
			httpStatus = HTTPStatusConstants.CONFLICT;
			break;
		case DUPPLICATE:
			httpStatus = HTTPStatusConstants.CONFLICT;
			break;
		case DELETED:
			httpStatus = HTTPStatusConstants.GONE;
			break;
		default:
			httpStatus = HTTPStatusConstants.BAD_REQUEST;
			logger.error("ExceptionMapper catched" + exception.getErrorContainer().getCode() + exception.getErrorContainer().getMessage());	
			if (exception.getErrorContainer().getErrors() != null) {
				logger.error("ExceptionMapper catched" + exception.getErrorContainer().getErrors().stream().map(s -> s.getCode() + s.getMessage()).collect(Collectors.joining(", ")));	
			}
			break;
		}

		ResponseBuilder rb = Response.status(httpStatus);
		rb.entity(exception.getErrorContainer());
		return rb.build();
	}

}

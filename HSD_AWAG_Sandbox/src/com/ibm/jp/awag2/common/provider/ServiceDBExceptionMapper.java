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
package com.ibm.jp.awag2.common.provider;

import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.ibm.jp.awag2.common.logic.ServiceDBException;
import com.ibm.jp.awag2.common.util.Logger;
import com.ibm.jp.awag2.common.util.LoggerFactory;

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

		Logger logger = LoggerFactory.getInstance().getLogger(this);
		logger.info("ExceptionMapper catched" + exception.getErrorContainer().getCode() + exception.getErrorContainer().getMessage());	
		if (exception.getErrorContainer().getErrors() != null) {
			logger.debug("ExceptionMapper catched" + exception.getErrorContainer().getErrors().stream().map(s -> s.getCode() + s.getMessage()).collect(Collectors.joining(", ")));	
		}
		logger.debug("ExceptionMapper catched", exception);
		
		Status httpStatus;
		
		switch (exception.getErrorType()) {
		case OPTIMISTIC_LOCK:
			httpStatus = Status.CONFLICT;
			break;
		case DUPPLICATE:
			httpStatus = Status.CONFLICT;
			break;
		case DELETED:
			httpStatus = Status.GONE;
			break;
		case NOT_FOUND:
			httpStatus = Status.NOT_FOUND;
			break;
		default:
			httpStatus = Status.BAD_REQUEST;
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

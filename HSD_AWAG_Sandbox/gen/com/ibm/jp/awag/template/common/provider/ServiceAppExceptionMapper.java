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

import com.ibm.jp.awag.rest.common.logic.ServiceAppException;
import com.ibm.jp.awag.rest.common.resource.HTTPStatusConstants;
import com.ibm.jp.awag.rest.common.util.Logger;
import com.ibm.jp.awag.rest.common.util.LoggerFactory;

/**
 * ServiceAppException（業務例外）のExceptionMapper。
 *
 */
@Provider
public class ServiceAppExceptionMapper implements
		ExceptionMapper<ServiceAppException> {

	/**
	 * 発生したServiceAppExceptionをResponseオブジェクトにマップする。
	 */
	@Override
	public Response toResponse(ServiceAppException exception) {
		
		Logger logger = LoggerFactory.getLogger(this);
		logger.info("ExceptionMapper catched" + exception.getErrorContainer().getCode() + exception.getErrorContainer().getMessage());	
		if (exception.getErrorContainer().getErrors() != null) {
			logger.debug("ExceptionMapper catched" + exception.getErrorContainer().getErrors().stream().map(s -> s.getCode() + s.getMessage()).collect(Collectors.joining(", ")));	
		}
		logger.debug("ExceptionMapper catched", exception);
		
		ResponseBuilder rb = Response.status(HTTPStatusConstants.UNPROCESSABLE_ENTITY);
		rb.entity(exception.getErrorContainer());
		return rb.build();
	}

}

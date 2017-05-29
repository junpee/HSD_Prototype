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

import com.ibm.jp.awag2.common.util.Logger;
import com.ibm.jp.awag2.common.util.LoggerFactory;
import com.ibm.jp.awag2.common.validator.ValidationException;

/**
 * ValidationException（入力チェック例外）のExceptionMapper
 *
 */
@Provider
public class ValidationExceptionMapper implements
		ExceptionMapper<ValidationException> {

	/**
	 * ValidationExceptionをResponseオブジェクトにマップする。
	 */
	@Override
	public Response toResponse(ValidationException exception) {
		
		Logger logger = LoggerFactory.getInstance().getLogger(this);
		logger.debug("ExceptionMapper catched" + exception.getErrorContainer().getCode() + exception.getErrorContainer().getMessage());	
		if (exception.getErrorContainer().getErrors() != null) {
			logger.debug("ExceptionMapper catched" + exception.getErrorContainer().getErrors().stream().map(s -> s.getCode() + s.getMessage()).collect(Collectors.joining(", ")));	
		}
		logger.debug("ExceptionMapper catched", exception);
		
		ResponseBuilder rb = Response.status(Status.BAD_REQUEST);
		rb.entity(exception.getErrorContainer());
		return rb.build();
	}

}

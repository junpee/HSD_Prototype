/** Generated by AWAG ver.2.0.0.GIT-e0917bf.BUILD-20170421-1451+0900 */

package com.ibm.salessample.resource.impl;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import com.ibm.jp.awag2.common.logic.ServiceAppException;
import com.ibm.jp.awag2.common.logic.ServiceDBException;
import com.ibm.jp.awag2.common.logic.ServiceIF;
import com.ibm.jp.awag2.common.util.MethodTraceLoggingInterceptor;
import com.ibm.salessample.dto.UpdateContactInputDTO;
import com.ibm.salessample.dto.UpdateContactOutputDTO;


/**
* コンタクト履歴更新 リソースクラス（UpdateContact）
*
*/ 
 @Interceptors(MethodTraceLoggingInterceptor.class)
@Dependent
public class UpdateContactResource {

	/** コンタクト履歴更新 Service（UpdateContact） */
	@Inject
	private ServiceIF<UpdateContactInputDTO, UpdateContactOutputDTO> service;

	public Response execute(UpdateContactInputDTO input) throws ServiceDBException, ServiceAppException {
		
		// CUSTOMIZE: ビジネスロジック前処理を実装
		
		UpdateContactOutputDTO result = service.execute(input);

		// CUSTOMIZE: ビジネスロジック後処理を実装
		
		ResponseBuilder rb = Response.ok(result);
	
		return rb.build();
	}
}


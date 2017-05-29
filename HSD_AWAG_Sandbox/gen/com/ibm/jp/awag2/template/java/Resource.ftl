<#--
Automated web application generator

Licensed Materials - Property of IBM
"Restricted Materials of IBM"
IPSC : 6949-63S
(C) Copyright IBM Japan, Ltd. 2016 All Rights Reserved.
(C) Copyright IBM Corp. 2016 All Rights Reserved.
US Government Users Restricted Rights -
Use, duplication or disclosure restricted
by GSA ADP Schedule Contract with IBM Corp.
 -->
<#include "includeHeader.ftl">

package ${apiDefinition.javaPackage}.resource.impl;

<#compress>
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
<#if apiDefinition.method == "GET">import javax.ws.rs.NotFoundException;</#if>
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
</#compress>

import com.ibm.jp.awag2.common.logic.ServiceAppException;
import com.ibm.jp.awag2.common.logic.ServiceDBException;
import com.ibm.jp.awag2.common.logic.ServiceIF;
import com.ibm.jp.awag2.common.util.MethodTraceLoggingInterceptor;
import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}InputDTO;
import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}OutputDTO;


<#compress>
/**
 * <#if apiDefinition.nameLocal??>${apiDefinition.nameLocal} リソースクラス（${apiDefinition.name}）<#else>${apiDefinition.name} リソースクラス</#if>
 *
 */
 </#compress>
 
 @Interceptors(MethodTraceLoggingInterceptor.class)
@Dependent
public class ${apiDefinition.name?cap_first}Resource {

	/** <#if apiDefinition.nameLocal??>${apiDefinition.nameLocal} Service（${apiDefinition.name}）<#else>${apiDefinition.name} Service</#if> */
	@Inject
	private ServiceIF<${apiDefinition.name?cap_first}InputDTO, ${apiDefinition.name?cap_first}OutputDTO> service;

	public Response execute(${apiDefinition.name?cap_first}InputDTO input) throws ServiceDBException, ServiceAppException {
		
		// CUSTOMIZE: ビジネスロジック前処理を実装
		
		${apiDefinition.name?cap_first}OutputDTO result = service.execute(input);

		// CUSTOMIZE: ビジネスロジック後処理を実装
		
<#if apiDefinition.method == "GET">

		ResponseBuilder rb = null;
		if (result == null) {
			rb = Response.status(Status.NO_CONTENT);
		} else {
			rb = Response.ok(result);
		}
		
<#else>
		ResponseBuilder rb = Response.ok(result);
</#if>
	
		return rb.build();
	}
}


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
<#assign isPathIncludeKey = apiDefinition.path?contains("{_id_}")/>

package ${apiDefinition.javaPackage}.resource;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.${apiDefinition.method};
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.ibm.jp.awag2.common.logic.ServiceAppException;
import com.ibm.jp.awag2.common.logic.ServiceDBException;
import com.ibm.jp.awag2.common.resource.ResourceManagerBase;
import com.ibm.jp.awag2.common.validator.ValidationException;
import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}InputDTO;
import ${apiDefinition.javaPackage}.resource.impl.${apiDefinition.name?cap_first}Resource;
 <#assign keyListStr><#if isPathIncludeKey>
 <#list apiDefinition.accessEntityList?values as accessEntity><#list accessEntity.mappedEntityDefinition.pkFields as column>{${column.name?lower_case}}<#sep>.</#list></#list><#t><#t>
 </#if></#assign>
 
 <#compress>
/**
 * <#if apiDefinition.nameLocal??>${apiDefinition.nameLocal} リソース・マネージャクラス（${apiDefinition.name}）<#else>${apiDefinition.name} リソース・マネージャクラス</#if>
<#if apiDefinition.description??> * ${apiDefinition.description}</#if>
 */
 </#compress>
 
@Path("${apiDefinition.path?replace("{_id_}", keyListStr)}")
public class ${apiDefinition.name?cap_first}ResourceManager extends ResourceManagerBase {
	
	@Inject 
	private ${apiDefinition.name?cap_first}Resource ${apiDefinition.name?lower_case}Resource;
	
	@${apiDefinition.method}
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	public Response execute(<#if apiDefinition.method?? && apiDefinition.method == "GET">@BeanParam </#if>${apiDefinition.name?cap_first}InputDTO input) throws ServiceDBException, ServiceAppException, ValidationException {
		
		// 単項目入力チェック
		super.validateBean(input);
		
		Response response = ${apiDefinition.name?lower_case}Resource.execute(input);

		<#if apiDefinition.method?? && apiDefinition.method == "GET"><#t>
		ResponseBuilder rb = null;
		if (!response.hasEntity()) {
			return response.status(Status.NO_CONTENT).build();
		} else {
			return response;
		}
		<#elseif apiDefinition.method?? && (  apiDefinition.method == "POST") ><#t>
		return response.status(Status.OK).build();
		<#elseif apiDefinition.method?? && (  apiDefinition.method == "PUT" || apiDefinition.method == "DELETE" ) ><#t>
		return response.status(Status.NO_CONTENT).build();
		</#if>
	}
}

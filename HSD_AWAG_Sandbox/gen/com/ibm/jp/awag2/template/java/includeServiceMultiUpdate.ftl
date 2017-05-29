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
 	<#include "includeServiceMethodCommentProcessInput.ftl"> 
	protected Map<String, Object> processInput(${apiDefinition.name?cap_first}InputDTO ${apiDefinition.name?lower_case}inputdto)  throws ServiceDBException, ServiceAppException {
		
		Map<String, Object> daoParameterMap = new HashMap<>();

		// InputDTOからDAOParameterへの詰替
<@printMappingDTOandEntity _inout="in" _target=apiDefinition _parent="none" _recurseCnt=1 />

		return daoParameterMap;
	}				
				
 	<#include "includeServiceMethodCommentPersist.ftl">		
	protected Map<String, Object> persist(Map<String, Object> daoParameterMap)  throws ServiceDBException, ServiceAppException  {

		// DAO呼び出し
		Map<String, Object> resultMap = new HashMap<>();
										
		// DAOパラメータの取得
<#list apiDefinition.accessEntityList?values as accessEntity>
<#if accessEntity?? && accessEntity.mappedEntityDefinition??>
		List<${accessEntity.entityName?cap_first}> ${accessEntity.entityName?lower_case} = (List<${accessEntity.entityName?cap_first}>) daoParameterMap.get(DAO_PARAM_MAP_KEY_${accessEntity.entityName?upper_case});
<#else>
	<#list accessEntity.accessEntityList?values as childAccessEntity>
	<#if childAccessEntity?? && childAccessEntity.mappedEntityDefinition??>
		List<${childAccessEntity.entityName?cap_first}> ${childAccessEntity.entityName?lower_case} = (List<${childAccessEntity.entityName?cap_first}>) daoParameterMap.get(DAO_PARAM_MAP_KEY_${childAccessEntity.entityName?upper_case});
	</#if>
	</#list>
</#if>
</#list>

		// DAO呼び出し
<#list apiDefinition.accessEntityList?values as accessEntity>
<#if accessEntity?? && accessEntity.mappedEntityDefinition??>
		${accessEntity.entityName?lower_case}Dao.updateList(${accessEntity.entityName?lower_case});
<#else>
	<#list accessEntity.accessEntityList?values as childAccessEntity>
	<#if childAccessEntity?? && childAccessEntity.mappedEntityDefinition??>
		${childAccessEntity.entityName?lower_case}Dao.updateList(${childAccessEntity.entityName?lower_case});
	</#if>
	</#list>
</#if>
</#list>

		return resultMap;
	}
	
 	<#include "includeServiceMethodCommentProcessOutput.ftl">	
	protected ${apiDefinition.name?cap_first}OutputDTO processOutput(Map<String, Object> resultMap)  throws ServiceDBException, ServiceAppException {

		return new ${apiDefinition.name?cap_first}OutputDTO();
		
	}
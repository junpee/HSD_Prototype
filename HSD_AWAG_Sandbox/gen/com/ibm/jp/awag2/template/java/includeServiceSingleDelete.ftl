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
		${accessEntity.entityName?cap_first} ${accessEntity.entityName?lower_case} = (${accessEntity.entityName?cap_first}) daoParameterMap.get(DAO_PARAM_MAP_KEY_${accessEntity.entityName?upper_case});
</#list>

		// DAO呼び出し
<#list apiDefinition.accessEntityList?values as accessEntity>
		${accessEntity.entityName?lower_case}Dao.delete(${accessEntity.entityName?lower_case});
</#list>

		return resultMap;
	}
	
 	<#include "includeServiceMethodCommentProcessOutput.ftl">
	protected ${apiDefinition.name?cap_first}OutputDTO processOutput(Map<String, Object> resultMap)  throws ServiceDBException, ServiceAppException {

		return new ${apiDefinition.name?cap_first}OutputDTO();
		
	}

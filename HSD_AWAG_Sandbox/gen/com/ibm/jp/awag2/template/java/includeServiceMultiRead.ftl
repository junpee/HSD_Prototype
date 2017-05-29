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
<#list apiDefinition.accessEntityList?values as accessEntity>
		DAOParameter daoParamFor${accessEntity.entityName?cap_first} = (DAOParameter) Optional.ofNullable(daoParameterMap.get(DAO_PARAM_MAP_KEY_${accessEntity.entityName?upper_case})).orElse(new DAOParameter());
</#list>

<#list apiDefinition.inputItemDefinitions as inputItem>
	<#if !inputItem.multiple><#t>
		if (daoParamFor${inputItem.mappedEntityName?cap_first}.get("${inputItem.mappedFieldName?lower_case}") == null  && ${apiDefinition.name?lower_case}inputdto.get${inputItem.name?cap_first}() != null) daoParamFor${inputItem.mappedEntityName?cap_first}.set("${inputItem.mappedFieldName?lower_case}", <#if inputItem.options?? && inputItem.options.select_where_op??>SelectWhereOperator.${inputItem.options.select_where_op}, </#if>${apiDefinition.name?lower_case}inputdto.get${inputItem.name?cap_first}());
	</#if>
</#list>
<#list apiDefinition.accessEntityList?values as accessEntity>
		daoParameterMap.put(DAO_PARAM_MAP_KEY_${accessEntity.entityName?upper_case}, daoParamFor${accessEntity.entityName?cap_first});
</#list>

		// Join対象Entity設定
<#list apiDefinition.accessEntityList?values as accessEntity>
<#if accessEntity.accessEntityList?? && accessEntity.accessEntityList?keys?size gt 0><#t>
		List<JoinEntity> joinEntityListFor${accessEntity.entityName?cap_first} = new ArrayList<>();
		<#list accessEntity.accessEntityList?values as childAccessEntity>
		JoinEntity joinEntityFor${childAccessEntity.entityName?cap_first} = new JoinEntity("<@propertyName orgName=childAccessEntity.entityName />");
				<#if childAccessEntity.accessEntityList?? && childAccessEntity.accessEntityList?keys?size gt 0><#t>
				<#list childAccessEntity.accessEntityList?values as grandSonAccessEntity><#t>
		joinEntityFor${childAccessEntity.entityName?cap_first}.addJoinEntity("<@propertyName orgName=grandSonAccessEntity.entityName />");		
				</#list>
				</#if>
		joinEntityListFor${accessEntity.entityName?cap_first}.add(joinEntityFor${childAccessEntity.entityName?cap_first});
		</#list>
		daoParameterMap.put(DAO_PARAM_MAP_KEY_${accessEntity.entityName?upper_case}_JOIN, joinEntityListFor${accessEntity.entityName?cap_first});
</#if>
</#list>

		// AND,OR検索条件
		daoParameterMap.put("_isInclusiveOr", ${apiDefinition.name?lower_case}inputdto.isInclusiveOr());

		// 最大取得件数
		daoParameterMap.put("_maxResults", ${apiDefinition.name?lower_case}inputdto.getMaxResults());
				
		return daoParameterMap;
	}
	
 	<#include "includeServiceMethodCommentPersist.ftl">	
	protected Map<String, Object> persist(Map<String, Object> daoParameterMap)  throws ServiceDBException, ServiceAppException  {
		
		// DAOパラメータの取得
<#list apiDefinition.accessEntityList?values as accessEntity>
		DAOParameter daoParamFor${accessEntity.entityName?cap_first} = (DAOParameter) daoParameterMap.get(DAO_PARAM_MAP_KEY_${accessEntity.entityName?upper_case});
</#list>		

		// Join対象Entityの取得
<#list apiDefinition.accessEntityList?values as accessEntity>
<#if accessEntity.accessEntityList??&& accessEntity.accessEntityList?keys?size gt 0>
		List<JoinEntity> joinEntityListFor${accessEntity.entityName?cap_first} = (List<JoinEntity>) daoParameterMap.get(DAO_PARAM_MAP_KEY_${accessEntity.entityName?upper_case}_JOIN);
</#if>
</#list>

		// DAO呼び出し
		Map<String, Object> resultMap = new HashMap<>();
		
<#list apiDefinition.accessEntityList?values as accessEntity>
		List<${accessEntity.entityName?cap_first}> ${accessEntity.entityName?lower_case}List = ${accessEntity.entityName?lower_case}Dao.findList(daoParamFor${accessEntity.entityName?cap_first}, <#if accessEntity.accessEntityList?? && accessEntity.accessEntityList?keys?size gt 0>joinEntityListFor${accessEntity.entityName?cap_first}<#else>null</#if>, (boolean) daoParameterMap.get("_isInclusiveOr"), (Integer) daoParameterMap.get("_maxResults"));
		if (!${accessEntity.entityName?lower_case}List.isEmpty()) {
			resultMap.put(RESULT_MAP_KEY_${accessEntity.entityName?upper_case}, ${accessEntity.entityName?lower_case}List);
		}
</#list>		

		return resultMap;
	}
	
 	<#include "includeServiceMethodCommentProcessOutput.ftl">	
	protected ${apiDefinition.name?cap_first}OutputDTO processOutput(Map<String, Object> resultMap)  throws ServiceDBException, ServiceAppException {

		if (resultMap.isEmpty()) {
			return null;
		}
		
		// DAO実行結果の取得
<#list apiDefinition.accessEntityList?values as accessEntity>
		List<${accessEntity.entityName?cap_first}> ${accessEntity.entityName?lower_case}List = (List<${accessEntity.entityName?cap_first}>) resultMap.get(RESULT_MAP_KEY_${accessEntity.entityName?upper_case});
</#list>

		// DAO実行結果からOutputDTOへの詰替
<@printMappingDTOandEntity _inout="out" _target=apiDefinition _parent="none" _recurseCnt=1 />
		return ${apiDefinition.name?lower_case}outputdtoTmp;
	}
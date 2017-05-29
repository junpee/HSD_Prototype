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

		// InputDTOからPKへの詰替		
<#list apiDefinition.accessEntityList?values as accessEntity>
		${accessEntity.entityName?cap_first}PK ${accessEntity.entityName?lower_case}Pk = new ${accessEntity.entityName?cap_first}PK();
</#list>

<#assign hash={} />
<#list apiDefinition.inputItemDefinitions as inputItem>
	<#if inputItem.modelType == "DataItem">
	  <#if inputItem.mappedEntityFieldDefinition??>
		<#if inputItem.mappedEntityFieldDefinition.pk>
		${inputItem.mappedEntityName?lower_case}Pk.set${inputItem.mappedFieldName?cap_first}(${apiDefinition.name?lower_case}inputdto.get${inputItem.name?cap_first}());
		<#else>
		${inputItem.mappedEntityName?lower_case}.set${inputItem.mappedFieldName?cap_first}(${apiDefinition.name?lower_case}inputdto.get${inputItem.name?cap_first}());
		</#if>
	  </#if>
	</#if>
	<#if inputItem.mappedEntityName??>
		<#assign hash = hash + {inputItem.mappedEntityName, "dummy"}>
	</#if>
</#list>
		<#list hash?keys as name>
		
		daoParameterMap.put(DAO_PARAM_MAP_KEY_${name?upper_case}_PK, ${name?lower_case}Pk);		
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

		return daoParameterMap;
	}
	
 	<#include "includeServiceMethodCommentPersist.ftl">
	protected Map<String, Object> persist(Map<String, Object> daoParameterMap)  throws ServiceDBException, ServiceAppException  {
		
		// DAOパラメータの取得
<#list apiDefinition.accessEntityList?values as accessEntity>
		${accessEntity.entityName?cap_first}PK ${accessEntity.entityName?lower_case}Pk = (${accessEntity.entityName?cap_first}PK) daoParameterMap.get(DAO_PARAM_MAP_KEY_${accessEntity.entityName?upper_case}_PK);
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

		Optional<${accessEntity.entityName?cap_first}> ${accessEntity.entityName?lower_case} = ${accessEntity.entityName?lower_case}Dao.find(${accessEntity.entityName?lower_case}Pk, <#if accessEntity.accessEntityList?? && accessEntity.accessEntityList?keys?size gt 0>joinEntityListFor${accessEntity.entityName?cap_first}<#else>null</#if>);
		${accessEntity.entityName?lower_case}.ifPresent(value -> resultMap.put(RESULT_MAP_KEY_${accessEntity.entityName?upper_case}, ${accessEntity.entityName?lower_case}.get()));
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
		${accessEntity.entityName?cap_first} ${accessEntity.entityName?lower_case} = (${accessEntity.entityName?cap_first}) resultMap.get(RESULT_MAP_KEY_${accessEntity.entityName?upper_case});
</#list>
				
		// DAO実行結果からOutputDTOへの詰替
<@printMappingDTOandEntity _inout="out" _target=apiDefinition _parent="none" _recurseCnt=1 />

		return ${apiDefinition.name?lower_case}outputdtoTmp;
	}
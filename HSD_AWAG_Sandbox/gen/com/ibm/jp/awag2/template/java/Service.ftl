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
<#include "includeServiceMacro.ftl">

package ${apiDefinition.javaPackage}.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

<#compress>
import com.ibm.jp.awag2.common.dao.DAOBase.SelectWhereOperator;
import com.ibm.jp.awag2.common.dao.DAOParameter;
import com.ibm.jp.awag2.common.entity.JoinEntity;
import com.ibm.jp.awag2.common.logic.ServiceAppException;
import com.ibm.jp.awag2.common.logic.ServiceBase;
import com.ibm.jp.awag2.common.logic.ServiceDBException;
import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}InputDTO;
import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}OutputDTO;
<#list apiDefinition.inputItemDefinitions as inputItem>
<#if inputItem.modelType=="Entity">import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}InputDTO${inputItem.name?cap_first};
<#list inputItem.itemDefinitions as childInputItem>
<#if childInputItem.modelType=="Entity">import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}InputDTO${childInputItem.name?cap_first};
<#list childInputItem.itemDefinitions as grandsonInputItem>
<#if grandsonInputItem.modelType=="Entity">import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}InputDTO${grandsonInputItem.name?cap_first};</#if>
</#list>
</#if>
</#list>
</#if>
</#list>
<#list apiDefinition.outputItemDefinitions as outputItem>
<#if outputItem.modelType=="Entity">import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}OutputDTO${outputItem.name?cap_first};
<#list outputItem.itemDefinitions as childOutputItem>
<#if childOutputItem.modelType=="Entity">import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}OutputDTO${childOutputItem.name?cap_first};
<#list childOutputItem.itemDefinitions as grandsonOutputItem>
<#if grandsonOutputItem.modelType=="Entity">import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}OutputDTO${grandsonOutputItem.name?cap_first};</#if>
</#list>
</#if>
</#list>
</#if>
</#list>
<#list apiDefinition.accessEntityList?values as accessEntity>
<#if accessEntity??>
<#if accessEntity.mappedEntityDefinition??>
import ${tableCategory.javaPackage}.dao.impl.${accessEntity.mappedEntityDefinition.name?cap_first}CustomizeDAO;
import ${tableCategory.javaPackage}.entity.${accessEntity.mappedEntityDefinition.name?cap_first};
import ${tableCategory.javaPackage}.entity.${accessEntity.mappedEntityDefinition.name?cap_first}PK;
</#if>
	<#if accessEntity.accessEntityList??>
	<#list accessEntity.accessEntityList?values as childAccessEntity>
	<#if childAccessEntity?? && childAccessEntity.mappedEntityDefinition??>
import ${tableCategory.javaPackage}.dao.impl.${childAccessEntity.mappedEntityDefinition.name?cap_first}CustomizeDAO;
import ${tableCategory.javaPackage}.entity.${childAccessEntity.mappedEntityDefinition.name?cap_first};
import ${tableCategory.javaPackage}.entity.${childAccessEntity.mappedEntityDefinition.name?cap_first}PK;
	<#if childAccessEntity.accessEntityList??>
	<#list childAccessEntity.accessEntityList?values as grandsonAccessEntity>
	<#if grandsonAccessEntity?? && grandsonAccessEntity.mappedEntityDefinition??>
import ${tableCategory.javaPackage}.dao.impl.${grandsonAccessEntity.mappedEntityDefinition.name?cap_first}CustomizeDAO;
import ${tableCategory.javaPackage}.entity.${grandsonAccessEntity.mappedEntityDefinition.name?cap_first};
import ${tableCategory.javaPackage}.entity.${grandsonAccessEntity.mappedEntityDefinition.name?cap_first}PK;	
	</#if>
	</#list></#if>
	</#if>
	</#list></#if>
</#if>
</#list>
<#list apiDefinition.inputItemDefinitions as inputItem>
	<#if inputItem.multiple>
import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}InputDTO${inputItem.name?cap_first};
	</#if>
</#list>
<#list apiDefinition.outputItemDefinitions as outputItem>
	<#if outputItem.multiple>
import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}OutputDTO${outputItem.name?cap_first};		
		<#list outputItem.itemDefinitions as childOutputItem>	
			<#if childOutputItem.multiple>
import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}OutputDTO${childOutputItem.name?cap_first};		
			</#if>
		</#list>
	</#if>
</#list>	
</#compress>


<#compress>
/**
 * <#if apiDefinition.nameLocal??>${apiDefinition.nameLocal} サービスクラス（${apiDefinition.name}）<#else>${apiDefinition.name} サービスクラス</#if>
<#if apiDefinition.description??> * ${apiDefinition.description}</#if>
 */
 </#compress>
 
@Stateless
public class ${apiDefinition.name?cap_first}Service extends ServiceBase {

<#if apiDefinition.apiType == "SingleRead" || apiDefinition.apiType == "MultiRead"><#t>
<#list apiDefinition.accessEntityList?values as accessEntity>
<#if accessEntity?? && accessEntity.mappedEntityDefinition??>
	/** RESULTマップのキー（${accessEntity.entityName}） */
	protected static final String RESULT_MAP_KEY_${accessEntity.entityName?upper_case} = "${accessEntity.entityName?lower_case}<#if apiDefinition.apiType == "MultiRead">List</#if>";
</#if>
</#list>
</#if>

<#list apiDefinition.accessEntityList?values as accessEntity>
<#if accessEntity?? && accessEntity.mappedEntityDefinition??>
	/** DAOパラメーターマップのキー（${accessEntity.entityName}） */
	protected static final String DAO_PARAM_MAP_KEY_${accessEntity.entityName?upper_case} = "${accessEntity.entityName?lower_case}";

<#if apiDefinition.apiType == "SingleRead"><#t>
	/** DAOパラメーターマップのキー（${accessEntity.entityName}のPK） */
	protected static final String DAO_PARAM_MAP_KEY_${accessEntity.entityName?upper_case}_PK = "${accessEntity.entityName?lower_case}Pk";
</#if>

	/** DAOパラメーターマップのキー（${accessEntity.entityName}のJoinList） */
	protected static final String DAO_PARAM_MAP_KEY_${accessEntity.entityName?upper_case}_JOIN = "joinEntityListFor${accessEntity.entityName?cap_first}";
<#else>
	<#list accessEntity.accessEntityList?values as childAccessEntity>
	<#if childAccessEntity?? && childAccessEntity.mappedEntityDefinition??>
	protected static final String DAO_PARAM_MAP_KEY_${childAccessEntity.entityName?upper_case} = "${childAccessEntity.entityName?lower_case}";
	</#if>
	</#list>
</#if>
</#list>
<#list apiDefinition.accessEntityList?values as accessEntity>
<#if accessEntity?? && accessEntity.mappedEntityDefinition??>

	/** ${accessEntity.entityName?upper_case}テーブルDAO */
	@Inject
	protected ${accessEntity.entityName?cap_first}CustomizeDAO ${accessEntity.entityName?lower_case}Dao;
<#else>
<#list accessEntity.accessEntityList?values as childAccessEntity>
<#if childAccessEntity?? && childAccessEntity.mappedEntityDefinition??>

	/** ${childAccessEntity.entityName?upper_case}テーブルDAO */
	@Inject
	private ${childAccessEntity.entityName?cap_first}CustomizeDAO ${childAccessEntity.entityName?lower_case}Dao;	
</#if>
</#list>
</#if>
</#list>
	
<#-- API種別毎ロジック -->
<#if apiDefinition.apiType == "SingleCreate"><#include "includeServiceSingleCreate.ftl"></#if>
<#if apiDefinition.apiType == "MultiCreate"><#include "includeServiceMultiCreate.ftl"></#if>
<#if apiDefinition.apiType == "SingleRead"><#include "includeServiceSingleRead.ftl"></#if>
<#if apiDefinition.apiType == "MultiRead"><#include "includeServiceMultiRead.ftl"></#if>
<#if apiDefinition.apiType == "SingleUpdate"><#include "includeServiceSingleUpdate.ftl"></#if>
<#if apiDefinition.apiType == "MultiUpdate"><#include "includeServiceMultiUpdate.ftl"></#if>
<#if apiDefinition.apiType == "SingleDelete"><#include "includeServiceSingleDelete.ftl"></#if>
<#if apiDefinition.apiType == "MultiDelete"><#include "includeServiceMultiDelete.ftl"></#if>
}
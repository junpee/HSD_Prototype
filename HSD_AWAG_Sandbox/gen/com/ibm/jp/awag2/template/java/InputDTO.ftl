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

package ${apiDefinition.javaPackage}.dto;

import java.util.List;

<#compress>
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import com.ibm.jp.awag2.common.entity.DisplayName;
import com.ibm.jp.awag2.common.validator.DateTime;
import com.ibm.jp.awag2.common.validator.Number;
import com.ibm.jp.awag2.common.validator.StringFormat;
</#compress>

import com.ibm.jp.awag2.common.dto.DTOBase;

/**
 * ${apiDefinition.name}<#if itemName??>.${itemName?cap_first}</#if>のInput DTOクラス。
 *
 */
<#-- クラスアノテーション -->

<#-- クラス -->
public class ${apiDefinition.name?cap_first}InputDTO<#if itemName??>${itemName?cap_first}</#if>  extends DTOBase {
<#-- フィールド -->
<#list itemDefinitions  as input>
<#if input.modelType?? && input.modelType == "DataItem">

	/** ${input.nameLocal}(${input.name}) */
	<#if input.paramType?? && (input.paramType != "Default") ><#t>
	@${input.paramType}(value = "${input.name}")
	</#if>
	<#if input.notNull><#t>
	@NotNull
	</#if><#t>
	<#if input.formatType??><#t>
		<#if input.formatType == "DECIMAL"><#t>
	@Number(type = Number.Type.DECIMAL, integer=${input.integer?c}, fraction = ${input.fraction?c})<#rt>
		<#elseif input.formatType == "INTEGER" || input.formatType == "SMALLINT" || input.formatType = "BIGINT" || input.formatType = "FLOAT"><#t>
			${"\t"}<@compress single_line=true><#t>
			@Number(type = Number.Type.INTEGER<#rt>
			<#if input.minLength??>
				<#if input.minLength != 0>
				, minLength = ${input.minLength?c}<#t>
				</#if>
			</#if>
			<#if input.maxLength??>
				<#if input.maxLength != 0>
				, maxLength = ${input.maxLength?c}<#t>
				</#if>
			</#if>
			<#t>
			)<#t>
			</@compress><#rt>
		<#elseif input.formatType == "DATE"><#t>
	@DateTime(type=DateTime.Type.DATE)<#rt>
		<#elseif input.formatType == "TIMESTAMP"><#t>
	@DateTime(type=DateTime.Type.TIMESTAMP)<#rt>
		<#else><#t>
			${"\t"}<@compress single_line=true><#t>
			@StringFormat(type = ${input.formatType.formatType}<#rt>
			<#if input.minLength??>
				<#if input.minLength != 0>
				, minLength = ${input.minLength?c}<#t>
				</#if>
			</#if>
			<#if input.maxLength??>
				<#if input.maxLength != 0>
				, maxLength = ${input.maxLength?c}<#t>
				</#if>
			</#if>
			<#if input.length??>
				<#if input.length != 0>
				, length = ${input.length?c}<#t>
				</#if>
			</#if>
			<#t>
			)<#t>
				</@compress>
			</#if>
			
		</#if>
	@DisplayName(name = "${input.nameLocal}")
	private String ${input.name?lower_case};
<#else>
<#if input.multiple><#t>

	/** ${input.nameLocal}(${input.name}) */
	@Valid
	private List<${apiDefinition.name?cap_first}InputDTO${input.name?cap_first}> ${input.name?lower_case};
<#else><#t>

	/** ${input.nameLocal}(${input.name}) */
	@Valid
	private ${apiDefinition.name?cap_first}InputDTO${input.name?cap_first} ${input.name?lower_case};
</#if>
</#if>
</#list>
	
<#if apiDefinition.apiType == "MultiRead"><#t>
	/** OR/AND検索条件(inclusiveOr) */
	@QueryParam(value = "_inclusiveor")
	private boolean inclusiveOr ;
	
	/** 最大取得件数(maxResults) */
	@QueryParam(value = "_maxResults")
	private Integer maxResults;
</#if>
<#-- アクセサ-->
<#list itemDefinitions  as input>
<#if input.modelType?? && input.modelType == "DataItem"><#t>

	/**
	 * ${input.nameLocal}を取得する。
	 * @return ${input.name}
	 */
	public String get${input.name?cap_first}() {
		return ${input.name?lower_case};
	}

	/**
	 * ${input.nameLocal}を設定する。
	 * @param ${input.name}
	 */
	public void set${input.name?cap_first}(String ${input.name?lower_case}) {
		this.${input.name?lower_case} = ${input.name?lower_case};
	}
<#else><#t>

<#if input.multiple><#t>
	/**
	 * ${input.name}を取得する。
	 * @return ${input.name}
	 */
	public List<${apiDefinition.name?cap_first}InputDTO${input.name?cap_first}> get${input.name?cap_first}() {
		return ${input.name?lower_case};
	}

	/**
	 * ${input.name}を設定する。
	 * @param ${input.name}
	 */
	public void set${input.name?cap_first}(List<${apiDefinition.name?cap_first}InputDTO${input.name?cap_first}> ${input.name?lower_case}) {
		this.${input.name?lower_case} = ${input.name?lower_case};
	}
<#else><#t>

	/**
	 * ${input.nameLocal}を取得する。
	 * @return ${input.name}
	 */
	public ${apiDefinition.name?cap_first}InputDTO${input.name?cap_first} get${input.name?cap_first}() {
		return ${input.name?lower_case};
	}

	/**
	 * ${input.nameLocal}を設定する。
	 * @param ${input.name}
	 */
	public void set${input.name?cap_first}(${apiDefinition.name?cap_first}InputDTO${input.name?cap_first} ${input.name?lower_case}) {
		this.${input.name?lower_case} = ${input.name?lower_case};
	}
</#if>
</#if>
</#list>
	
<#if apiDefinition.apiType == "MultiRead"><#t>
	/**
	 * OR/AND検索条件を設定する。
	 * @param inclusiveOr OR/AND検索条件
	 */
	public void setInclusiveOr(boolean inclusiveOr) {
		this.inclusiveOr = inclusiveOr;
	}
	
	/**
	 * OR/AND検索条件を取得する。
	 * @return OR/AND検索条件
	 */
	public boolean isInclusiveOr() {
		return this.inclusiveOr;
	}

	/**
	 * 最大取得件数を設定する。
	 * @param maxResults 最大取得件数
	 */
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
	
	/**
	 * 最大取得件数を取得する。
	 * @return 最大取得件数
	 */
	public Integer getMaxResults() {
		return this.maxResults;
	}
</#if>
}
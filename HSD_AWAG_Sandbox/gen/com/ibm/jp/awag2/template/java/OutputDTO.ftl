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

package ${apiDefinition.javaPackage}.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.ibm.jp.awag2.common.dto.DTOBase;
import com.ibm.jp.awag2.common.util.TextUtility;

 /**
 * ${apiDefinition.name}<#if itemName??>.${itemName?cap_first}</#if>のOutput DTOクラス。
 *
 */
<#-- クラスアノテーション -->

<#-- クラス -->
@XmlType(propOrder = {<#list  itemDefinitions  as output>"${output.name}"<#sep>, </#list>})
public class ${apiDefinition.name?cap_first}OutputDTO<#if itemName??>${itemName?cap_first}</#if>  extends DTOBase {
<#-- フィールド -->
<#list itemDefinitions  as output>
<#if output.modelType?? && output.modelType == "DataItem"><#t>

	/** ${output.nameLocal}(${output.name}) */
	private String ${output.name?lower_case};
<#else>
<#if output.multiple>

	/** ${output.nameLocal}(${output.name}) */
	private List<${apiDefinition.name?cap_first}OutputDTO${output.name?cap_first}> ${output.name?lower_case};
<#else>

	/** ${output.nameLocal}(${output.name}) */
	private ${apiDefinition.name?cap_first}OutputDTO${output.name?cap_first} ${output.name?lower_case};

</#if>
</#if>
</#list>
<#-- アクセサ-->
<#list itemDefinitions  as output>
<#if output.modelType?? && output.modelType == "DataItem"><#t>

	/**
	 * ${output.nameLocal}を取得する。
	 * @return ${output.name}
	 */
	public String get${output.name?cap_first}() {
<#if output.mappedEntityFieldDefinition?? && output.mappedEntityFieldDefinition.dataType == "TIMESTAMP"><#t>
		return TextUtility.formatTimestamp(${output.name?lower_case});
<#else>
		return ${output.name?lower_case};
</#if>	 
	}

	/**
	 * ${output.nameLocal}を設定する。
	 * @param ${output.name}
	 */
	public void set${output.name?cap_first}(String ${output.name?lower_case}) {
		this.${output.name?lower_case} = ${output.name?lower_case};
	}
<#else>
<#if output.multiple><#t>

	/**
	 * ${output.nameLocal}を取得する。
	 * @return ${output.name}
	 */
	public List<${apiDefinition.name?cap_first}OutputDTO${output.name?cap_first}> get${output.name?cap_first}() {
		return ${output.name?lower_case};
	}

	/**
	 * ${output.nameLocal}を設定する。
	 * @param ${output.name}
	 */
	public void set${output.name?cap_first}(List<${apiDefinition.name?cap_first}OutputDTO${output.name?cap_first}> ${output.name?lower_case}) {
		this.${output.name?lower_case} = ${output.name?lower_case};
	}
<#else>

	/**
	 * ${output.nameLocal}を取得する。
	 * @return ${output.name}
	 */
	public ${apiDefinition.name?cap_first}OutputDTO${output.name?cap_first} get${output.name?cap_first}() {
		return ${output.name?lower_case};
	}

	/**
	 * ${output.nameLocal}を設定する。
	 * @param ${output.name}
	 */
	public void set${output.name?cap_first}(${apiDefinition.name?cap_first}OutputDTO${output.name?cap_first} ${output.name?lower_case}) {
		this.${output.name?lower_case} = ${output.name?lower_case};
	}
</#if>
</#if>
</#list>
}
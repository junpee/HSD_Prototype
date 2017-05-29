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
/** Generated by AWAG ver.${AWAGversion} at ${.now?iso_local} */

package ${resource.packageName}.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import com.ibm.jp.awag.rest.common.model.DisplayName;
import com.ibm.jp.awag.rest.common.model.PKBase;
import com.ibm.jp.awag.rest.common.validator.DateTime;
import com.ibm.jp.awag.rest.common.validator.Number;
import com.ibm.jp.awag.rest.common.validator.StringFormat;
import com.ibm.jp.awag.rest.common.validator.group.DeleteGroup;
import com.ibm.jp.awag.rest.common.validator.group.QueryGroup;
import static com.ibm.jp.awag.rest.common.util.TextUtility.*;

<#-- クラス -->
/**
 * ${resource.resourceName}のPKクラス。
 *
 */
@Embeddable
public class ${resource.resourceName}PK extends PKBase implements Serializable{

<#-- フィールド -->
<#list resource.pkFields as pkField>
	/** ${pkField.displayName}(${pkField.fieldName}) */
	@DisplayName(name = "${pkField.displayName}")
	<#if !pkField.allowedNull>
	@NotNull(groups = {Default.class, DeleteGroup.class})<#rt>
	
	</#if>
	<#if pkField.formatValidationRule??>
		<#if pkField.searchParam && pkField.formatValidationRule.length != 0 && pkField.conditionOperator != "EXACT">
			<#if pkField.formatValidationRule.formatType == "DECIMAL">
	@Number(type = Number.Type.DECIMAL, integer=${pkField.formatValidationRule.maxLength}, fraction = ${pkField.formatValidationRule.minLength}, groups = {Default.class, QueryGroup.class, DeleteGroup.class})<#rt>
			<#elseif pkField.formatValidationRule.formatType == "INTEGER" || pkField.formatValidationRule.formatType == "SMALLINT" || pkField.formatValidationRule.formatType = "BIGINT" || pkField.formatValidationRule.formatType = "FLOAT">
			${"\t"}<@compress single_line=true><#t>
	@Number.List({
	
		@Number(type = Number.Type.INTEGER<#rt>
				<#if pkField.formatValidationRule.minLength??>
					<#if pkField.formatValidationRule.minLength != 0>
				, minLength = ${pkField.formatValidationRule.minLength}<#t>
					</#if>
				</#if>
				<#if pkField.formatValidationRule.maxLength??>
					<#if pkField.formatValidationRule.maxLength != 0>
				, maxLength = ${pkField.formatValidationRule.maxLength}<#t>
					</#if>
				</#if>
				<#if pkField.formatValidationRule.length??>
					<#if pkField.formatValidationRule.length != 0>
				, length = ${pkField.formatValidationRule.length}<#t>
					</#if>
				</#if>
			, groups = {Default.class, DeleteGroup.class}),<#t>
		@Number(type = Number.Type.INTEGER<#rt>
				<#if pkField.formatValidationRule.minLength??>
					<#if pkField.formatValidationRule.minLength != 0>
				, minLength = ${pkField.formatValidationRule.minLength}<#t>
					</#if>
				</#if>
				<#if pkField.formatValidationRule.maxLength??>
					<#if pkField.formatValidationRule.maxLength != 0>
				, maxLength = ${pkField.formatValidationRule.maxLength}<#t>
					</#if>
				</#if>
				<#if pkField.formatValidationRule.length??>
					<#if pkField.formatValidationRule.length != 0>
				, maxLength = ${pkField.formatValidationRule.length}<#t>
					</#if>
				</#if>
			, groups = QueryGroup.class)<#t>
	})<#t>
			</@compress>
			<#elseif pkField.formatValidationRule.formatType == "DATE">
	@DateTime(type=DateTime.Type.DATE, groups = {Default.class, QueryGroup.class, DeleteGroup.class})<#rt>
			<#elseif pkField.formatValidationRule.formatType == "TIMESTAMP">	
	@DateTime(type=DateTime.Type.TIMESTAMP, groups = {Default.class, QueryGroup.class, DeleteGroup.class})<#rt>		
			<#else>

	@StringFormat.List({
	${"\t"}<@compress single_line=true>
		@StringFormat(type = ${pkField.formatValidationRule.formatType.formatType}<#rt>
				<#if pkField.formatValidationRule.minLength??>
					<#if pkField.formatValidationRule.minLength != 0>
				, minLength = ${pkField.formatValidationRule.minLength}<#t>
					</#if>
				</#if>
				<#if pkField.formatValidationRule.maxLength??>
					<#if pkField.formatValidationRule.maxLength != 0>
				, maxLength = ${pkField.formatValidationRule.maxLength}<#t>
					</#if>
				</#if>
				<#if pkField.formatValidationRule.length??>
					<#if pkField.formatValidationRule.length != 0>
				, length = ${pkField.formatValidationRule.length}<#t>
					</#if>
				</#if>
			, groups = {Default.class, DeleteGroup.class}),<#t>
	</@compress><#t>

	${"\t"}<@compress single_line=true>
		@StringFormat(type = ${pkField.formatValidationRule.formatType.formatType}<#rt>
				<#if pkField.formatValidationRule.minLength??>
					<#if pkField.formatValidationRule.minLength != 0>
				, minLength = ${pkField.formatValidationRule.minLength}<#t>
					</#if>
				</#if>
				<#if pkField.formatValidationRule.maxLength??>
					<#if pkField.formatValidationRule.maxLength != 0>
				, maxLength = ${pkField.formatValidationRule.maxLength}<#t>
					</#if>
				</#if>
				<#if pkField.formatValidationRule.length??>
					<#if pkField.formatValidationRule.length != 0>
				, maxLength = ${pkField.formatValidationRule.length}<#t>
					</#if>
				</#if>
			, groups = QueryGroup.class)<#t>
			})<#t>
	</@compress>
			</#if>	
		<#else>
			<#if pkField.formatValidationRule.formatType == "DECIMAL">
	@Number(type = Number.Type.DECIMAL, integer=${pkField.formatValidationRule.maxLength}, fraction = ${pkField.formatValidationRule.minLength}, groups = {Default.class, QueryGroup.class, DeleteGroup.class})<#rt>
			<#elseif pkField.formatValidationRule.formatType == "INTEGER" || pkField.formatValidationRule.formatType == "SMALLINT" || pkField.formatValidationRule.formatType = "BIGINT" || pkField.formatValidationRule.formatType = "FLOAT">
			${"\t"}<@compress single_line=true><#t>
			@Number(type = Number.Type.INTEGER<#rt>
				<#if pkField.formatValidationRule.minLength??>
					<#if pkField.formatValidationRule.minLength != 0>
				, minLength = ${pkField.formatValidationRule.minLength}<#t>
					</#if>
				</#if>
				<#if pkField.formatValidationRule.maxLength??>
					<#if pkField.formatValidationRule.maxLength != 0>
				, maxLength = ${pkField.formatValidationRule.maxLength}<#t>
					</#if>
				</#if>
				<#if pkField.formatValidationRule.length??>
					<#if pkField.formatValidationRule.length != 0>
				, length = ${pkField.formatValidationRule.length}<#t>
					</#if>
				</#if>
			, groups = {Default.class, QueryGroup.class, DeleteGroup.class})<#t>
			</@compress>
			<#elseif pkField.formatValidationRule.formatType == "DATE">
	@DateTime(type=DateTime.Type.DATE, groups = {Default.class, QueryGroup.class, DeleteGroup.class})<#rt>
			<#elseif pkField.formatValidationRule.formatType == "TIMESTAMP">	
	@DateTime(type=DateTime.Type.TIMESTAMP, groups = {Default.class, QueryGroup.class, DeleteGroup.class})<#rt>		
			<#else>
			${"\t"}<@compress single_line=true><#t>
			@StringFormat(type = ${pkField.formatValidationRule.formatType.formatType}<#rt>
				<#if pkField.formatValidationRule.minLength??>
					<#if pkField.formatValidationRule.minLength != 0>
				, minLength = ${pkField.formatValidationRule.minLength}<#t>
					</#if>
				</#if>
				<#if pkField.formatValidationRule.maxLength??>
					<#if pkField.formatValidationRule.maxLength != 0>
				, maxLength = ${pkField.formatValidationRule.maxLength}<#t>
					</#if>
				</#if>
				<#if pkField.formatValidationRule.length??>
					<#if pkField.formatValidationRule.length != 0>
				, length = ${pkField.formatValidationRule.length}<#t>
					</#if>
				</#if>
			, groups = {Default.class, QueryGroup.class, DeleteGroup.class})<#t>
			</@compress>
			</#if>
		</#if>
	</#if>

	private ${pkField.dataType.javaType} ${pkField.fieldName};

</#list>
<#-- コンストラクタ1 -->
	/**
	 * デフォルト・コンストラクタ。
	 */
	public ${resource.resourceName}PK() {}

<#-- コンストラクタ2 -->
	/**
	 * PKのフィールド値を指定してインスタンスを初期化する。
	<#list resource.pkFields as pkField>
	 * @param ${pkField.fieldName} ${pkField.displayName}
	</#list>
	 */
	${"\t"}<@compress single_line=true><#t>
		public ${resource.resourceName}PK (<#t>
	<#list resource.pkFields as pkField>
			${pkField.dataType.javaType} ${pkField.fieldName}<#sep>, <#t>
	</#list>
		) {<#t>
	</@compress>

		super();

	<#list resource.pkFields as pkField>
		this.set${pkField.fieldName?cap_first}(${pkField.fieldName});
	</#list>
	}

<#-- アクセサ -->
	<#list resource.pkFields as pkField>
	/**
	 * ${pkField.fieldName}を取得する。
	 * @return ${pkField.fieldName}
	 */
	@Column(name = "${pkField.columnName}")
	public ${pkField.dataType.javaType} get${pkField.fieldName?cap_first}() {
		return this.${pkField.fieldName};
	}

	/**
	 * ${pkField.fieldName}を設定する。
	 * @param ${pkField.fieldName}
	 */
	public void set${pkField.fieldName?cap_first}(${pkField.dataType.javaType} ${pkField.fieldName}) {
		this.${pkField.fieldName} = emptyStringToNull(${pkField.fieldName});
	}
	</#list>

<#-- メソッド実装 -->
	/**
	 * このPKオブジェクトが指定したオブジェクトと一致するか確認する。
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ${resource.resourceName}PK)) {
			return false;
		}
		${resource.resourceName}PK castOther = (${resource.resourceName}PK) other;
		${"\t\t"}<@compress single_line=true><#lt>
		return
		<#list resource.pkFields as pkField>
			this.${pkField.fieldName}.equals(castOther.${pkField.fieldName})<#sep> && </#sep><#t>
		</#list>
		;<#t></@compress>
	}

	/**
	 * PKオブジェクトのハッシュコードを取得する。
	 */
	@Override
	public int hashCode() {

		final int prime = 31;
		int hash = 17;
		<#list resource.pkFields as pkField>
		hash = hash * prime + ((this.${pkField.fieldName} != null) ? this.${pkField.fieldName} : "").hashCode();
		</#list>

		return hash;
	}

	/**
	 * PKオブジェクトの文字列表現を取得する。
	 */
	@Override
	public String toString() {
${"\t\t"}<@compress single_line=true><#t>
		String ret = <#t>
		<#list resource.pkFields as pkField><#t>
		this.${pkField.fieldName}<#sep> + "," + </#sep><#t>
		</#list><#t>
		</@compress><#t>;
		
		return ret;
	}

	/**
	 * PKオブジェクトの表示表文字列表現を取得する。
	 */
	@Override
	public String toDisplayString() {
${"\t\t"}<@compress single_line=true><#t>
		String ret = <#t>
		<#list resource.pkFields as pkField><#t>
		"${pkField.displayName}:" + this.${pkField.fieldName}<#sep> + "," + </#sep><#t>
		</#list><#t>
		</@compress><#t>;
		
		return ret;
	}
}
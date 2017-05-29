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
<#assign hasDependent = tableDefinition.getRelationDefinitions()?? && tableDefinition.getRelationDefinitions()?size gt 0>
<#include "includeHeader.ftl">

package ${tableCategory.javaPackage}.entity;

import static com.ibm.jp.awag2.common.util.TextUtility.emptyStringToNull;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.ibm.jp.awag2.common.entity.EntityBase;
import com.ibm.jp.awag2.common.entity.ResourceVersionNotlockImpl;
import com.ibm.jp.awag2.common.entity.ResourceVersionLongImpl;
import com.ibm.jp.awag2.common.entity.ResourceVersionTimestampImpl;
<#if hasDependent>
<#list tableDefinition.relationDefinitions as relationDefinition>
import ${tableCategory.javaPackage}.entity.${relationDefinition.name?cap_first};
</#list>
</#if>

/**
 * ${tableDefinition.nameLocal}のEntityクラス。
 *
 */
<#-- クラスアノテーション -->
@Entity
@Access(AccessType.PROPERTY)
<#if tableDefinition.schemaName??><#t>
@Table(name = "${tableDefinition.tableName?lower_case}"<#if tableDefinition.schemaName?? && tableDefinition.schemaName != "">, schema="${tableDefinition.schemaName?lower_case}"</#if>)
<#else>
@Table(name = "${tableDefinition.tableName?lower_case}")
</#if>
<#-- クラス -->
public class ${tableDefinition.name?cap_first} extends EntityBase <${tableDefinition.name?cap_first}PK, ${tableDefinition.versionType.javaType}> {

<#-- フィールド -->
	/** 主キー */
	private ${tableDefinition.name?cap_first}PK pk = new ${tableDefinition.name?cap_first}PK();
	
<#list tableDefinition.columnDefinitions as column>
<#if !column.pk>
	/** ${column.nameLocal}(${column.name}) */
	<#if column.version>
	private String _${column.name?lower_case};
	<#else>
	private ${column.dataType.javaType} ${column.name?lower_case};
	</#if>
	
</#if>
</#list>
<#if hasDependent>
<#list tableDefinition.relationDefinitions as relationDefinition><#t>
	/** ${relationDefinition.name}(${relationDefinition.name}) */
	<#if relationDefinition.relationPattern == "OneToMany">
	private List<${relationDefinition.name?cap_first}> ${relationDefinition.name?lower_case};
	<#elseif relationDefinition.relationPattern == "OneToOne">
	private ${relationDefinition.name?cap_first} ${relationDefinition.name?lower_case};
	</#if>
</#list>
</#if>

<#-- コンストラクタ1 -->
	/**
	 * デフォルト・コンストラクタ。
	 */
	public ${tableDefinition.name?cap_first}() {
		super();
<#if tableDefinition.versionType == "TIMESTAMP">
		super._resourceVersion = new ResourceVersionTimestampImpl();
<#elseif tableDefinition.versionType == "FLOAT">
		super._resourceVersion = new ResourceVersionLongImpl();
<#else>
		super._resourceVersion = new ResourceVersionNotlockImpl();
</#if>
	}

<#-- コンストラクタ2 -->
	/**
	 * Entityのフィールド値を指定してインスタンスを初期化する。
	<#list tableDefinition.columnDefinitions as column>
	 * @param ${column.name?lower_case} ${column.nameLocal}
	</#list>
	 */
	${"\t"}<@compress single_line=true><#t>
		public ${tableDefinition.name?cap_first} (<#t>
		<#list tableDefinition.columnDefinitions as column>
			${column.dataType.javaType} ${column.name?lower_case}<#sep>, <#t>
		</#list>
		) {<#t>
	</@compress>

<#if tableDefinition.versionType == "TIMESTAMP">
		super._resourceVersion = new ResourceVersionTimestampImpl();
<#elseif tableDefinition.versionType == "FLOAT">
		super._resourceVersion = new ResourceVersionLongImpl();
<#else>
		super._resourceVersion = new ResourceVersionNotlockImpl();
</#if>
		
	<#list tableDefinition.columnDefinitions as column>
		<#if column.version>
		this.set${column.name?cap_first}(${column.name?lower_case});
		<#else>
		this.set${column.name?cap_first}(${column.name?lower_case});
		</#if>
	</#list>
	}

<#-- アクセサ -->
	/**
	 * 主キーを取得する。
	 * @return 主キーオブジェクト
	 */
	@EmbeddedId
	@Override
	public ${tableDefinition.name?cap_first}PK getPk() {
		return this.pk;
	}

	/**
	 * 主キーを設定する。
	 * @param 主キーオブジェクト
	 */
	@Override
	public void setPk(${tableDefinition.name?cap_first}PK pk) {
		this.pk = (${tableDefinition.name?cap_first}PK) pk;
	}
<#list tableDefinition.columnDefinitions as column>

	<#if column.version>
	/**
	 * ${column.name}のDB値を取得する。
	 * @return ${column.name}のDB値
	 */
	@Version
	<#else>
	/**
	 * ${column.name}を取得する。
	 * @return ${column.name}
	 */
	</#if>
	<#if !column.pk>
	@Column(name = "${column.name?lower_case}")
	<#else>
	@Transient	
	</#if>
	<#if column.version>
	public ${tableDefinition.versionType.javaType} get${column.name?cap_first}_db() {	
		return super.get_resourceVersion();
	<#else>
	public ${column.dataType.javaType} get${column.name?cap_first}() {
	<#if column.pk>
		return this.pk.get${column.name?cap_first}();
	<#else>
		return this.${column.name?lower_case};
	</#if>
	</#if>
	}
	<#if column.version>

	/**
	 * ${column.nameLocal}の文字列表記を取得する。
	 * @return ${column.name}の文字列表記
	 */
	@Transient	
	public String get${column.name?cap_first}() {
		return super.get_resourceVersionString();
	}
	</#if>

	<#if column.version>
	/**
	 * ${column.nameLocal}のDB値を設定する。
	 * @param ${column.name}のDB値
	 */
	public void set${column.name?cap_first}_db(${tableDefinition.versionType.javaType} ${column.name?lower_case}) {
		super.set_resourceVersion(${column.name?lower_case});
	}

	/**
	 * ${column.nameLocal}の文字列表記を設定する。
	 * @param ${column.name}の文字列表記
	 */
	public void set${column.name?cap_first}(String ${column.name?lower_case}) {
		super.set_resourceVersionString(emptyStringToNull(${column.name?lower_case}));
	}
	<#else>
	/**
	 * ${column.nameLocal}を設定する。
	 * @param ${column.name}
	 */
	public void set${column.name?cap_first}(${column.dataType.javaType} ${column.name?lower_case}) {
	<#if column.pk>
		this.pk.set${column.name?cap_first}(emptyStringToNull(${column.name?lower_case}));
	<#else>
		this.${column.name?lower_case} = emptyStringToNull(${column.name?lower_case});
	</#if>
	}
	</#if>
</#list>

<#if hasDependent>
<#list tableDefinition.relationDefinitions as relationDefinition><#t>
  <#if relationDefinition.relationPattern == "OneToMany">
	/**
	 * ${relationDefinition.name}を取得する。
	 * @return ${relationDefinition.name}
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch=FetchType.LAZY )
	<#if relationDefinition.getJoinKeyList()?size gt 1>
	@JoinColumns({
		<#list relationDefinition.joinKeyList as joinKey>
		@JoinColumn(name="${joinKey.keyDependence?lower_case}", referencedColumnName="${joinKey.keyParent?lower_case}")<#sep>,
		</#sep><#t>
		</#list>})<#lt>
	<#else>
	@JoinColumn(name="${relationDefinition.joinKeyList[0].keyDependence?lower_case}", referencedColumnName="${relationDefinition.joinKeyList[0].keyParent?lower_case}", insertable=false, updatable=false)
	</#if>
	@OrderBy("<#list relationDefinition.tableDefinition.pkFields as pkField>pk.${pkField.name} ASC<#sep>, </#sep></#list>")
	public List<${relationDefinition.name?cap_first}> get${relationDefinition.name?cap_first}() {
		return ${relationDefinition.name?lower_case};
	}

	/**
	 * ${relationDefinition.name}を設定する。
	 * @param ${relationDefinition.name}
	 */
	public void set${relationDefinition.name?cap_first}(List<${relationDefinition.name?cap_first}> ${relationDefinition.name?lower_case}) {
		this.${relationDefinition.name?lower_case} = ${relationDefinition.name?lower_case};
	}
  <#elseif  relationDefinition.relationPattern == "OneToOne">
	/**
	 * ${relationDefinition.name}を取得する。
	 * @return ${relationDefinition.name}
	 */
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch=FetchType.LAZY )
	<#if relationDefinition.getJoinKeyList()?size gt 1>
	@JoinColumns({
		<#list relationDefinition.joinKeyList as joinKey>
		@JoinColumn(name="${joinKey.keyParent?lower_case}", referencedColumnName="${joinKey.keyDependence?lower_case}", insertable=false, updatable=false)<#sep>,
		</#sep><#t>
		</#list>})<#lt>
	<#else>
	@JoinColumn(name="${relationDefinition.joinKeyList[0].keyParent?lower_case}", referencedColumnName="${relationDefinition.joinKeyList[0].keyDependence?lower_case}", insertable=false, updatable=false)
	</#if>
	public ${relationDefinition.name?cap_first} get${relationDefinition.name?cap_first}() {
		return ${relationDefinition.name?lower_case};
	}

	/**
	 * ${relationDefinition.name}を設定する。
	 * @param ${relationDefinition.name}
	 */
	public void set${relationDefinition.name?cap_first}(${relationDefinition.name?cap_first} ${relationDefinition.name?lower_case}) {
		this.${relationDefinition.name?lower_case} = ${relationDefinition.name?lower_case};
	}
  </#if>
</#list>
</#if>

}
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

package ${tableCategory.javaPackage}.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import com.ibm.jp.awag2.common.entity.DisplayName;
import com.ibm.jp.awag2.common.entity.PKBase;
import static com.ibm.jp.awag2.common.util.TextUtility.*;

<#-- クラス -->
/**
 * ${tableDefinition.nameLocal}のPKクラス。
 *
 */
@Embeddable
public class ${tableDefinition.name?cap_first}PK extends PKBase implements Serializable{

<#-- フィールド -->
<#list tableDefinition.pkFields as pkField>
	/** ${pkField.nameLocal}(${pkField.name}) */
	@DisplayName(name = "${pkField.nameLocal}")
	private ${pkField.dataType.javaType} ${pkField.name?lower_case};

</#list>
<#-- コンストラクタ1 -->
	/**
	 * デフォルト・コンストラクタ。
	 */
	public ${tableDefinition.name?cap_first}PK() {}

<#-- コンストラクタ2 -->
	/**
	 * PKのフィールド値を指定してインスタンスを初期化する。
	<#list tableDefinition.pkFields as pkField>
	 * @param ${pkField.name} ${pkField.nameLocal}
	</#list>
	 */
	${"\t"}<@compress single_line=true><#t>
		public ${tableDefinition.name?cap_first}PK (<#t>
	<#list tableDefinition.pkFields as pkField>
			${pkField.dataType.javaType} ${pkField.name?lower_case}<#sep>, <#t>
	</#list>
		) {<#t>
	</@compress>

		super();

	<#list tableDefinition.pkFields as pkField>
		this.set${pkField.name?cap_first}(${pkField.name?lower_case});
	</#list>
	}

<#-- アクセサ -->
	<#list tableDefinition.pkFields as pkField>
	/**
	 * ${pkField.nameLocal}を取得する。
	 * @return ${pkField.name}
	 */
	@Column(name = "${pkField.name?lower_case}")
	<#if pkField.options?? && pkField.options.gen_strat??><#t>
	@GeneratedValue(strategy = GenerationType.${pkField.options.gen_strat}<#if pkField.options.gen_strat == "SEQUENCE" && pkField.options.gen_name??>, generator = "${pkField.options.gen_name}"</#if>)
	<#if pkField.options.gen_strat == "SEQUENCE"><#t>
	@SequenceGenerator(name = "${pkField.options.gen_name}"<#if pkField.options.gen_seq_name??>, sequenceName = "${pkField.options.gen_seq_name}"</#if><#if pkField.options.gen_seq_initval??>, initialValue = ${pkField.options.gen_seq_initval}</#if><#if pkField.options.gen_seq_alocsize??>, allocationSize = ${pkField.options.gen_seq_alocsize}</#if>)
	</#if>
	</#if>
	public ${pkField.dataType.javaType} get${pkField.name?cap_first}() {
		return this.${pkField.name?lower_case};
	}

	/**
	 * ${pkField.nameLocal?lower_case}を設定する。
	 * @param ${pkField.name?lower_case}
	 */
	public void set${pkField.name?cap_first}(${pkField.dataType.javaType} ${pkField.name?lower_case}) {
		this.${pkField.name?lower_case} = emptyStringToNull(${pkField.name?lower_case});
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
		if (!(other instanceof ${tableDefinition.name?cap_first}PK)) {
			return false;
		}
		${tableDefinition.name?cap_first}PK castOther = (${tableDefinition.name?cap_first}PK) other;
		${"\t\t"}<@compress single_line=true><#lt>
		return
		<#list tableDefinition.pkFields as pkField>
			this.${pkField.name?lower_case}.equals(castOther.${pkField.name?lower_case})<#sep> && </#sep><#t>
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
		<#list tableDefinition.pkFields as pkField>
		hash = hash * prime + ((this.${pkField.name?lower_case} != null) ? this.${pkField.name?lower_case} : "").hashCode();
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
		<#list tableDefinition.pkFields as pkField><#t>
		this.${pkField.name?lower_case}<#sep> + "," + </#sep><#t>
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
		<#list tableDefinition.pkFields as pkField><#t>
		"${pkField.nameLocal}:" + this.${pkField.name?lower_case}<#sep> + "," + </#sep><#t>
		</#list><#t>
		</@compress><#t>;
		
		return ret;
	}
}
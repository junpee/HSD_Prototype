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

package ${apiDefinition.javaPackage}.logic.impl;

import java.util.Map;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.ibm.jp.awag2.common.logic.ServiceAppException;
import com.ibm.jp.awag2.common.logic.ServiceDBException;
import com.ibm.jp.awag2.common.logic.ServiceIF;
import com.ibm.jp.awag2.common.util.MethodTraceLoggingInterceptor;
import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}InputDTO;
import ${apiDefinition.javaPackage}.dto.${apiDefinition.name?cap_first}OutputDTO;

<#compress>
/**
 * <#if apiDefinition.name??>${apiDefinition.name} サービスのカスタマイズクラス<#else>${apiDefinition.name} サービスのカスタマイズクラス</#if>
 *
 */
 </#compress>
 
@Interceptors(MethodTraceLoggingInterceptor.class)
@Stateless
public class ${apiDefinition.name?cap_first}ServiceCustomize extends ${apiDefinition.name?cap_first}Service implements ServiceIF<${apiDefinition.name?cap_first}InputDTO, ${apiDefinition.name?cap_first}OutputDTO> {

	/* (非 Javadoc)
	 * @see com.ibm.jp.awag2.common.logic.ServiceIF#execute(com.ibm.jp.awag2.common.dto.DTOBase)
	 */
	public ${apiDefinition.name?cap_first}OutputDTO execute(${apiDefinition.name?cap_first}InputDTO input) throws ServiceDBException, ServiceAppException {
		
		// CUSTOMIZE: Serviceメソッドの前後処理や置き換えを実装します。
		
		// DAOパラメータの取得
		Map<String, Object> daoParameterMap = this.processInput(input);
		
		// DAOの実行
		Map<String, Object> resultMap = this.persist(daoParameterMap);
		
		// DAO実行結果の出力DTOマッピング
		return this.processOutput(resultMap);
		
	}
	
	/*
	 * CUSTOMIZE: Serviceメソッドをカスタマイズする場合、対象のメソッドのコメントを解除して実装します。
	 *
	@Override
	protected ${apiDefinition.name?cap_first}OutputDTO execute(${apiDefinition.name?cap_first}InputDTO input) throws ServiceDBException, ServiceAppException {
		return super.execute(input);
	}

	@Override
	protected Map<String, Object> processInput(${apiDefinition.name?cap_first}InputDTO input) throws ServiceDBException, ServiceAppException {
		return super.processInput(input);
	}

	@Override
	protected Map<String, Object> persist(Map<String, Object> daoParameterMap) throws ServiceDBException, ServiceAppException {
		return super.persist(daoParameterMap);
	}

	@Override
	protected ${apiDefinition.name?cap_first}OutputDTO processOutput(Map<String, Object> resultMap) throws ServiceDBException, ServiceAppException {
		return super.processOutput(resultMap);
	}
	*/
}

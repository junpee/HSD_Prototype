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
<#------------------------------------>
<#-- Entity to DTO 詰替処理 出力マクロ -->
<#-- _inout:入出力区分, _target:詰替対象のオブジェクト, _parent:再帰呼出時の親オブジェクト, _recurseCnt:再帰呼出回数 -->
<#------------------------------------>
<#macro printMappingDTOandEntity _inout _target _parent _recurseCnt>
<#-- //ForDebug  _inout=${_inout}, _target=${_target}, _parent=${_parent}, _recurseCnt=${_recurseCnt} -->
<#----------------------------------->
<#-- 変換マップの取得 -->
<#----------------------------------->
<#compress>
<#-- InputDTOからEntityへ変換する場合 -->
<#if _parent == "none" && _inout == "in"><#local _outputTypeMap = _target.getInputDtoMapping()>
<#-- EntityからOutputDTOへ変換する場合 -->
<#elseif _parent == "none" && _inout == "out"><#local _outputTypeMap = _target.getOutputDtoMapping()>
<#-- 再帰呼出の場合 -->
<#else><#local _outputTypeMap = _target.getEmbeddedMapping()></#if>
</#compress>
<#----------------------------------->
<#-- 変換後オブジェクトの生成コード-->
<#----------------------------------->
<#-- 変化後オブジェクト単位にコードを出力 -->
<#if _inout == "out" && (_outputTypeMap?size == 0)><@blank _cnt=_recurseCnt + 1 />${apiDefinition.name?cap_first + "OutputDTO"} ${apiDefinition.name?lower_case + "outputdto"}Tmp = new ${apiDefinition.name?cap_first + "OutputDTO"}();</#if>
<#list _outputTypeMap?keys as _outputType><#t>
<#-- //ForDebug  // _outputType = ${_outputType} -->
<#local _isWrappedOutputObj = (_outputType != "_none_")><#t><#-- 変換後オブジェクトが他のオブジェクトにラップされている場合true -->
<#if _isWrappedOutputObj><#t>
<#if _parent == "none"><#t>
<@blank _cnt=_recurseCnt + 1 />${_outputType?cap_first} ${_outputType?lower_case}Tmp = new ${_outputType?cap_first}();
<#else><#t>
<#local _isWrappedInputObj = (_target.inputType != _target.inputField)><#t><#-- 変換前オブジェクトが他のオブジェクトにラップされている場合true -->
<#if !_target.multiple><#t>
<@blank _cnt=_recurseCnt />${_outputType?cap_first} ${_outputType?lower_case}TmpWrap = null;
<@blank _cnt=_recurseCnt />if (<#if _isWrappedInputObj><#if _target.inputType != "_none_">${_target.inputType?lower_case} != null && ${_target.inputType?lower_case}.get${_target.inputField?cap_first}()<#else>${_target.inputField?lower_case}List</#if><#else>${_target.inputType?uncap_first}Tmp</#if> != null) ${_outputType?lower_case}TmpWrap = new ${_outputType?cap_first}();
<#if _inout == "in"><#t>
<@blank _cnt=_recurseCnt />${apiDefinition.name?cap_first + "InputDTO"}${_target.inputField?cap_first} ${apiDefinition.name?lower_case + "inputdto"}${_target.inputField?lower_case} = null;
<@blank _cnt=_recurseCnt />if (<#if _isWrappedInputObj><#if _target.inputType != "_none_">${_target.inputType?lower_case} != null && ${_target.inputType?lower_case}.get${_target.inputField?cap_first}()<#else>${_target.inputField?lower_case}List</#if><#else>${_target.inputType?uncap_first}Tmp</#if> != null) ${apiDefinition.name?lower_case + "inputdto"}${_target.inputField?lower_case} = <#if _isWrappedInputObj >${_target.inputType?lower_case}.get${_target.inputField?cap_first}()<#else>${_target.inputType?uncap_first}Tmp</#if>;
<#else>
<@blank _cnt=_recurseCnt />${_target.inputField?cap_first} ${_target.inputField?lower_case} = null;
<@blank _cnt=_recurseCnt />if (<#if _isWrappedInputObj><#if _target.inputType != "_none_">${_target.inputType?lower_case} != null && ${_target.inputType?lower_case}.get${_target.inputField?cap_first}()<#else>${_target.inputField?lower_case}List</#if><#else>${_target.inputType?uncap_first}Tmp</#if> != null) ${_target.inputField?lower_case} = <#if _isWrappedInputObj >${_target.inputType?lower_case}.get${_target.inputField?cap_first}()<#else>${_target.inputType?uncap_first}Tmp</#if>;
</#if>
<#else><#t>
<@blank _cnt=_recurseCnt />List<${_outputType?cap_first}> ${_outputType?lower_case}TmpWrap = null;
<@blank _cnt=_recurseCnt />if (<#if _isWrappedInputObj><#if _target.inputType != "_none_">${_target.inputType?lower_case} != null && ${_target.inputType?lower_case}.get${_target.inputField?cap_first}()  != null && !${_target.inputType?lower_case}.get${_target.inputField?cap_first}().isEmpty()<#else>${_target.inputField?lower_case}List != null && !${_target.inputField?lower_case}List.isEmpty()</#if><#else>${_target.inputType?uncap_first}Tmp != null && !${_target.inputType?uncap_first}Tmp.isEmpty()</#if>) ${_outputType?lower_case}TmpWrap = new ArrayList<>();
	<#if _inout == "in"><#t>
<@blank _cnt=_recurseCnt />if (${_target.inputType?lower_case}<#if _parent != "none">.get${_target.inputField?cap_first}()</#if> != null && !${_target.inputType?lower_case}<#if _parent != "none">.get${_target.inputField?cap_first}()</#if>.isEmpty()) {
<@blank _cnt=_recurseCnt />for (${apiDefinition.name?cap_first + "InputDTO"}${_target.inputField?cap_first} ${apiDefinition.name?lower_case + "inputdto"}${_target.inputField?lower_case} : <#if _target.inputType != "_none_">${_target.inputType?lower_case}<#if _parent != "none">.get${_target.inputField?cap_first}()</#if><#else>${_target.inputField?lower_case}</#if>) {
	<#else><#t>
<@blank _cnt=_recurseCnt />if (<#if _isWrappedInputObj><#if _target.inputType != "_none_">${_target.inputType?lower_case} != null && ${_target.inputType?lower_case}.get${_target.inputField?cap_first}()  != null && !${_target.inputType?lower_case}.get${_target.inputField?cap_first}().isEmpty()<#else>${_target.inputField?lower_case}List != null && !${_target.inputField?lower_case}List.isEmpty()</#if><#else>${_target.inputType?uncap_first}Tmp != null && !${_target.inputType?uncap_first}Tmp.isEmpty()</#if>) {
<@blank _cnt=_recurseCnt />for (${_target.inputField?cap_first} ${_target.inputField?lower_case} : <#if _target.inputType != "_none_"><#if _isWrappedInputObj >${_target.inputType?lower_case}.get${_target.inputField?cap_first}()<#else>${_target.inputField?lower_case}List</#if><#else>${_target.inputField?lower_case}List</#if>) {
	</#if>
<@blank _cnt=_recurseCnt+1 />${_outputType?cap_first} ${_outputType?lower_case}Tmp = new ${_outputType?cap_first}();	
</#if>
</#if>
</#if>
<#----------------------------------->
<#-- オブジェクトの詰め替えコード-->
<#----------------------------------->
<#list _outputTypeMap[_outputType] as _item><#t>
	<#local _outputFieldType = _item.outputField><#t><#-- //ForDebug  // _outputFieldType = ${_outputFieldType}--><#-- 詰替の出力側のフィールドの型 -->
	<#local _inputType = _item.inputType><#t><#-- //ForDebug  // _inputType = ${_inputType}--><#-- 詰替の入力側の型 -->
	<#local _inputFieldType = _item.inputField><#t><#-- //ForDebug  // _inputFieldType = ${_inputFieldType}--<#-- //ForDebug  <#-- 詰替の入力側のフィールドの型 -->
<#if _item.fieldDefinition??><#local _isVersion = _item.fieldDefinition.isVersion()><#else><#local _isVersion = false></#if><#t>
	<#local _hasEmbeddedMapping = _item.getEmbeddedMapping()?? && (_item.getEmbeddedMapping()?size > 0)><#t>
<#t>
	<#if _hasEmbeddedMapping><#t>
	<@printMappingDTOandEntity _inout=_inout _target=_item _parent=_target _recurseCnt=_recurseCnt+1 />
	</#if>
	<#if !_hasEmbeddedMapping><#t>
<@blank _cnt=_recurseCnt+1 /><#if _inputFieldType?contains(".")>if (${_inputType?lower_case} != null && ${_inputType?lower_case}.get${_inputFieldType?split(".")?first?cap_first}() != null) <#else>if (${_inputType?lower_case} != null) </#if>${_outputType?lower_case}Tmp<#if _parent != "none" && !_target.multiple>Wrap</#if>.set${_outputFieldType?cap_first}(${_inputType?lower_case}<#list _inputFieldType?split(".") as x>.get${x?cap_first}()</#list>);
	<#elseif _isWrappedOutputObj><#t>
<@blank _cnt=_recurseCnt+1 />${_outputType?lower_case}Tmp.set${_outputFieldType?cap_first}(<#if _inout == "out">${apiDefinition.name?lower_case + "outputdto"}</#if>${_outputFieldType?lower_case}TmpWrap);
	</#if>
</#list>
<#t>
<#if _parent != "none" && _target.multiple><#t>
<@blank _cnt=_recurseCnt+1 />${_outputType?lower_case}TmpWrap.add(${_outputType?lower_case}Tmp);
<@blank _cnt=_recurseCnt />}
<@blank _cnt=_recurseCnt />}
</#if>
<#t>
<#----------------------------------->
<#-- DAOパラメータの設定コード-->
<#----------------------------------->
<#if _parent == "none" && _inout == "in"><#t>
<#if _isWrappedOutputObj>
<@blank _cnt=_recurseCnt+1 />daoParameterMap.put(DAO_PARAM_MAP_KEY_${_outputType?upper_case},  ${_outputType?lower_case}Tmp);
<#else>
<#list _outputTypeMap[_outputType] as _item><#t>
<@blank _cnt=_recurseCnt+1 />daoParameterMap.put(DAO_PARAM_MAP_KEY_${_item.outputField?upper_case},  ${_item.outputField?lower_case}TmpWrap);
</#list>
</#if>
</#if>
</#list>
</#macro>

<#------------------------------------>
<#------- インデント 出力マクロ ------->
<#------------------------------------>
<#macro blank _cnt>
<#list 0..<_cnt as x>	</#list><#rt>
</#macro>

<#------------------------------------>
<#------- プロパティ名 出力マクロ ------->
<#------------------------------------>
<#macro propertyName orgName>
<@compress single_line=true>
<#if orgName?substring(0,2) == orgName?substring(0,2)?upper_case>
${orgName}<#t>
<#else>
${orgName?uncap_first}<#t>
</#if>
</@compress>
</#macro>
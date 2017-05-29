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
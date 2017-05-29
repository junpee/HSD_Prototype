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
<#if project.designType == "SIMPLE">
	<#if screen.screenType == "STANDARD">
		<#include "screen.STANDARD.SIMPLE.ftl">
	<#elseif screen.screenType == "SEARCH_TABLE" || screen.screenType == "HEADER_DETAIL">
		<#include "screen.SEARCH_TABLE.SIMPLE.ftl">
	<#elseif screen.screenType == "GRID_TABLE" || screen.screenType == "GRID_SELECT_TABLE">
		<#include "screen.GRID_TABLE.SIMPLE.ftl">
	<#else>
		<#include "screen.STANDARD.SIMPLE.ftl">
	</#if>
<#else>
	<#if screen.screenType == "STANDARD">
		<#include "screen.STANDARD.DEFAULT.ftl">
	<#elseif screen.screenType == "SEARCH_TABLE" || screen.screenType == "HEADER_DETAIL">
		<#include "screen.SEARCH_TABLE.DEFAULT.ftl">
	<#elseif screen.screenType == "GRID_TABLE" || screen.screenType == "GRID_SELECT_TABLE">
		<#include "screen.GRID_TABLE.DEFAULT.ftl">
	<#else>
		<#include "screen.STANDARD.DEFAULT.ftl">
	</#if>
</#if>

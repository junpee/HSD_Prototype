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
<#list usecase.screenFlow as screen>
	<#if screen.menuTop>
		<#assign topScreen = screen.screenName>
		<#break>
	</#if>
</#list>
<#if !topScreen??>
	<#assign topScreen = usecase.screenFlow[0].screenName>
</#if>
"use strict";
var app = angular.module('awag.${usecase.usecaseName?lower_case}', ['ngRoute', 'awag.common'])
    /**
     * routing for sample CRUD
     **/
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/${usecase.usecaseName?lower_case}', {
                controller: '${usecase.usecaseName?lower_case}${topScreen?cap_first}Ctrl as vm',
                templateUrl: 'awag/${usecase.usecaseName?lower_case}/template/${topScreen?lower_case}.html'
<#list usecase.screenFlow as screen>
            })
            .when('/${usecase.usecaseName?lower_case}/${screen.screenName}', {
                controller: '${usecase.usecaseName?lower_case}${screen.screenName?cap_first}Ctrl as vm',
                templateUrl: 'awag/${usecase.usecaseName?lower_case}/template/${screen.screenName?lower_case}.html'
</#list>
            });
    }]);

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
"use strict";
var app = angular.module('awag.${usecase.usecaseName?lower_case}');

<#list screen.fieldGroupList as group>
	<#if group.list>
		<#assign firstListName=group.listName>
		<#break>
	</#if>
</#list>
<#list screen.fieldGroupList as group>
	<#list group.eventList as event>

/**
 * ${usecase.usecaseName} ${screen.screenDisplayName} ${event.eventDisplayName} Service
 **/
app.service('${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}.execute()");

		<#if !event.nextScreen?has_content && !event.apiType?has_content>
            //同画面イベント
            //custom logic
            vm.model = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}CustomLogic(vm.model, Context, vm);
            //loading
            hideLoading();
            $timeout(MKDT, 0, false);
		</#if>
		<#if event.nextScreen?has_content && !event.apiType?has_content>
            //遷移イベント
            Context.nextPath='/${usecase.usecaseName?lower_case}/${event.nextScreen}';
            //custom logic
			<#if (screen.screenType=="SEARCH_TABLE" || screen.screenType=="HEADER_DETAIL") && group.list>
            Context.vModels.${event.nextScreen} = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}CustomLogic(Context.nextModel, Context, vm);
			<#elseif event.eventName=="awagGridConfirm">
            var tmp = {${firstListName}: []};
            for (var i=0;i<vm.model.${firstListName}.length;i++) {
                if (vm.model.${firstListName}[i].selected) {
                    delete vm.model.${firstListName}[i].selected;
                    tmp.${firstListName}.push(vm.model.${firstListName}[i]);
                }
            }
            Context.vModels.${event.nextScreen} = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}CustomLogic(tmp, Context, vm);
			<#else>
            Context.vModels.${event.nextScreen} = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}CustomLogic(vm.model, Context, vm);
			</#if>
            //next page
            if(Context.nextPath != null){
                $location.path(Context.nextPath);
            }
            //loading
            hideLoading();
            $timeout(MKDT, 0, false);
		</#if>
		<#if event.nextScreen?has_content>
			<#assign next="Context.vModels.${event.nextScreen}">
		<#elseif event.eventName=="awagSelect">
			<#assign next="Context.nextModel">
		<#else>
			<#assign next="vm.model">
		</#if>
		<#if event.apiType?has_content>
			<#if event.apiType=="GET">
            //pre API call logic
				<#if event.eventName=="awagSelect">
            var param = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}PreCallLogic(vm.selectedKey, Context, vm);
				<#elseif event.eventName=="awagSearch">
            var param = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}PreCallLogic(vm.model.search, Context, vm);
				<#else>
            var param = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}PreCallLogic(vm.model, Context, vm);
				</#if>
            //clear empty params
            param = JsonUtils.clearEmptyParams(param);
            var success = function(httpResponse) {
                var value = httpResponse.data;
                if(value===""){
                    value = {};
                }
                //success
				<#if event.nextScreen?has_content>
                Context.nextPath='/${usecase.usecaseName?lower_case}/${event.nextScreen}';
				</#if>
				<#if event.eventName=="awagSearch">
                vm.pager.pagerList = [];
                if(!value.${firstListName}) {
                    value.${firstListName} = [];
                }
                $log.debug(value.${firstListName}.length + ' items');
                value.search = vm.model.search;
				<#else>
                $log.debug(value);
				</#if>

                //custom logic
                ${next} = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}SuccessLogic(value, Context, vm);

				<#if event.eventName=="awagSearch">
                //setup pager
                for (var i = 0; i < Math.floor((value.${firstListName}.length - 1) / vm.pager.len) + 1; i++) {
                    vm.pager.pagerList[i] = i;
                }
                vm.pager.start = 0;
                //no record
                if (httpResponse.status===204 || value.${firstListName}.length === 0) {
                    var msg;
                    if(Context.tmpMessage){
                        msg = Context.tmpMessage;
                        delete Context.tmpMessage;
                    } else {
                        msg = MSG_SEARCH_204;
                    }
                    vm.error = ErrorHandler.handleError(param, httpResponse.headers, msg);
                } else {
					<#if event.nextScreen?has_content>
                    //go next screen
                    if(Context.nextPath != null){
                        $location.path(Context.nextPath);
                    }
					</#if>
                }
				</#if>

				<#if event.eventName=="awagSelect">
                //no record
                if (httpResponse.status===204) {
                    var msg;
                    if(Context.tmpMessage){
                        msg = Context.tmpMessage;
                        delete Context.tmpMessage;
                    } else {
                        msg = MSG_GET_204;
                    }
                    vm.error = ErrorHandler.handleError(param, httpResponse.headers, msg);
                } else {
                    vm.modelSelected = true;
                }
				</#if>
				<#if event.nextScreen?has_content && event.eventName!="awagSearch">
                //go next screen
                if(Context.nextPath != null){
                    $location.path(Context.nextPath);
                }
				</#if>
                //loading
                hideLoading();
                $timeout(MKDT, 0, false);
            };
            var error = function(httpResponse) {
                vm.error = ErrorHandler.handleError(param, httpResponse);
                vm.error = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}ErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.get(contextRoot + ${event.getReplacedApiPath()} + JsonUtils.getQueryParamString(param)).then(success, error);
			</#if>
			<#if event.apiType=="POST">
            //pre API call logic
            var param = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}PreCallLogic(vm.model, Context, vm);
            //clear empty params
            param = JsonUtils.clearEmptyParams(param);
            var success = function(httpResponse) {
                var value = httpResponse.data;
                if(value===""){
                    value = {};
                }
                //success
				<#if event.nextScreen?has_content>
                Context.nextPath='/${usecase.usecaseName?lower_case}/${event.nextScreen}';
				</#if>
                //custom logic
                ${next} = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}SuccessLogic(value, Context, vm);
				<#if event.nextScreen?has_content>
                //go next screen
                if(Context.nextPath != null){
                    $location.path(Context.nextPath);
                }
				</#if>
                //loading
                hideLoading();
                $timeout(MKDT, 0, false);
            };
            var error = function(httpResponse) {
                vm.error = ErrorHandler.handleError(param, httpResponse);
                vm.error = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}ErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.post(contextRoot + ${event.getReplacedApiPath()}, param).then(success, error);
			</#if>
			<#if event.apiType=="PUT">
            //pre API call logic
            var param = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}PreCallLogic(vm.model, Context, vm);
            //clear empty params
            param = JsonUtils.clearEmptyParams(param);
            var success = function(httpResponse) {
                var value = httpResponse.data;
                if(value===""){
                    value = {};
                }
                //success
				<#if event.nextScreen?has_content>
                Context.nextPath='/${usecase.usecaseName?lower_case}/${event.nextScreen}';
				</#if>
                //custom logic
                ${next} = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}SuccessLogic(value, Context, vm);
				<#if event.nextScreen?has_content>
                //go next screen
                if(Context.nextPath != null){
                    $location.path(Context.nextPath);
                }
				</#if>
                //loading
                hideLoading();
                $timeout(MKDT, 0, false);
            };
            var error = function(httpResponse) {
                vm.error = ErrorHandler.handleError(param, httpResponse);
                vm.error = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}ErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.put(contextRoot + ${event.getReplacedApiPath()}, param).then(success, error);
			</#if>
			<#if event.apiType=="DELETE">
            //pre API call logic
            var param = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}PreCallLogic(vm.model, Context, vm);
            //clear empty params
            param = JsonUtils.clearEmptyParams(param);
            var success = function(httpResponse) {
                var value = httpResponse.data;
                if(value===""){
                    value = {};
                }
                //success
				<#if event.nextScreen?has_content>
                Context.nextPath='/${usecase.usecaseName?lower_case}/${event.nextScreen}';
				</#if>
                //custom logic
                ${next} = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}SuccessLogic(value, Context, vm);
				<#if event.nextScreen?has_content>
                //go next screen
                if(Context.nextPath != null){
                    $location.path(Context.nextPath);
                }
				</#if>
                //loading
                hideLoading();
                $timeout(MKDT, 0, false);
            };
            var error = function(httpResponse) {
                vm.error = ErrorHandler.handleError(param, httpResponse);
                vm.error = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}ErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.delete(contextRoot + ${event.getReplacedApiPath()}, {
                headers: {'Content-Type': 'application/json'},
                data: param
            }).then(success, error);
			</#if>
		</#if>
        };
    }
]);

	</#list>
</#list>

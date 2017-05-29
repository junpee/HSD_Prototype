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
<#assign tmpHash = {}>
<#list screen.fieldGroupList as group>
	<#list group.fieldList as field>
		<#if field.codeListId??>
			<#assign tmpHash = tmpHash + {field.codeListId : "tmp"}>
		</#if>
	</#list>
</#list>
"use strict";
var app = angular.module('awag.${usecase.usecaseName?lower_case}');

/**
 * controller for ${screen.screenDisplayName}
 **/
app.controller('${usecase.usecaseName?lower_case}${screen.screenName?cap_first}Ctrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    <#list screen.fieldGroupList as group><#list group.eventList as event>'${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}', </#list></#list>
    <#list tmpHash?keys as key>'${key}', </#list>
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    <#list screen.fieldGroupList as group><#list group.eventList as event>, ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}</#list></#list>
    <#list tmpHash?keys as key>, ${key}</#list>) {
        var vm = this;
        $window.document.title = "${screen.screenDisplayName}";
        vm.getNameFromValue = NameValue.getName;
<#list screen.fieldGroupList as group>
	<#list group.fieldList as field>
		<#if field.codeListId??>
        vm.${field.codeListId} = ${field.codeListId};
		</#if>
	</#list>
</#list>
        vm.model = Context.vModels.${screen.screenName};
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
<#if screen.screenType == "SEARCH_TABLE">
        vm.model.search = {
          _inclusiveor: "false"
        };

        /**
         * reset input
         **/
        vm.awagReset = function(){
          vm.model.search = {
            _inclusiveor: "false"
          };
          $timeout(MKDT, 0, false);
          $timeout(RSCKD, 0, false);
        };

</#if>
<#if screen.screenType == "SEARCH_TABLE" || screen.screenType == "HEADER_DETAIL">
	<#list screen.fieldGroupList as group>
		<#if group.list>
			<#assign listName=group.listName>
			<#break>
		</#if>
	</#list>
        vm.pager = {
          start: 0,
          len: 10,
          pagerList: [],
          pagenum: 0
        };
        if(vm.model.${listName}) {
            //setup pager
            for (var i = 0; i < Math.floor((vm.model.${listName}.length - 1) / vm.pager.len) + 1; i++) {
                vm.pager.pagerList[i] = i;
            }
        }

        /**
         * pagenation
         * @param {page} page number (0-)
         **/
        vm.paginate = function(pager, page){
          pager.start = pager.len * page;
          pager.pagenum = page;
          $timeout(MKDT, 0, false);
        };

        vm.sortKey${listName?cap_first} = null;
        /**
         * sort ${listName}
         * @param {key} sort key
         **/
        vm.sort${listName?cap_first} = function(key){
            if(vm.sortKey${listName?cap_first} == null){
                vm.sortKey${listName?cap_first} = key;
            } else if (!vm.sortKey${listName?cap_first}.endsWith(key)){
                vm.sortKey${listName?cap_first} = key;
            }
            if(vm.sortKey${listName?cap_first}.startsWith("+")){
                vm.sortKey${listName?cap_first} = "-" + vm.sortKey${listName?cap_first}.substr(1);
            } else if(vm.sortKey${listName?cap_first}.startsWith("-")) {
                vm.sortKey${listName?cap_first} = "+" + vm.sortKey${listName?cap_first}.substr(1);
            } else {
                vm.sortKey${listName?cap_first} = "-" + key;
            }
            $timeout(MKDT, 0, false);
            vm.model.${listName} =  $filter('orderBy')(vm.model.${listName}, vm.sortKey${listName?cap_first});
        };

</#if>
<#if screen.screenType == "GRID_SELECT_TABLE">
	<#list screen.fieldGroupList as group>
		<#if group.list>
			<#assign listName=group.listName>
			<#break>
		</#if>
	</#list>
        /**
         * returns true when checkbox is selected
         **/
        vm.awagIsCheckboxSelected = function() {
            if (vm.model.${listName}) {
                for (var i=0;i<vm.model.${listName}.length;i++) {
                    if (vm.model.${listName}[i].selected) {
                        return true;
                    }
                }
            }
            return false;
        };

</#if>
        vm.model = ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}Initialize(vm.model, Context, vm);

<#list screen.fieldGroupList as group>
	<#list group.eventList as event>
        /**
         * call ${event.eventName} service
         **/
        vm.${event.eventName} = function() {
            $log.debug("CTR-EVNT:start - ${event.eventName}");
            Context.vModels.${screen.screenName} = vm.model;
            //loading
            showLoading();
            ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

		<#if event.eventName=="awagInit">
        //call init event
        vm.${event.eventName}();

		</#if>
	</#list>
</#list>
        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

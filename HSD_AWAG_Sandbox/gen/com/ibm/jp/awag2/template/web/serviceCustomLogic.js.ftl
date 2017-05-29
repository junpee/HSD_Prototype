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

<#if !event.apiType?has_content>
/**
 * Custom Business Logic for event ${screen.screenName} ${event.eventName}
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}CustomLogic(model, Context, vm){
    console.log("SVC-CUST:start - ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}CustomLogic");
    console.log(model);
	<#if event.eventName=="awagGoback" && event.nextScreen??>
    var next = Context.vModels.${event.nextScreen};
	<#elseif event.eventName=="awagGoblank">
    var next = {};
	<#else>
    var next = model;
	</#if>
    //TODO customize logic here

    return next;
}

<#else>
/**
 * Custom Business Logic before ${screen.screenName} ${event.eventName} API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}PreCallLogic(model, Context, vm){
    console.log("SVC-CUST:start - ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}PreCallLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after success ${screen.screenName} ${event.eventName} API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}SuccessLogic(model, Context, vm){
    console.log("SVC-CUST:start - ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}SuccessLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after error ${screen.screenName} ${event.eventName} API call
 *
 * @param {Object} 加工前エラーメッセージ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @param {Object} httpResponse
 * @return {Object} 加工後エラーメッセージ
 **/
function ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}ErrorLogic(error, Context, vm, httpResponse){
    console.log("SVC-CUST:start - ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}${event.eventName?cap_first}ErrorLogic");
    //TODO customize logic here

    return error;
}

</#if>

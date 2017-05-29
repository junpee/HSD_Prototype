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

/**
 * Custom Business Logic for event ${screen.screenName} Initialize
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}Initialize(model, Context, vm){
  console.log("CTR-INIT:start - ${usecase.usecaseName?lower_case}${screen.screenName?cap_first}Initialize");
  //TODO customize logic here
<#if screen.options?? && screen.options.clear_in_initialize?? && screen.options.clear_in_initialize=="true">
  model = {};
</#if>

  return model;
}

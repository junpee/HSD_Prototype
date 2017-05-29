"use strict";

/**
 * Custom Business Logic before search awagSearch API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function maintenanceSearchAwagSearchPreCallLogic(model, Context, vm){
    console.log("SVC-CUST:start - maintenanceSearchAwagSearchPreCallLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after success search awagSearch API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function maintenanceSearchAwagSearchSuccessLogic(model, Context, vm){
    console.log("SVC-CUST:start - maintenanceSearchAwagSearchSuccessLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after error search awagSearch API call
 *
 * @param {Object} 加工前エラーメッセージ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @param {Object} httpResponse
 * @return {Object} 加工後エラーメッセージ
 **/
function maintenanceSearchAwagSearchErrorLogic(error, Context, vm, httpResponse){
    console.log("SVC-CUST:start - maintenanceSearchAwagSearchErrorLogic");
    //TODO customize logic here

    return error;
}


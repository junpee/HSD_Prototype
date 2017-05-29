"use strict";

/**
 * Custom Business Logic before search awagSelect API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function maintenanceSearchAwagSelectPreCallLogic(model, Context, vm){
    console.log("SVC-CUST:start - maintenanceSearchAwagSelectPreCallLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after success search awagSelect API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function maintenanceSearchAwagSelectSuccessLogic(model, Context, vm){
    console.log("SVC-CUST:start - maintenanceSearchAwagSelectSuccessLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after error search awagSelect API call
 *
 * @param {Object} 加工前エラーメッセージ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @param {Object} httpResponse
 * @return {Object} 加工後エラーメッセージ
 **/
function maintenanceSearchAwagSelectErrorLogic(error, Context, vm, httpResponse){
    console.log("SVC-CUST:start - maintenanceSearchAwagSelectErrorLogic");
    //TODO customize logic here

    return error;
}


"use strict";

/**
 * Custom Business Logic before confirm update API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function maintenanceConfirmUpdatePreCallLogic(model, Context, vm){
    console.log("SVC-CUST:start - maintenanceConfirmUpdatePreCallLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after success confirm update API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function maintenanceConfirmUpdateSuccessLogic(model, Context, vm){
    console.log("SVC-CUST:start - maintenanceConfirmUpdateSuccessLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after error confirm update API call
 *
 * @param {Object} 加工前エラーメッセージ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @param {Object} httpResponse
 * @return {Object} 加工後エラーメッセージ
 **/
function maintenanceConfirmUpdateErrorLogic(error, Context, vm, httpResponse){
    console.log("SVC-CUST:start - maintenanceConfirmUpdateErrorLogic");
    //TODO customize logic here

    return error;
}


"use strict";

/**
 * Custom Business Logic before search awagSelect API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactSearchAwagSelectPreCallLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactSearchAwagSelectPreCallLogic");
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
function contactSearchAwagSelectSuccessLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactSearchAwagSelectSuccessLogic");
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
function contactSearchAwagSelectErrorLogic(error, Context, vm, httpResponse){
    console.log("SVC-CUST:start - contactSearchAwagSelectErrorLogic");
    //TODO customize logic here

    return error;
}


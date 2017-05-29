"use strict";

/**
 * Custom Business Logic before search awagSearch API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactSearchAwagSearchPreCallLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactSearchAwagSearchPreCallLogic");
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
function contactSearchAwagSearchSuccessLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactSearchAwagSearchSuccessLogic");
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
function contactSearchAwagSearchErrorLogic(error, Context, vm, httpResponse){
    console.log("SVC-CUST:start - contactSearchAwagSearchErrorLogic");
    //TODO customize logic here

    return error;
}


"use strict";

/**
 * Custom Business Logic before gridconfirm confirm API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function approveGridconfirmConfirmPreCallLogic(model, Context, vm){
    console.log("SVC-CUST:start - approveGridconfirmConfirmPreCallLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after success gridconfirm confirm API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function approveGridconfirmConfirmSuccessLogic(model, Context, vm){
    console.log("SVC-CUST:start - approveGridconfirmConfirmSuccessLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after error gridconfirm confirm API call
 *
 * @param {Object} 加工前エラーメッセージ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @param {Object} httpResponse
 * @return {Object} 加工後エラーメッセージ
 **/
function approveGridconfirmConfirmErrorLogic(error, Context, vm, httpResponse){
    console.log("SVC-CUST:start - approveGridconfirmConfirmErrorLogic");
    //TODO customize logic here

    return error;
}


"use strict";

/**
 * Custom Business Logic before addconfirm add API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactAddconfirmAddPreCallLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactAddconfirmAddPreCallLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after success addconfirm add API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactAddconfirmAddSuccessLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactAddconfirmAddSuccessLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after error addconfirm add API call
 *
 * @param {Object} 加工前エラーメッセージ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @param {Object} httpResponse
 * @return {Object} 加工後エラーメッセージ
 **/
function contactAddconfirmAddErrorLogic(error, Context, vm, httpResponse){
    console.log("SVC-CUST:start - contactAddconfirmAddErrorLogic");
    //TODO customize logic here

    return error;
}


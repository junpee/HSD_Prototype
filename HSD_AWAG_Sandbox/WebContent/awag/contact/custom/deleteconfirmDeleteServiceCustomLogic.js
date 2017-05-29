"use strict";

/**
 * Custom Business Logic before deleteconfirm delete API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactDeleteconfirmDeletePreCallLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactDeleteconfirmDeletePreCallLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after success deleteconfirm delete API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactDeleteconfirmDeleteSuccessLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactDeleteconfirmDeleteSuccessLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after error deleteconfirm delete API call
 *
 * @param {Object} 加工前エラーメッセージ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @param {Object} httpResponse
 * @return {Object} 加工後エラーメッセージ
 **/
function contactDeleteconfirmDeleteErrorLogic(error, Context, vm, httpResponse){
    console.log("SVC-CUST:start - contactDeleteconfirmDeleteErrorLogic");
    //TODO customize logic here

    return error;
}


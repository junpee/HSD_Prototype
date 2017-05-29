"use strict";

/**
 * Custom Business Logic before gridinput awagInit API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function approveGridinputAwagInitPreCallLogic(model, Context, vm){
    console.log("SVC-CUST:start - approveGridinputAwagInitPreCallLogic");
    console.log(model);
    //TODO customize logic here
    return {checkbox: '100'};
}

/**
 * Custom Business Logic after success gridinput awagInit API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function approveGridinputAwagInitSuccessLogic(model, Context, vm){
    console.log("SVC-CUST:start - approveGridinputAwagInitSuccessLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after error gridinput awagInit API call
 *
 * @param {Object} 加工前エラーメッセージ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @param {Object} httpResponse
 * @return {Object} 加工後エラーメッセージ
 **/
function approveGridinputAwagInitErrorLogic(error, Context, vm, httpResponse){
    console.log("SVC-CUST:start - approveGridinputAwagInitErrorLogic");
    //TODO customize logic here

    return error;
}


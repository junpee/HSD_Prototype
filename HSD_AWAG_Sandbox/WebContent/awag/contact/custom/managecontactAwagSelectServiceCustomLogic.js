"use strict";

/**
 * Custom Business Logic before managecontact awagSelect API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactManagecontactAwagSelectPreCallLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactManagecontactAwagSelectPreCallLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after success managecontact awagSelect API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactManagecontactAwagSelectSuccessLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactManagecontactAwagSelectSuccessLogic");
    console.log(model);
    //TODO customize logic here

    return model;
}

/**
 * Custom Business Logic after error managecontact awagSelect API call
 *
 * @param {Object} 加工前エラーメッセージ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @param {Object} httpResponse
 * @return {Object} 加工後エラーメッセージ
 **/
function contactManagecontactAwagSelectErrorLogic(error, Context, vm, httpResponse){
    console.log("SVC-CUST:start - contactManagecontactAwagSelectErrorLogic");
    //TODO customize logic here

    return error;
}


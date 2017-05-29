"use strict";

/**
 * Custom Business Logic before thanks goback API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactThanksGobackPreCallLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactThanksGobackPreCallLogic");
    console.log(model);
    //TODO customize logic here

    var customer = {customerid: Context.vModels.managecontact.customerid};
    return customer;
}

/**
 * Custom Business Logic after success thanks goback API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactThanksGobackSuccessLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactThanksGobackSuccessLogic");
    console.log(model);
    //TODO customize logic here
    return model;
}


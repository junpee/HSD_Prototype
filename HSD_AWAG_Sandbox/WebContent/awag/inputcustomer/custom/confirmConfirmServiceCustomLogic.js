"use strict";

/**
 * Custom Business Logic before confirm confirm API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function inputcustomerConfirmConfirmPreCallLogic(model, Context, vm){
    console.log("SVC-CUST:start - inputcustomerConfirmConfirmPreCallLogic");
    console.log(model);
    //TODO customize logic here

    model.checkbox = '000';
    model.approvelog = {customerid: model.customerid};

    return model;
}

/**
 * Custom Business Logic after success confirm confirm API call
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function inputcustomerConfirmConfirmSuccessLogic(model, Context, vm){
    console.log("SVC-CUST:start - inputcustomerConfirmConfirmSuccessLogic");
    console.log(model);
    //TODO customize logic here
    return model;
}


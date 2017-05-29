"use strict";

/**
 * Custom Business Logic for event add awagGoback
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactAddAwagGobackCustomLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactAddAwagGobackCustomLogic");
    console.log(model);
    var next = Context.vModels.managecontact;
    //TODO customize logic here

    return next;
}


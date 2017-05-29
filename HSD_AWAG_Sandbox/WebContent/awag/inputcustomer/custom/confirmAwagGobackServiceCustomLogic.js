"use strict";

/**
 * Custom Business Logic for event confirm awagGoback
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function inputcustomerConfirmAwagGobackCustomLogic(model, Context, vm){
    console.log("SVC-CUST:start - inputcustomerConfirmAwagGobackCustomLogic");
    console.log(model);
    var next = Context.vModels.input;
    //TODO customize logic here

    return next;
}


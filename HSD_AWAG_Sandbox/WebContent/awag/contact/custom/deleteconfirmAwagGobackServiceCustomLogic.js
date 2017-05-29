"use strict";

/**
 * Custom Business Logic for event deleteconfirm awagGoback
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactDeleteconfirmAwagGobackCustomLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactDeleteconfirmAwagGobackCustomLogic");
    console.log(model);
    var next = Context.vModels.managecontact;
    //TODO customize logic here

    return next;
}


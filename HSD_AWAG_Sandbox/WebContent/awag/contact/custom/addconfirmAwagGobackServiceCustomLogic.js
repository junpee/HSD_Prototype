"use strict";

/**
 * Custom Business Logic for event addconfirm awagGoback
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactAddconfirmAwagGobackCustomLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactAddconfirmAwagGobackCustomLogic");
    console.log(model);
    var next = Context.vModels.add;
    //TODO customize logic here

    return next;
}


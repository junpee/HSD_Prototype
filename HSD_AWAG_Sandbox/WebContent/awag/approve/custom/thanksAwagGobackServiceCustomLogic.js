"use strict";

/**
 * Custom Business Logic for event thanks awagGoback
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function approveThanksAwagGobackCustomLogic(model, Context, vm){
    console.log("SVC-CUST:start - approveThanksAwagGobackCustomLogic");
    console.log(model);
    var next = Context.vModels.gridinput;
    //TODO customize logic here

    return next;
}


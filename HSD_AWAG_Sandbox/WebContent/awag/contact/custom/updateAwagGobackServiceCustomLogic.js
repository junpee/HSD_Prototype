"use strict";

/**
 * Custom Business Logic for event update awagGoback
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactUpdateAwagGobackCustomLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactUpdateAwagGobackCustomLogic");
    console.log(model);
    var next = Context.vModels.managecontact;
    //TODO customize logic here

    return next;
}


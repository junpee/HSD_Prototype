"use strict";

/**
 * Custom Business Logic for event thanks awagGoback
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function maintenanceThanksAwagGobackCustomLogic(model, Context, vm){
    console.log("SVC-CUST:start - maintenanceThanksAwagGobackCustomLogic");
    console.log(model);
    var next = Context.vModels.search;
    //TODO customize logic here

    return next;
}


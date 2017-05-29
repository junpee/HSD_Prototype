"use strict";

/**
 * Custom Business Logic for event view awagGoback
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function maintenanceViewAwagGobackCustomLogic(model, Context, vm){
    console.log("SVC-CUST:start - maintenanceViewAwagGobackCustomLogic");
    console.log(model);
    var next = Context.vModels.search;
    //TODO customize logic here

    return next;
}


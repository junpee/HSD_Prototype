"use strict";

/**
 * Custom Business Logic for event confirm awagGoback
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function maintenanceConfirmAwagGobackCustomLogic(model, Context, vm){
    console.log("SVC-CUST:start - maintenanceConfirmAwagGobackCustomLogic");
    console.log(model);
    var next = Context.vModels.update;
    //TODO customize logic here

    return next;
}


"use strict";

/**
 * Custom Business Logic for event managecontact add
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function contactManagecontactAddCustomLogic(model, Context, vm){
    console.log("SVC-CUST:start - contactManagecontactAddCustomLogic");
    console.log(model);
    //TODO customize logic here

    var max = 0;
    var num = 0;
    if(Context.vModels.managecontact.contacts){
        for(var i=0;i<Context.vModels.managecontact.contacts.length;i++){
            var num = Number(Context.vModels.managecontact.contacts[i].contactid);
            if(num > max){
                max = num;
            }
        }
    }
    var next = {customerid: model.customerid, contactid: num + 1};

    return next;
}


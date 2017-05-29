"use strict";

/**
 * Custom Business Logic for event input confirm
 *
 * @param {Object} 加工前画面データ
 * @param {Object} Context情報
 * @param {Object} Angular Controller
 * @return {Object} 加工後画面データ
 **/
function inputcustomerInputConfirmCustomLogic(model, Context, vm){
    console.log("SVC-CUST:start - inputcustomerInputConfirmCustomLogic");
    console.log(model);
    var next = model;
    //TODO customize logic here
    //相関チェック
    if(model.nameen.startsWith("IBM")){//nameenは必須項目
        if(Context.error == null) Context.error = {details:[]};
        var msg = {code:"E003", message: "IBMで始まる登録名は禁止です。"};
        Context.error.details.push(msg);
        Context.nextPath = null;//次画面をクリアします。
    }

    return next;
}


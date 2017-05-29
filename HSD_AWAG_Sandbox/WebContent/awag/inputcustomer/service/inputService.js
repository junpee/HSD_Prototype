"use strict";
var app = angular.module('awag.inputcustomer');


/**
 * inputcustomer 登録 登録確認 Service
 **/
app.service('inputcustomerInputConfirm', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - inputcustomerInputConfirm.execute()");

            //遷移イベント
            Context.nextPath='/inputcustomer/confirm';
            //custom logic
            Context.vModels.confirm = inputcustomerInputConfirmCustomLogic(vm.model, Context, vm);
            //next page
            if(Context.nextPath != null){
                $location.path(Context.nextPath);
            }
            //loading
            hideLoading();
            $timeout(MKDT, 0, false);
        };
    }
]);


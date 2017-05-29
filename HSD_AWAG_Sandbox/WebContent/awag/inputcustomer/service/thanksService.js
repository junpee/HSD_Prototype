"use strict";
var app = angular.module('awag.inputcustomer');


/**
 * inputcustomer 登録完了 お客様登録に戻る Service
 **/
app.service('inputcustomerThanksAwagGoblank', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - inputcustomerThanksAwagGoblank.execute()");

            //遷移イベント
            Context.nextPath='/inputcustomer/input';
            //custom logic
            Context.vModels.input = inputcustomerThanksAwagGoblankCustomLogic(vm.model, Context, vm);
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


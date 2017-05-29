"use strict";
var app = angular.module('awag.inputcustomer');


/**
 * inputcustomer 登録確認 登録 Service
 **/
app.service('inputcustomerConfirmConfirm', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - inputcustomerConfirmConfirm.execute()");

            //pre API call logic
            var param = inputcustomerConfirmConfirmPreCallLogic(vm.model, Context, vm);
            //clear empty params
            param = JsonUtils.clearEmptyParams(param);
            var success = function(httpResponse) {
                var value = httpResponse.data;
                if(value===""){
                    value = {};
                }
                //success
                Context.nextPath='/inputcustomer/thanks';
                //custom logic
                Context.vModels.thanks = inputcustomerConfirmConfirmSuccessLogic(value, Context, vm);
                //go next screen
                if(Context.nextPath != null){
                    $location.path(Context.nextPath);
                }
                //loading
                hideLoading();
                $timeout(MKDT, 0, false);
            };
            var error = function(httpResponse) {
                vm.error = ErrorHandler.handleError(param, httpResponse);
                vm.error = inputcustomerConfirmConfirmErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.post(contextRoot + '/customer', param).then(success, error);
        };
    }
]);


/**
 * inputcustomer 登録確認 入力へ戻る Service
 **/
app.service('inputcustomerConfirmAwagGoback', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - inputcustomerConfirmAwagGoback.execute()");

            //遷移イベント
            Context.nextPath='/inputcustomer/input';
            //custom logic
            Context.vModels.input = inputcustomerConfirmAwagGobackCustomLogic(vm.model, Context, vm);
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


/**
 * inputcustomer 登録確認 初期処理 Service
 **/
app.service('inputcustomerConfirmAwagInit', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - inputcustomerConfirmAwagInit.execute()");

            //同画面イベント
            //custom logic
            vm.model = inputcustomerConfirmAwagInitCustomLogic(vm.model, Context, vm);
            //loading
            hideLoading();
            $timeout(MKDT, 0, false);
        };
    }
]);


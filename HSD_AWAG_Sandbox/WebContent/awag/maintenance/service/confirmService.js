"use strict";
var app = angular.module('awag.maintenance');


/**
 * maintenance 更新確認 入力へ戻る Service
 **/
app.service('maintenanceConfirmAwagGoback', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - maintenanceConfirmAwagGoback.execute()");

            //遷移イベント
            Context.nextPath='/maintenance/update';
            //custom logic
            Context.vModels.update = maintenanceConfirmAwagGobackCustomLogic(vm.model, Context, vm);
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
 * maintenance 更新確認 更新 Service
 **/
app.service('maintenanceConfirmUpdate', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - maintenanceConfirmUpdate.execute()");

            //pre API call logic
            var param = maintenanceConfirmUpdatePreCallLogic(vm.model, Context, vm);
            //clear empty params
            param = JsonUtils.clearEmptyParams(param);
            var success = function(httpResponse) {
                var value = httpResponse.data;
                if(value===""){
                    value = {};
                }
                //success
                Context.nextPath='/maintenance/thanks';
                //custom logic
                Context.vModels.thanks = maintenanceConfirmUpdateSuccessLogic(value, Context, vm);
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
                vm.error = maintenanceConfirmUpdateErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.put(contextRoot + '/customer', param).then(success, error);
        };
    }
]);


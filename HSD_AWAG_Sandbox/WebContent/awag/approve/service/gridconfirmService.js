"use strict";
var app = angular.module('awag.approve');


/**
 * approve 承認確認 更新 Service
 **/
app.service('approveGridconfirmConfirm', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - approveGridconfirmConfirm.execute()");

            //pre API call logic
            var param = approveGridconfirmConfirmPreCallLogic(vm.model, Context, vm);
            //clear empty params
            param = JsonUtils.clearEmptyParams(param);
            var success = function(httpResponse) {
                var value = httpResponse.data;
                if(value===""){
                    value = {};
                }
                //success
                Context.nextPath='/approve/thanks';
                //custom logic
                Context.vModels.thanks = approveGridconfirmConfirmSuccessLogic(value, Context, vm);
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
                vm.error = approveGridconfirmConfirmErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.put(contextRoot + '/approvelist/', param).then(success, error);
        };
    }
]);


/**
 * approve 承認確認 戻る Service
 **/
app.service('approveGridconfirmBack', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - approveGridconfirmBack.execute()");

            //遷移イベント
            Context.nextPath='/approve/gridinput';
            //custom logic
            Context.vModels.gridinput = approveGridconfirmBackCustomLogic(vm.model, Context, vm);
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


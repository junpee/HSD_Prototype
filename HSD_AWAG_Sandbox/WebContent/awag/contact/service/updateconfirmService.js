"use strict";
var app = angular.module('awag.contact');


/**
 * contact コンタクト更新確認 更新 Service
 **/
app.service('contactUpdateconfirmUpdate', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactUpdateconfirmUpdate.execute()");

            //pre API call logic
            var param = contactUpdateconfirmUpdatePreCallLogic(vm.model, Context, vm);
            //clear empty params
            param = JsonUtils.clearEmptyParams(param);
            var success = function(httpResponse) {
                var value = httpResponse.data;
                if(value===""){
                    value = {};
                }
                //success
                Context.nextPath='/contact/thanks';
                //custom logic
                Context.vModels.thanks = contactUpdateconfirmUpdateSuccessLogic(value, Context, vm);
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
                vm.error = contactUpdateconfirmUpdateErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.put(contextRoot + '/contact', param).then(success, error);
        };
    }
]);


/**
 * contact コンタクト更新確認 戻る Service
 **/
app.service('contactUpdateconfirmAwagGoback', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactUpdateconfirmAwagGoback.execute()");

            //遷移イベント
            Context.nextPath='/contact/update';
            //custom logic
            Context.vModels.update = contactUpdateconfirmAwagGobackCustomLogic(vm.model, Context, vm);
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


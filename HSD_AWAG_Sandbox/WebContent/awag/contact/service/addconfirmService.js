"use strict";
var app = angular.module('awag.contact');


/**
 * contact コンタクト追加確認 追加 Service
 **/
app.service('contactAddconfirmAdd', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactAddconfirmAdd.execute()");

            //pre API call logic
            var param = contactAddconfirmAddPreCallLogic(vm.model, Context, vm);
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
                Context.vModels.thanks = contactAddconfirmAddSuccessLogic(value, Context, vm);
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
                vm.error = contactAddconfirmAddErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.post(contextRoot + '/contact', param).then(success, error);
        };
    }
]);


/**
 * contact コンタクト追加確認 戻る Service
 **/
app.service('contactAddconfirmAwagGoback', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactAddconfirmAwagGoback.execute()");

            //遷移イベント
            Context.nextPath='/contact/add';
            //custom logic
            Context.vModels.add = contactAddconfirmAwagGobackCustomLogic(vm.model, Context, vm);
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


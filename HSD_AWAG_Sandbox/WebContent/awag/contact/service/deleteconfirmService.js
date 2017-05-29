"use strict";
var app = angular.module('awag.contact');


/**
 * contact コンタクト削除確認 削除 Service
 **/
app.service('contactDeleteconfirmDelete', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactDeleteconfirmDelete.execute()");

            //pre API call logic
            var param = contactDeleteconfirmDeletePreCallLogic(vm.model, Context, vm);
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
                Context.vModels.thanks = contactDeleteconfirmDeleteSuccessLogic(value, Context, vm);
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
                vm.error = contactDeleteconfirmDeleteErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.delete(contextRoot + '/contact', {
                headers: {'Content-Type': 'application/json'},
                data: param
            }).then(success, error);
        };
    }
]);


/**
 * contact コンタクト削除確認 戻る Service
 **/
app.service('contactDeleteconfirmAwagGoback', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactDeleteconfirmAwagGoback.execute()");

            //遷移イベント
            Context.nextPath='/contact/managecontact';
            //custom logic
            Context.vModels.managecontact = contactDeleteconfirmAwagGobackCustomLogic(vm.model, Context, vm);
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


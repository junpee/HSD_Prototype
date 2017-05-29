"use strict";
var app = angular.module('awag.contact');


/**
 * contact コンタクト更新完了 コンタクト一覧に戻る Service
 **/
app.service('contactThanksGoback', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactThanksGoback.execute()");

            //pre API call logic
            var param = contactThanksGobackPreCallLogic(vm.model, Context, vm);
            //clear empty params
            param = JsonUtils.clearEmptyParams(param);
            var success = function(httpResponse) {
                var value = httpResponse.data;
                if(value===""){
                    value = {};
                }
                //success
                Context.nextPath='/contact/managecontact';
                $log.debug(value);

                //custom logic
                Context.vModels.managecontact = contactThanksGobackSuccessLogic(value, Context, vm);


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
                vm.error = contactThanksGobackErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.get(contextRoot + '/contact/'+encodeURIComponent(param.customerid) + JsonUtils.getQueryParamString(param)).then(success, error);
        };
    }
]);


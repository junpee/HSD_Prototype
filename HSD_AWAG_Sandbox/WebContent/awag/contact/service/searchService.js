"use strict";
var app = angular.module('awag.contact');


/**
 * contact コンタクトお客様検索 検索 Service
 **/
app.service('contactSearchAwagSearch', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactSearchAwagSearch.execute()");

            //pre API call logic
            var param = contactSearchAwagSearchPreCallLogic(vm.model.search, Context, vm);
            //clear empty params
            param = JsonUtils.clearEmptyParams(param);
            var success = function(httpResponse) {
                var value = httpResponse.data;
                if(value===""){
                    value = {};
                }
                //success
                vm.pager.pagerList = [];
                if(!value.list) {
                    value.list = [];
                }
                $log.debug(value.list.length + ' items');
                value.search = vm.model.search;

                //custom logic
                vm.model = contactSearchAwagSearchSuccessLogic(value, Context, vm);

                //setup pager
                for (var i = 0; i < Math.floor((value.list.length - 1) / vm.pager.len) + 1; i++) {
                    vm.pager.pagerList[i] = i;
                }
                vm.pager.start = 0;
                //no record
                if (httpResponse.status===204 || value.list.length === 0) {
                    var msg;
                    if(Context.tmpMessage){
                        msg = Context.tmpMessage;
                        delete Context.tmpMessage;
                    } else {
                        msg = MSG_SEARCH_204;
                    }
                    vm.error = ErrorHandler.handleError(param, httpResponse.headers, msg);
                } else {
                }

                //loading
                hideLoading();
                $timeout(MKDT, 0, false);
            };
            var error = function(httpResponse) {
                vm.error = ErrorHandler.handleError(param, httpResponse);
                vm.error = contactSearchAwagSearchErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.get(contextRoot + '/contact/search' + JsonUtils.getQueryParamString(param)).then(success, error);
        };
    }
]);


/**
 * contact コンタクトお客様検索 コンタクト履歴へ Service
 **/
app.service('contactSearchGoContact', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactSearchGoContact.execute()");

            //遷移イベント
            Context.nextPath='/contact/managecontact';
            //custom logic
            Context.vModels.managecontact = contactSearchGoContactCustomLogic(Context.nextModel, Context, vm);
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
 * contact コンタクトお客様検索 1行選択 Service
 **/
app.service('contactSearchAwagSelect', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactSearchAwagSelect.execute()");

            //pre API call logic
            var param = contactSearchAwagSelectPreCallLogic(vm.selectedKey, Context, vm);
            //clear empty params
            param = JsonUtils.clearEmptyParams(param);
            var success = function(httpResponse) {
                var value = httpResponse.data;
                if(value===""){
                    value = {};
                }
                //success
                $log.debug(value);

                //custom logic
                Context.nextModel = contactSearchAwagSelectSuccessLogic(value, Context, vm);


                //no record
                if (httpResponse.status===204) {
                    var msg;
                    if(Context.tmpMessage){
                        msg = Context.tmpMessage;
                        delete Context.tmpMessage;
                    } else {
                        msg = MSG_GET_204;
                    }
                    vm.error = ErrorHandler.handleError(param, httpResponse.headers, msg);
                } else {
                    vm.modelSelected = true;
                }
                //loading
                hideLoading();
                $timeout(MKDT, 0, false);
            };
            var error = function(httpResponse) {
                vm.error = ErrorHandler.handleError(param, httpResponse);
                vm.error = contactSearchAwagSelectErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.get(contextRoot + '/contact/'+encodeURIComponent(param.customerid) + JsonUtils.getQueryParamString(param)).then(success, error);
        };
    }
]);


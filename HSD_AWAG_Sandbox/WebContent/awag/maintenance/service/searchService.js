"use strict";
var app = angular.module('awag.maintenance');


/**
 * maintenance お客様検索 検索 Service
 **/
app.service('maintenanceSearchAwagSearch', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - maintenanceSearchAwagSearch.execute()");

            //pre API call logic
            var param = maintenanceSearchAwagSearchPreCallLogic(vm.model.search, Context, vm);
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
                vm.model = maintenanceSearchAwagSearchSuccessLogic(value, Context, vm);

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
                vm.error = maintenanceSearchAwagSearchErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.get(contextRoot + '/maintenance/search' + JsonUtils.getQueryParamString(param)).then(success, error);
        };
    }
]);


/**
 * maintenance お客様検索 詳細表示 Service
 **/
app.service('maintenanceSearchView', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - maintenanceSearchView.execute()");

            //遷移イベント
            Context.nextPath='/maintenance/view';
            //custom logic
            Context.vModels.view = maintenanceSearchViewCustomLogic(Context.nextModel, Context, vm);
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
 * maintenance お客様検索 更新 Service
 **/
app.service('maintenanceSearchUpdate', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - maintenanceSearchUpdate.execute()");

            //遷移イベント
            Context.nextPath='/maintenance/update';
            //custom logic
            Context.vModels.update = maintenanceSearchUpdateCustomLogic(Context.nextModel, Context, vm);
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
 * maintenance お客様検索 選択 Service
 **/
app.service('maintenanceSearchAwagSelect', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - maintenanceSearchAwagSelect.execute()");

            //pre API call logic
            var param = maintenanceSearchAwagSelectPreCallLogic(vm.selectedKey, Context, vm);
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
                Context.nextModel = maintenanceSearchAwagSelectSuccessLogic(value, Context, vm);


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
                vm.error = maintenanceSearchAwagSelectErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.get(contextRoot + '/customer/'+encodeURIComponent(param.customerid) + JsonUtils.getQueryParamString(param)).then(success, error);
        };
    }
]);


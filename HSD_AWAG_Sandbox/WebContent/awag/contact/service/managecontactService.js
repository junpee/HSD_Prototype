"use strict";
var app = angular.module('awag.contact');


/**
 * contact お客様コンタクト一覧 詳細確認 Service
 **/
app.service('contactManagecontactShowDetail', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactManagecontactShowDetail.execute()");

            //遷移イベント
            Context.nextPath='/contact/view';
            //custom logic
            Context.vModels.view = contactManagecontactShowDetailCustomLogic(Context.nextModel, Context, vm);
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
 * contact お客様コンタクト一覧 コンタクト履歴更新 Service
 **/
app.service('contactManagecontactUpdateContact', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactManagecontactUpdateContact.execute()");

            //遷移イベント
            Context.nextPath='/contact/update';
            //custom logic
            Context.vModels.update = contactManagecontactUpdateContactCustomLogic(Context.nextModel, Context, vm);
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
 * contact お客様コンタクト一覧 コンタクト履歴削除 Service
 **/
app.service('contactManagecontactDeleteContact', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactManagecontactDeleteContact.execute()");

            //遷移イベント
            Context.nextPath='/contact/deleteconfirm';
            //custom logic
            Context.vModels.deleteconfirm = contactManagecontactDeleteContactCustomLogic(Context.nextModel, Context, vm);
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
 * contact お客様コンタクト一覧 1行選択 Service
 **/
app.service('contactManagecontactAwagSelect', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactManagecontactAwagSelect.execute()");

            //pre API call logic
            var param = contactManagecontactAwagSelectPreCallLogic(vm.selectedKey, Context, vm);
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
                Context.nextModel = contactManagecontactAwagSelectSuccessLogic(value, Context, vm);


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
                vm.error = contactManagecontactAwagSelectErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.get(contextRoot + '/contact/'+encodeURIComponent(param.customerid)+'.'+encodeURIComponent(param.contactid) + JsonUtils.getQueryParamString(param)).then(success, error);
        };
    }
]);


/**
 * contact お客様コンタクト一覧 コンタクト履歴追加 Service
 **/
app.service('contactManagecontactAdd', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactManagecontactAdd.execute()");

            //遷移イベント
            Context.nextPath='/contact/add';
            //custom logic
            Context.vModels.add = contactManagecontactAddCustomLogic(Context.nextModel, Context, vm);
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


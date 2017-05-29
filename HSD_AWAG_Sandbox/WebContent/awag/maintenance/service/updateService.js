"use strict";
var app = angular.module('awag.maintenance');


/**
 * maintenance 更新 検索へ戻る Service
 **/
app.service('maintenanceUpdateAwagGoback', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - maintenanceUpdateAwagGoback.execute()");

            //遷移イベント
            Context.nextPath='/maintenance/search';
            //custom logic
            Context.vModels.search = maintenanceUpdateAwagGobackCustomLogic(vm.model, Context, vm);
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
 * maintenance 更新 更新確認 Service
 **/
app.service('maintenanceUpdateConfirm', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - maintenanceUpdateConfirm.execute()");

            //遷移イベント
            Context.nextPath='/maintenance/confirm';
            //custom logic
            Context.vModels.confirm = maintenanceUpdateConfirmCustomLogic(vm.model, Context, vm);
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


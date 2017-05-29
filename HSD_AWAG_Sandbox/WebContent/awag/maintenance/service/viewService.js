"use strict";
var app = angular.module('awag.maintenance');


/**
 * maintenance 詳細表示 検索へ戻る Service
 **/
app.service('maintenanceViewAwagGoback', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - maintenanceViewAwagGoback.execute()");

            //遷移イベント
            Context.nextPath='/maintenance/search';
            //custom logic
            Context.vModels.search = maintenanceViewAwagGobackCustomLogic(vm.model, Context, vm);
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


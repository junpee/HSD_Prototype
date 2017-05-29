"use strict";
var app = angular.module('awag.maintenance');


/**
 * maintenance 更新完了 お客様検索に戻る Service
 **/
app.service('maintenanceThanksAwagGoback', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - maintenanceThanksAwagGoback.execute()");

            //遷移イベント
            Context.nextPath='/maintenance/search';
            //custom logic
            Context.vModels.search = maintenanceThanksAwagGobackCustomLogic(vm.model, Context, vm);
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


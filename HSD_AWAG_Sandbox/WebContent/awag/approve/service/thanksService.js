"use strict";
var app = angular.module('awag.approve');


/**
 * approve 承認完了 未承認一覧に戻る Service
 **/
app.service('approveThanksAwagGoback', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - approveThanksAwagGoback.execute()");

            //遷移イベント
            Context.nextPath='/approve/gridinput';
            //custom logic
            Context.vModels.gridinput = approveThanksAwagGobackCustomLogic(vm.model, Context, vm);
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


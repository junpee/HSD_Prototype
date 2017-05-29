"use strict";
var app = angular.module('awag.contact');


/**
 * contact コンタクト詳細表示 戻る Service
 **/
app.service('contactViewAwagGoback', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactViewAwagGoback.execute()");

            //遷移イベント
            Context.nextPath='/contact/managecontact';
            //custom logic
            Context.vModels.managecontact = contactViewAwagGobackCustomLogic(vm.model, Context, vm);
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


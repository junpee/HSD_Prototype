"use strict";
var app = angular.module('awag.contact');


/**
 * contact コンタクト更新 確認へ Service
 **/
app.service('contactUpdateConfirm', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactUpdateConfirm.execute()");

            //遷移イベント
            Context.nextPath='/contact/updateconfirm';
            //custom logic
            Context.vModels.updateconfirm = contactUpdateConfirmCustomLogic(vm.model, Context, vm);
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
 * contact コンタクト更新 戻る Service
 **/
app.service('contactUpdateAwagGoback', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactUpdateAwagGoback.execute()");

            //遷移イベント
            Context.nextPath='/contact/managecontact';
            //custom logic
            Context.vModels.managecontact = contactUpdateAwagGobackCustomLogic(vm.model, Context, vm);
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


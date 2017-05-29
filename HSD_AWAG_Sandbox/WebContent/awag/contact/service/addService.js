"use strict";
var app = angular.module('awag.contact');


/**
 * contact コンタクト追加 確認へ Service
 **/
app.service('contactAddConfirm', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactAddConfirm.execute()");

            //遷移イベント
            Context.nextPath='/contact/addconfirm';
            //custom logic
            Context.vModels.addconfirm = contactAddConfirmCustomLogic(vm.model, Context, vm);
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
 * contact コンタクト追加 戻る Service
 **/
app.service('contactAddAwagGoback', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - contactAddAwagGoback.execute()");

            //遷移イベント
            Context.nextPath='/contact/managecontact';
            //custom logic
            Context.vModels.managecontact = contactAddAwagGobackCustomLogic(vm.model, Context, vm);
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


"use strict";
var app = angular.module('awag.inputcustomer');

/**
 * controller for 登録確認
 **/
app.controller('inputcustomerConfirmCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'inputcustomerConfirmConfirm', 'inputcustomerConfirmAwagGoback', 'inputcustomerConfirmAwagInit', 
    'rankOpts', 'class1Opts', 'class2Opts', 
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , inputcustomerConfirmConfirm, inputcustomerConfirmAwagGoback, inputcustomerConfirmAwagInit
    , rankOpts, class1Opts, class2Opts) {
        var vm = this;
        $window.document.title = "登録確認";
        vm.getNameFromValue = NameValue.getName;
        vm.rankOpts = rankOpts;
        vm.class1Opts = class1Opts;
        vm.class2Opts = class2Opts;
        vm.model = Context.vModels.confirm;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.model = inputcustomerConfirmInitialize(vm.model, Context, vm);

        /**
         * call confirm service
         **/
        vm.confirm = function() {
            $log.debug("CTR-EVNT:start - confirm");
            Context.vModels.confirm = vm.model;
            //loading
            showLoading();
            inputcustomerConfirmConfirm.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call awagGoback service
         **/
        vm.awagGoback = function() {
            $log.debug("CTR-EVNT:start - awagGoback");
            Context.vModels.confirm = vm.model;
            //loading
            showLoading();
            inputcustomerConfirmAwagGoback.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call awagInit service
         **/
        vm.awagInit = function() {
            $log.debug("CTR-EVNT:start - awagInit");
            Context.vModels.confirm = vm.model;
            //loading
            showLoading();
            inputcustomerConfirmAwagInit.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //call init event
        vm.awagInit();

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

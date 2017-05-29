"use strict";
var app = angular.module('awag.inputcustomer');

/**
 * controller for 登録
 **/
app.controller('inputcustomerInputCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'inputcustomerInputConfirm', 
    'rankOpts', 'class1Opts', 'class2Opts', 
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , inputcustomerInputConfirm
    , rankOpts, class1Opts, class2Opts) {
        var vm = this;
        $window.document.title = "登録";
        vm.getNameFromValue = NameValue.getName;
        vm.rankOpts = rankOpts;
        vm.class1Opts = class1Opts;
        vm.class2Opts = class2Opts;
        vm.model = Context.vModels.input;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.model = inputcustomerInputInitialize(vm.model, Context, vm);

        /**
         * call confirm service
         **/
        vm.confirm = function() {
            $log.debug("CTR-EVNT:start - confirm");
            Context.vModels.input = vm.model;
            //loading
            showLoading();
            inputcustomerInputConfirm.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

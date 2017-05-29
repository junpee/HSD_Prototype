"use strict";
var app = angular.module('awag.approve');

/**
 * controller for 未承認一覧
 **/
app.controller('approveGridinputCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'approveGridinputAwagGridConfirm', 'approveGridinputAwagInit', 
    'rankOpts2', 'class2Opts', 'approveOpts', 
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , approveGridinputAwagGridConfirm, approveGridinputAwagInit
    , rankOpts2, class2Opts, approveOpts) {
        var vm = this;
        $window.document.title = "未承認一覧";
        vm.getNameFromValue = NameValue.getName;
        vm.rankOpts2 = rankOpts2;
        vm.class2Opts = class2Opts;
        vm.approveOpts = approveOpts;
        vm.model = Context.vModels.gridinput;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        /**
         * returns true when checkbox is selected
         **/
        vm.awagIsCheckboxSelected = function() {
            if (vm.model.list) {
                for (var i=0;i<vm.model.list.length;i++) {
                    if (vm.model.list[i].selected) {
                        return true;
                    }
                }
            }
            return false;
        };

        vm.model = approveGridinputInitialize(vm.model, Context, vm);

        /**
         * call awagGridConfirm service
         **/
        vm.awagGridConfirm = function() {
            $log.debug("CTR-EVNT:start - awagGridConfirm");
            Context.vModels.gridinput = vm.model;
            //loading
            showLoading();
            approveGridinputAwagGridConfirm.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call awagInit service
         **/
        vm.awagInit = function() {
            $log.debug("CTR-EVNT:start - awagInit");
            Context.vModels.gridinput = vm.model;
            //loading
            showLoading();
            approveGridinputAwagInit.execute(vm);
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

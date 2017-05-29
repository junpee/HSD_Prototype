"use strict";
var app = angular.module('awag.approve');

/**
 * controller for 承認確認
 **/
app.controller('approveGridconfirmCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'approveGridconfirmConfirm', 'approveGridconfirmBack', 
    'rankOpts2', 'class2Opts', 'approveOpts', 
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , approveGridconfirmConfirm, approveGridconfirmBack
    , rankOpts2, class2Opts, approveOpts) {
        var vm = this;
        $window.document.title = "承認確認";
        vm.getNameFromValue = NameValue.getName;
        vm.rankOpts2 = rankOpts2;
        vm.class2Opts = class2Opts;
        vm.approveOpts = approveOpts;
        vm.model = Context.vModels.gridconfirm;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.model = approveGridconfirmInitialize(vm.model, Context, vm);

        /**
         * call confirm service
         **/
        vm.confirm = function() {
            $log.debug("CTR-EVNT:start - confirm");
            Context.vModels.gridconfirm = vm.model;
            //loading
            showLoading();
            approveGridconfirmConfirm.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call back service
         **/
        vm.back = function() {
            $log.debug("CTR-EVNT:start - back");
            Context.vModels.gridconfirm = vm.model;
            //loading
            showLoading();
            approveGridconfirmBack.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

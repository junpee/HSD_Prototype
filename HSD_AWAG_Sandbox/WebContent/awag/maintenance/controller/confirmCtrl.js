"use strict";
var app = angular.module('awag.maintenance');

/**
 * controller for 更新確認
 **/
app.controller('maintenanceConfirmCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'maintenanceConfirmAwagGoback', 'maintenanceConfirmUpdate', 
    'rankOpts', 'class1Opts', 'class2Opts', 'approveOpts', 
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , maintenanceConfirmAwagGoback, maintenanceConfirmUpdate
    , rankOpts, class1Opts, class2Opts, approveOpts) {
        var vm = this;
        $window.document.title = "更新確認";
        vm.getNameFromValue = NameValue.getName;
        vm.rankOpts = rankOpts;
        vm.class1Opts = class1Opts;
        vm.class2Opts = class2Opts;
        vm.approveOpts = approveOpts;
        vm.model = Context.vModels.confirm;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.model = maintenanceConfirmInitialize(vm.model, Context, vm);

        /**
         * call awagGoback service
         **/
        vm.awagGoback = function() {
            $log.debug("CTR-EVNT:start - awagGoback");
            Context.vModels.confirm = vm.model;
            //loading
            showLoading();
            maintenanceConfirmAwagGoback.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call update service
         **/
        vm.update = function() {
            $log.debug("CTR-EVNT:start - update");
            Context.vModels.confirm = vm.model;
            //loading
            showLoading();
            maintenanceConfirmUpdate.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

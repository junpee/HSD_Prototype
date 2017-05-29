"use strict";
var app = angular.module('awag.maintenance');

/**
 * controller for 詳細表示
 **/
app.controller('maintenanceViewCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'maintenanceViewAwagGoback', 
    'rankOpts', 'class1Opts', 'class2Opts', 'approveOpts', 
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , maintenanceViewAwagGoback
    , rankOpts, class1Opts, class2Opts, approveOpts) {
        var vm = this;
        $window.document.title = "詳細表示";
        vm.getNameFromValue = NameValue.getName;
        vm.rankOpts = rankOpts;
        vm.class1Opts = class1Opts;
        vm.class2Opts = class2Opts;
        vm.approveOpts = approveOpts;
        vm.model = Context.vModels.view;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.model = maintenanceViewInitialize(vm.model, Context, vm);

        /**
         * call awagGoback service
         **/
        vm.awagGoback = function() {
            $log.debug("CTR-EVNT:start - awagGoback");
            Context.vModels.view = vm.model;
            //loading
            showLoading();
            maintenanceViewAwagGoback.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

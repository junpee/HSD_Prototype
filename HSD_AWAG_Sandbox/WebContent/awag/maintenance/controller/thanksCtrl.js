"use strict";
var app = angular.module('awag.maintenance');

/**
 * controller for 更新完了
 **/
app.controller('maintenanceThanksCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'maintenanceThanksAwagGoback', 
    
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , maintenanceThanksAwagGoback
    ) {
        var vm = this;
        $window.document.title = "更新完了";
        vm.getNameFromValue = NameValue.getName;
        vm.model = Context.vModels.thanks;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.model = maintenanceThanksInitialize(vm.model, Context, vm);

        /**
         * call awagGoback service
         **/
        vm.awagGoback = function() {
            $log.debug("CTR-EVNT:start - awagGoback");
            Context.vModels.thanks = vm.model;
            //loading
            showLoading();
            maintenanceThanksAwagGoback.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

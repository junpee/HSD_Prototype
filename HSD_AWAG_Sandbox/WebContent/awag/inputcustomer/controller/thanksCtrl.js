"use strict";
var app = angular.module('awag.inputcustomer');

/**
 * controller for 登録完了
 **/
app.controller('inputcustomerThanksCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'inputcustomerThanksAwagGoblank', 
    
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , inputcustomerThanksAwagGoblank
    ) {
        var vm = this;
        $window.document.title = "登録完了";
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
        vm.model = inputcustomerThanksInitialize(vm.model, Context, vm);

        /**
         * call awagGoblank service
         **/
        vm.awagGoblank = function() {
            $log.debug("CTR-EVNT:start - awagGoblank");
            Context.vModels.thanks = vm.model;
            //loading
            showLoading();
            inputcustomerThanksAwagGoblank.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

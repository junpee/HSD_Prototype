"use strict";
var app = angular.module('awag.contact');

/**
 * controller for コンタクト更新
 **/
app.controller('contactUpdateCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'contactUpdateConfirm', 'contactUpdateAwagGoback', 
    
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , contactUpdateConfirm, contactUpdateAwagGoback
    ) {
        var vm = this;
        $window.document.title = "コンタクト更新";
        vm.getNameFromValue = NameValue.getName;
        vm.model = Context.vModels.update;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.model = contactUpdateInitialize(vm.model, Context, vm);

        /**
         * call confirm service
         **/
        vm.confirm = function() {
            $log.debug("CTR-EVNT:start - confirm");
            Context.vModels.update = vm.model;
            //loading
            showLoading();
            contactUpdateConfirm.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call awagGoback service
         **/
        vm.awagGoback = function() {
            $log.debug("CTR-EVNT:start - awagGoback");
            Context.vModels.update = vm.model;
            //loading
            showLoading();
            contactUpdateAwagGoback.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

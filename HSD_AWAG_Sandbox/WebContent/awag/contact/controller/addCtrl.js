"use strict";
var app = angular.module('awag.contact');

/**
 * controller for コンタクト追加
 **/
app.controller('contactAddCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'contactAddConfirm', 'contactAddAwagGoback', 
    
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , contactAddConfirm, contactAddAwagGoback
    ) {
        var vm = this;
        $window.document.title = "コンタクト追加";
        vm.getNameFromValue = NameValue.getName;
        vm.model = Context.vModels.add;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.model = contactAddInitialize(vm.model, Context, vm);

        /**
         * call confirm service
         **/
        vm.confirm = function() {
            $log.debug("CTR-EVNT:start - confirm");
            Context.vModels.add = vm.model;
            //loading
            showLoading();
            contactAddConfirm.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call awagGoback service
         **/
        vm.awagGoback = function() {
            $log.debug("CTR-EVNT:start - awagGoback");
            Context.vModels.add = vm.model;
            //loading
            showLoading();
            contactAddAwagGoback.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

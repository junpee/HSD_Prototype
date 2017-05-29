"use strict";
var app = angular.module('awag.contact');

/**
 * controller for コンタクト追加確認
 **/
app.controller('contactAddconfirmCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'contactAddconfirmAdd', 'contactAddconfirmAwagGoback', 
    
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , contactAddconfirmAdd, contactAddconfirmAwagGoback
    ) {
        var vm = this;
        $window.document.title = "コンタクト追加確認";
        vm.getNameFromValue = NameValue.getName;
        vm.model = Context.vModels.addconfirm;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.model = contactAddconfirmInitialize(vm.model, Context, vm);

        /**
         * call add service
         **/
        vm.add = function() {
            $log.debug("CTR-EVNT:start - add");
            Context.vModels.addconfirm = vm.model;
            //loading
            showLoading();
            contactAddconfirmAdd.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call awagGoback service
         **/
        vm.awagGoback = function() {
            $log.debug("CTR-EVNT:start - awagGoback");
            Context.vModels.addconfirm = vm.model;
            //loading
            showLoading();
            contactAddconfirmAwagGoback.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

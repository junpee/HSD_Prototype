"use strict";
var app = angular.module('awag.contact');

/**
 * controller for コンタクト更新確認
 **/
app.controller('contactUpdateconfirmCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'contactUpdateconfirmUpdate', 'contactUpdateconfirmAwagGoback', 
    
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , contactUpdateconfirmUpdate, contactUpdateconfirmAwagGoback
    ) {
        var vm = this;
        $window.document.title = "コンタクト更新確認";
        vm.getNameFromValue = NameValue.getName;
        vm.model = Context.vModels.updateconfirm;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.model = contactUpdateconfirmInitialize(vm.model, Context, vm);

        /**
         * call update service
         **/
        vm.update = function() {
            $log.debug("CTR-EVNT:start - update");
            Context.vModels.updateconfirm = vm.model;
            //loading
            showLoading();
            contactUpdateconfirmUpdate.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call awagGoback service
         **/
        vm.awagGoback = function() {
            $log.debug("CTR-EVNT:start - awagGoback");
            Context.vModels.updateconfirm = vm.model;
            //loading
            showLoading();
            contactUpdateconfirmAwagGoback.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

"use strict";
var app = angular.module('awag.contact');

/**
 * controller for コンタクト詳細表示
 **/
app.controller('contactViewCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'contactViewAwagGoback', 
    
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , contactViewAwagGoback
    ) {
        var vm = this;
        $window.document.title = "コンタクト詳細表示";
        vm.getNameFromValue = NameValue.getName;
        vm.model = Context.vModels.view;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.model = contactViewInitialize(vm.model, Context, vm);

        /**
         * call awagGoback service
         **/
        vm.awagGoback = function() {
            $log.debug("CTR-EVNT:start - awagGoback");
            Context.vModels.view = vm.model;
            //loading
            showLoading();
            contactViewAwagGoback.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

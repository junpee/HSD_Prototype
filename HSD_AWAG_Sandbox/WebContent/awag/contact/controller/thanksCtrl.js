"use strict";
var app = angular.module('awag.contact');

/**
 * controller for コンタクト更新完了
 **/
app.controller('contactThanksCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'contactThanksGoback', 
    
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , contactThanksGoback
    ) {
        var vm = this;
        $window.document.title = "コンタクト更新完了";
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
        vm.model = contactThanksInitialize(vm.model, Context, vm);

        /**
         * call goback service
         **/
        vm.goback = function() {
            $log.debug("CTR-EVNT:start - goback");
            Context.vModels.thanks = vm.model;
            //loading
            showLoading();
            contactThanksGoback.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

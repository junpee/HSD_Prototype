"use strict";
var app = angular.module('awag.contact');

/**
 * controller for コンタクト削除確認
 **/
app.controller('contactDeleteconfirmCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'contactDeleteconfirmDelete', 'contactDeleteconfirmAwagGoback', 
    
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , contactDeleteconfirmDelete, contactDeleteconfirmAwagGoback
    ) {
        var vm = this;
        $window.document.title = "コンタクト削除確認";
        vm.getNameFromValue = NameValue.getName;
        vm.model = Context.vModels.deleteconfirm;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.model = contactDeleteconfirmInitialize(vm.model, Context, vm);

        /**
         * call delete service
         **/
        vm.delete = function() {
            $log.debug("CTR-EVNT:start - delete");
            Context.vModels.deleteconfirm = vm.model;
            //loading
            showLoading();
            contactDeleteconfirmDelete.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call awagGoback service
         **/
        vm.awagGoback = function() {
            $log.debug("CTR-EVNT:start - awagGoback");
            Context.vModels.deleteconfirm = vm.model;
            //loading
            showLoading();
            contactDeleteconfirmAwagGoback.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

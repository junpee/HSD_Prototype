"use strict";
var app = angular.module('awag.contact');

/**
 * controller for お客様コンタクト一覧
 **/
app.controller('contactManagecontactCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'contactManagecontactShowDetail', 'contactManagecontactUpdateContact', 'contactManagecontactDeleteContact', 'contactManagecontactAwagSelect', 'contactManagecontactAdd', 
    
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , contactManagecontactShowDetail, contactManagecontactUpdateContact, contactManagecontactDeleteContact, contactManagecontactAwagSelect, contactManagecontactAdd
    ) {
        var vm = this;
        $window.document.title = "お客様コンタクト一覧";
        vm.getNameFromValue = NameValue.getName;
        vm.model = Context.vModels.managecontact;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.pager = {
          start: 0,
          len: 10,
          pagerList: [],
          pagenum: 0
        };
        if(vm.model.contacts) {
            //setup pager
            for (var i = 0; i < Math.floor((vm.model.contacts.length - 1) / vm.pager.len) + 1; i++) {
                vm.pager.pagerList[i] = i;
            }
        }

        /**
         * pagenation
         * @param {page} page number (0-)
         **/
        vm.paginate = function(pager, page){
          pager.start = pager.len * page;
          pager.pagenum = page;
          $timeout(MKDT, 0, false);
        };

        vm.sortKeyContacts = null;
        /**
         * sort contacts
         * @param {key} sort key
         **/
        vm.sortContacts = function(key){
            if(vm.sortKeyContacts == null){
                vm.sortKeyContacts = key;
            } else if (!vm.sortKeyContacts.endsWith(key)){
                vm.sortKeyContacts = key;
            }
            if(vm.sortKeyContacts.startsWith("+")){
                vm.sortKeyContacts = "-" + vm.sortKeyContacts.substr(1);
            } else if(vm.sortKeyContacts.startsWith("-")) {
                vm.sortKeyContacts = "+" + vm.sortKeyContacts.substr(1);
            } else {
                vm.sortKeyContacts = "-" + key;
            }
            $timeout(MKDT, 0, false);
            vm.model.contacts =  $filter('orderBy')(vm.model.contacts, vm.sortKeyContacts);
        };

        vm.model = contactManagecontactInitialize(vm.model, Context, vm);

        /**
         * call showDetail service
         **/
        vm.showDetail = function() {
            $log.debug("CTR-EVNT:start - showDetail");
            Context.vModels.managecontact = vm.model;
            //loading
            showLoading();
            contactManagecontactShowDetail.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call updateContact service
         **/
        vm.updateContact = function() {
            $log.debug("CTR-EVNT:start - updateContact");
            Context.vModels.managecontact = vm.model;
            //loading
            showLoading();
            contactManagecontactUpdateContact.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call deleteContact service
         **/
        vm.deleteContact = function() {
            $log.debug("CTR-EVNT:start - deleteContact");
            Context.vModels.managecontact = vm.model;
            //loading
            showLoading();
            contactManagecontactDeleteContact.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call awagSelect service
         **/
        vm.awagSelect = function() {
            $log.debug("CTR-EVNT:start - awagSelect");
            Context.vModels.managecontact = vm.model;
            //loading
            showLoading();
            contactManagecontactAwagSelect.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call add service
         **/
        vm.add = function() {
            $log.debug("CTR-EVNT:start - add");
            Context.vModels.managecontact = vm.model;
            //loading
            showLoading();
            contactManagecontactAdd.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

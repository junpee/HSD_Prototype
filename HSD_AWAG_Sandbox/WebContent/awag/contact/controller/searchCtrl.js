"use strict";
var app = angular.module('awag.contact');

/**
 * controller for コンタクトお客様検索
 **/
app.controller('contactSearchCtrl', ['$scope', '$window', '$location', '$timeout', '$log', '$filter', 'Context', 'ErrorHandler', 'KEY_DELIMITER', 'NameValue', 'JsonUtils',

    'contactSearchAwagSearch', 'contactSearchGoContact', 'contactSearchAwagSelect', 
    'rankOpts', 'class1Opts', 'approveOpts', 
    function($scope, $window, $location, $timeout, $log, $filter, Context, ErrorHandler, KEY_DELIMITER, NameValue, JsonUtils
    , contactSearchAwagSearch, contactSearchGoContact, contactSearchAwagSelect
    , rankOpts, class1Opts, approveOpts) {
        var vm = this;
        $window.document.title = "コンタクトお客様検索";
        vm.getNameFromValue = NameValue.getName;
        vm.rankOpts = rankOpts;
        vm.class1Opts = class1Opts;
        vm.approveOpts = approveOpts;
        vm.model = Context.vModels.search;
        if(vm.model === undefined) {
            vm.model = Context.nextModel;
            delete Context.nextModel;
            if(vm.model === undefined) {
                vm.model = {};
            }
        }
        vm.error = Context.error;
        vm.model.search = {
          _inclusiveor: "false"
        };

        /**
         * reset input
         **/
        vm.awagReset = function(){
          vm.model.search = {
            _inclusiveor: "false"
          };
          $timeout(MKDT, 0, false);
          $timeout(RSCKD, 0, false);
        };

        vm.pager = {
          start: 0,
          len: 10,
          pagerList: [],
          pagenum: 0
        };
        if(vm.model.list) {
            //setup pager
            for (var i = 0; i < Math.floor((vm.model.list.length - 1) / vm.pager.len) + 1; i++) {
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

        vm.sortKeyList = null;
        /**
         * sort list
         * @param {key} sort key
         **/
        vm.sortList = function(key){
            if(vm.sortKeyList == null){
                vm.sortKeyList = key;
            } else if (!vm.sortKeyList.endsWith(key)){
                vm.sortKeyList = key;
            }
            if(vm.sortKeyList.startsWith("+")){
                vm.sortKeyList = "-" + vm.sortKeyList.substr(1);
            } else if(vm.sortKeyList.startsWith("-")) {
                vm.sortKeyList = "+" + vm.sortKeyList.substr(1);
            } else {
                vm.sortKeyList = "-" + key;
            }
            $timeout(MKDT, 0, false);
            vm.model.list =  $filter('orderBy')(vm.model.list, vm.sortKeyList);
        };

        vm.model = contactSearchInitialize(vm.model, Context, vm);

        /**
         * call awagSearch service
         **/
        vm.awagSearch = function() {
            $log.debug("CTR-EVNT:start - awagSearch");
            Context.vModels.search = vm.model;
            //loading
            showLoading();
            contactSearchAwagSearch.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call goContact service
         **/
        vm.goContact = function() {
            $log.debug("CTR-EVNT:start - goContact");
            Context.vModels.search = vm.model;
            //loading
            showLoading();
            contactSearchGoContact.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        /**
         * call awagSelect service
         **/
        vm.awagSelect = function() {
            $log.debug("CTR-EVNT:start - awagSelect");
            Context.vModels.search = vm.model;
            //loading
            showLoading();
            contactSearchAwagSelect.execute(vm);
            //clear errors in same screan
            vm.error = Context.error;
            Context.error = null;

        };

        //invoke after updating DOM
        $timeout(MKDT, 0, false);

    }
]);

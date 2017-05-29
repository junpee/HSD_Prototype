"use strict";
var app = angular.module('awag.approve');


/**
 * approve 未承認一覧 確認へ Service
 **/
app.service('approveGridinputAwagGridConfirm', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - approveGridinputAwagGridConfirm.execute()");

            //遷移イベント
            Context.nextPath='/approve/gridconfirm';
            //custom logic
            var tmp = {list: []};
            for (var i=0;i<vm.model.list.length;i++) {
                if (vm.model.list[i].selected) {
                    delete vm.model.list[i].selected;
                    tmp.list.push(vm.model.list[i]);
                }
            }
            Context.vModels.gridconfirm = approveGridinputAwagGridConfirmCustomLogic(tmp, Context, vm);
            //next page
            if(Context.nextPath != null){
                $location.path(Context.nextPath);
            }
            //loading
            hideLoading();
            $timeout(MKDT, 0, false);
        };
    }
]);


/**
 * approve 未承認一覧 初期処理 Service
 **/
app.service('approveGridinputAwagInit', ['$log', '$location', '$http', '$timeout', 'ErrorHandler', 'KEY_DELIMITER', 'Context', 'JsonUtils',
    function($log, $location, $http, $timeout, ErrorHandler, KEY_DELIMITER, Context, JsonUtils) {
        /**
         * execute method
         * @param {vm} Controller
         **/
        this.execute = function(vm) {
            $log.debug("SVC-EXEC:start - approveGridinputAwagInit.execute()");

            //pre API call logic
            var param = approveGridinputAwagInitPreCallLogic(vm.model, Context, vm);
            //clear empty params
            param = JsonUtils.clearEmptyParams(param);
            var success = function(httpResponse) {
                var value = httpResponse.data;
                if(value===""){
                    value = {};
                }
                //success
                $log.debug(value);

                //custom logic
                vm.model = approveGridinputAwagInitSuccessLogic(value, Context, vm);


                //loading
                hideLoading();
                $timeout(MKDT, 0, false);
            };
            var error = function(httpResponse) {
                vm.error = ErrorHandler.handleError(param, httpResponse);
                vm.error = approveGridinputAwagInitErrorLogic(vm.error, Context, vm, httpResponse);
            };
            $http.get(contextRoot + '/approvelist/' + JsonUtils.getQueryParamString(param)).then(success, error);
        };
    }
]);


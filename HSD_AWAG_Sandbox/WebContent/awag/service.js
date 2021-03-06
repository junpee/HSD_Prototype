"use strict";
/* Services */

var common = angular.module('awag.common', []);
// constants
common.value("KEY_DELIMITER", ".");

// parameter between pages (like HTTP sessions)
common.service('Context', function() {
    var param = {
        error: null,
        message: "メニューから処理を開始してください。",
        nextPath: null,
        vModels: null
    };
    return param;
});

//JSON Utilities
common.service('JsonUtils', function() {
    /**
     * returns url query string
     *
     * @param {input} json
     **/
    this.getQueryParamString = function(input) {
        var str = "?";
        angular.forEach(input, function(value, key) {
            str = str + "&" + encodeURIComponent(key) + "=" + encodeURIComponent(value);
        });
        return str;
    };

    /**
     * clears empty json params
     *
     * @param {input} json
     **/
    this.clearEmptyParams = function(input) {
        if (angular.isArray(input)) {
            return clearEmptyArray(input);
        } else {
            return clearEmptyValues(input);
        }
    };

    var clearEmptyArray = function(input) {
        var ar = [];
        angular.forEach(input, function(value, key) {
            ar.push(clearEmptyValues(value));
        });
        return ar;
    }

    var clearEmptyValues = function(input) {
        var out = {};
        angular.forEach(input, function(value, key) {
            if (angular.isArray(value)) {
                this[key] = clearEmptyArray(value);
            } else if (angular.isObject(value)) {
                this[key] = clearEmptyValues(value);
            } else if (angular.isString(value)) {
                if (value.length > 0) {
                    this[key] = value;
                }

            } else if (angular.isDate(value)) {
                this[key] = value.getFullYear() + '-' + ('0' + (value.getMonth() + 1)).slice(-2) + '-' + ('0' + value.getDate()).slice(-2);

            } else if (angular.isNumber(value)) {
                this[key] = value;

            }
        }, out);
        return out;
    }
});

common.service('ErrorHandler', ['$log', function($log) {
    /**
     * Parse and, log detail error mesages from HTTPresponse
     *  and show message in material design snackbar
     *
     * @param {item} parameter for HTTPRequest
     * @param {response} HTTPresponse
     * @param {msg} optional message to override
     **/
    this.handleError = function(item, response, msg) {
        //loading
        hideLoading();

        var error = {
            code: null,
            message: null,
            details: []
        };

        //failure
        if (msg == null || msg.length == 0) {
            msg = 'エラーが発生しました。';
        }
        $log.error(item);
        if (response.status) {
            msg += " (status: " + response.status + ") ";
        }
        $log.error(response.status);
        if (response.data) {
            if (response.data.code) {
                msg += " [code] " + response.data.code;
                msg += " [message] " + response.data.message;

                $log.error(" [code] " + response.data.code);
                $log.error(" [message] " + response.data.message);

                error.code = response.data.code;
                error.message = response.data.message;

                error.details.push({
                    code: error.code,
                    message: error.message
                });
                if (response.data.errors) {
                    for (var i = 0; i < response.data.errors.length; i++) {
                        // msg += " code["+i+"] "+ response.data.errors[i].code;
                        // msg += " message["+i+"] "+ response.data.errors[i].message;

                        $log.error(" code[" + i + "] " + response.data.errors[i].code);
                        $log.error(" message[" + i + "] " + response.data.errors[i].message);

                        error.details.push({
                            code: response.data.errors[i].code,
                            message: response.data.errors[i].message
                        });

                    } //for
                }
            } else {
                msg += response.data;
            }
        } //if data

        if (error.details.length === 0) {
            if(response.status) {
                error.code = response.status;
            } else {
                error.code = "";
            }
            error.message = msg;
            error.details.push({
                code: error.code,
                message: msg
            });
        }

        //show material design snackbar
//        var notification = document.querySelector('.mdl-js-snackbar');
//        notification.MaterialSnackbar.showSnackbar({
//            message: msg,
//            timeout: 10000
//        });

        return error;
    };
}]);

common.service('NameValue', [function() {
    /**
     * returns name from value
     *
     * @param {nameValue} name value list service
     * @param {value} value
     **/
    this.getName = function(nameValue, value) {
        for (var i = 0; i < nameValue.length; i++) {
            if (nameValue[i].value == value) {
                return nameValue[i].name;
            }
        }
        return value;
    };
}]);

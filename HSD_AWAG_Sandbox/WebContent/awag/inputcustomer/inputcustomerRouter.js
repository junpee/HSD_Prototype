"use strict";
var app = angular.module('awag.inputcustomer', ['ngRoute', 'awag.common'])
    /**
     * routing for sample CRUD
     **/
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/inputcustomer', {
                controller: 'inputcustomerInputCtrl as vm',
                templateUrl: 'awag/inputcustomer/template/input.html'
            })
            .when('/inputcustomer/input', {
                controller: 'inputcustomerInputCtrl as vm',
                templateUrl: 'awag/inputcustomer/template/input.html'
            })
            .when('/inputcustomer/confirm', {
                controller: 'inputcustomerConfirmCtrl as vm',
                templateUrl: 'awag/inputcustomer/template/confirm.html'
            })
            .when('/inputcustomer/thanks', {
                controller: 'inputcustomerThanksCtrl as vm',
                templateUrl: 'awag/inputcustomer/template/thanks.html'
            });
    }]);

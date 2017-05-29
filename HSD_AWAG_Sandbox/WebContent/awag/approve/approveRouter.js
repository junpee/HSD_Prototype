"use strict";
var app = angular.module('awag.approve', ['ngRoute', 'awag.common'])
    /**
     * routing for sample CRUD
     **/
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/approve', {
                controller: 'approveGridinputCtrl as vm',
                templateUrl: 'awag/approve/template/gridinput.html'
            })
            .when('/approve/gridinput', {
                controller: 'approveGridinputCtrl as vm',
                templateUrl: 'awag/approve/template/gridinput.html'
            })
            .when('/approve/gridconfirm', {
                controller: 'approveGridconfirmCtrl as vm',
                templateUrl: 'awag/approve/template/gridconfirm.html'
            })
            .when('/approve/thanks', {
                controller: 'approveThanksCtrl as vm',
                templateUrl: 'awag/approve/template/thanks.html'
            });
    }]);

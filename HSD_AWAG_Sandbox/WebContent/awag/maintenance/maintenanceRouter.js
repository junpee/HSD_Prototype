"use strict";
var app = angular.module('awag.maintenance', ['ngRoute', 'awag.common'])
    /**
     * routing for sample CRUD
     **/
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/maintenance', {
                controller: 'maintenanceSearchCtrl as vm',
                templateUrl: 'awag/maintenance/template/search.html'
            })
            .when('/maintenance/search', {
                controller: 'maintenanceSearchCtrl as vm',
                templateUrl: 'awag/maintenance/template/search.html'
            })
            .when('/maintenance/view', {
                controller: 'maintenanceViewCtrl as vm',
                templateUrl: 'awag/maintenance/template/view.html'
            })
            .when('/maintenance/update', {
                controller: 'maintenanceUpdateCtrl as vm',
                templateUrl: 'awag/maintenance/template/update.html'
            })
            .when('/maintenance/confirm', {
                controller: 'maintenanceConfirmCtrl as vm',
                templateUrl: 'awag/maintenance/template/confirm.html'
            })
            .when('/maintenance/thanks', {
                controller: 'maintenanceThanksCtrl as vm',
                templateUrl: 'awag/maintenance/template/thanks.html'
            });
    }]);

"use strict";
var app = angular.module('awag.contact', ['ngRoute', 'awag.common'])
    /**
     * routing for sample CRUD
     **/
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/contact', {
                controller: 'contactSearchCtrl as vm',
                templateUrl: 'awag/contact/template/search.html'
            })
            .when('/contact/search', {
                controller: 'contactSearchCtrl as vm',
                templateUrl: 'awag/contact/template/search.html'
            })
            .when('/contact/managecontact', {
                controller: 'contactManagecontactCtrl as vm',
                templateUrl: 'awag/contact/template/managecontact.html'
            })
            .when('/contact/add', {
                controller: 'contactAddCtrl as vm',
                templateUrl: 'awag/contact/template/add.html'
            })
            .when('/contact/addconfirm', {
                controller: 'contactAddconfirmCtrl as vm',
                templateUrl: 'awag/contact/template/addconfirm.html'
            })
            .when('/contact/update', {
                controller: 'contactUpdateCtrl as vm',
                templateUrl: 'awag/contact/template/update.html'
            })
            .when('/contact/updateconfirm', {
                controller: 'contactUpdateconfirmCtrl as vm',
                templateUrl: 'awag/contact/template/updateconfirm.html'
            })
            .when('/contact/deleteconfirm', {
                controller: 'contactDeleteconfirmCtrl as vm',
                templateUrl: 'awag/contact/template/deleteconfirm.html'
            })
            .when('/contact/view', {
                controller: 'contactViewCtrl as vm',
                templateUrl: 'awag/contact/template/view.html'
            })
            .when('/contact/thanks', {
                controller: 'contactThanksCtrl as vm',
                templateUrl: 'awag/contact/template/thanks.html'
            });
    }]);

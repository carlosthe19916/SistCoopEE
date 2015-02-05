define(['./app'], function(app) {
    'use strict';

    app.config(function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $urlRouterProvider.otherwise('/app/home');

        $stateProvider.
            state('app', {
                abstract: true,
                url: '/app',
                templateUrl: appHelper.templatePath('layout/body')
            })
            .state('app.home', {
                url: '/home',
                templateUrl: appHelper.templatePath('dashboards/home')
            });

    });

    return app;

});





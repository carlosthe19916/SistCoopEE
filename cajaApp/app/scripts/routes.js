define(['./app'], function(app) {
    'use strict';
    return app.config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                url: '/home',
                templateUrl: 'views/themplate/themplate01.html'
            })
            .state('app', {
                url: '/app',
                templateUrl: 'views/themplate/themplate02.html'
            });
    });

});

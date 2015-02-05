/*jshint unused: vars */
define([
        'angular',
        './xenon/main',
        './common/main',
        './organizacion/main',
        './persona/main',
        './ubigeo/main'
    ]/*deps*/,
    function (angular){

        'use strict';

        var app = angular.module('sistcoop-app',
            [
                /*xenon*/
                'xenon',

                /*angular*/
                'ngCookies',
                //'ngAria',
                'ngSanitize',
                'ngMessages',
                'ngAnimate',

                /*ui modules*/
                'ui.router',
                'ui.bootstrap',
                'ui.select',
                'ui.utils',
                'ui.grid',
                'ui.grid.edit',
                'ui.grid.selection',
                'angular-ladda',
                'restangular',
                'blockUI',
                'oc.lazyLoad',

                /*sistcoop*/
                'persona',
                'ubigeo',
                'organizacion',
                'common'
            ]);

        return app;
    }
);





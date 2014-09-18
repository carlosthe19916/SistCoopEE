/*jshint unused: vars */
define([
        'angular',
        './controllers/main',
        './directives/main',
        './factories/main',
        './filters/main',
        './services/main'
    ]/*deps*/,
    function (angular)/*invoke*/ {
        'use strict';

        return angular.module('cajaApp', [
            'cajaApp.controllers',
            'cajaApp.directives',
            'cajaApp.factories',
            'cajaApp.filters',
            'cajaApp.services',

            /*angJSDeps*/
            'ui.router',
            'ngAnimate',
            'ngCookies',
            'ngSanitize',
            'ngMessages',
            'ui.bootstrap',
            'angular-ladda',

            'ubigeoSDK',
            'personaSDK'
        ]);
    }
);

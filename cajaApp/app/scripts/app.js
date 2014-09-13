/*jshint unused: vars */
define([
        'angular',
        './controllers/main',
        './directives/main',
        './filters/main',
        './services/main'
    ]/*deps*/,
    function (angular)/*invoke*/ {
        'use strict';

        return angular.module('cajaApp', [
            'cajaApp.controllers',
            'cajaApp.directives',
            'cajaApp.filters',
            'cajaApp.services',

            /*angJSDeps*/
            'ui.router',
            'ngAnimate',
            'ngCookies',
            'ngSanitize',
            'ngMessages',
            'ui.bootstrap',

            'ubigeoSDK',
            'personaSDK'
        ]);
    }
);

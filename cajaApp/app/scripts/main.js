/*jshint unused: vars */
require.config({
    paths: {
        jquery: '../bower_components/jquery/dist/jquery',
        angular: '../bower_components/angular/angular',
        'angular-animate': '../bower_components/angular-animate/angular-animate',
        'angular-cookies': '../bower_components/angular-cookies/angular-cookies',
        'angular-sanitize': '../bower_components/angular-sanitize/angular-sanitize',
        'angular-scenario': '../bower_components/angular-scenario/angular-scenario',
        'angular-mocks': '../bower_components/angular-mocks/angular-mocks',
        'angular-messages': '../bower_components/angular-messages/angular-messages',
        'ui-router': '../bower_components/angular-ui-router/release/angular-ui-router',
        'angular-restmod': '../bower_components/angular-restmod/dist/angular-restmod-bundle',
        'angular-restmod-style': '../bower_components/angular-restmod/dist/styles/ams',
        'ui.bootstrap': '../bower_components/angular-bootstrap/ui-bootstrap-tpls',
        'angular-ladda': '../bower_components/angular-ladda/dist/angular-ladda.min',

        'ubigeoSDK': '../js/ubigeoSDK',
        'personaSDK': '../js/personaSDK'
    },
    shim: {
        jquery: {
            'exports' : 'jquery'
        },
        angular: {
            exports: 'angular'
        },
        'angular-animate':{
            deps: ['angular']
        },
        'angular-cookies':{
            deps: ['angular']
        },
        'angular-sanitize':{
            deps: ['angular']
        },
        'angular-scenario':{
            deps: ['angular']
        },
        'angular-messages':{
            deps: ['angular']
        },
        'ui-router':{
            deps: ['angular']
        },
        'angular-restmod': {
            deps: ['angular']
        },
        'angular-restmod-style': {
            deps: [
                'angular',
                'angular-restmod'
            ]
        },
        'ui.bootstrap':{
            deps: ['angular']
        },
        'angular-ladda':{
            deps: [
                'angular'
            ]
        },

        'ubigeoSDK':{
            deps: [
                'angular',
                'angular-restmod'
            ]
        },
        'personaSDK':{
            deps: [
                'angular',
                'angular-restmod'
            ]
        },
        'angular-mocks': {
            deps: [
                'angular'
            ],
            exports: 'angular.mock'
        }
    },
    priority: [
        'angular'
    ]
});

//http://code.angularjs.org/1.2.1/docs/guide/bootstrap#overview_deferred-bootstrap
window.name = 'NG_DEFER_BOOTSTRAP!';

require([
    'angular',
    'app',
    'jquery',
    'routes',
    'angular-animate',
    'angular-cookies',
    'angular-sanitize',
    'angular-scenario',
    'angular-messages',
    'ui-router',
    'angular-restmod',
    'angular-restmod-style',
    'ui.bootstrap',
    'angular-ladda',
    'angular-mocks',

    'ubigeoSDK',
    'personaSDK'

], function(angular, app) {
    'use strict';
    /* jshint ignore:start */
    var $html = angular.element(document.getElementsByTagName('html')[0]);
    /* jshint ignore:end */
    angular.element().ready(function() {
        angular.resumeBootstrap([app.name]);
    });
});


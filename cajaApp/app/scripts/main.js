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
        'ui.bootstrap': '../bower_components/angular-bootstrap/ui-bootstrap-tpls'
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
        'ui.bootstrap':{
            deps: ['angular']
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
    'ui.bootstrap',
    'angular-mocks'
], function(angular, app) {
    'use strict';
    /* jshint ignore:start */
    var $html = angular.element(document.getElementsByTagName('html')[0]);
    /* jshint ignore:end */
    angular.element().ready(function() {
        angular.resumeBootstrap([app.name]);
    });
});


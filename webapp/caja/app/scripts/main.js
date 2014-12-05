require.config({
    paths: {
        'domReady': '../bower_components/requirejs-domready/domReady',

        'angular': '../bower_components/angular/angular',
        'angular-sanitize': '../bower_components/angular-sanitize/angular-sanitize',
        'angular-cookies': '../bower_components/angular-cookies/angular-cookies',
        'angular-messages': '../bower_components/angular-messages/angular-messages',
        'angular-animate': '../bower_components/angular-animate/angular-animate',
        'angular-ui-router': '../bower_components/angular-ui-router/release/angular-ui-router',
        'angular-bootstrap': '../bower_components/angular-bootstrap/ui-bootstrap-tpls',
        'angular-ui-select': '../bower_components/angular-ui-select/dist/select',
        'angular-ui-utils': '../bower_components/angular-ui-utils/ui-utils',
        'angular-ui-grid': '../bower_components/angular-ui-grid/ui-grid',
        'angular-block-ui': '../bower_components/angular-block-ui/dist/angular-block-ui',
        'angular-ladda': '../bower_components/angular-ladda/dist/angular-ladda.min',
        'restangular': '../bower_components/restangular/dist/restangular',
        'underscore': '../bower_components/underscore/underscore',
        'ocLazyLoad': '../bower_components/ocLazyLoad/dist/ocLazyLoad',

        'jquery': '../bower_components/jquery/dist/jquery',
        'TweenMax': '../bower_components/gsap/src/minified/TweenMax.min',
        'perfect-scrollbar': '../bower_components/perfect-scrollbar/min/perfect-scrollbar.min',
        'joinable': '../xenon/assets/js/joinable',
        'resizeable': '../xenon/assets/js/resizeable',

        'xenon-custom': '../xenon/assets/js/xenon-custom'
    },
    shim: {
        angular: {
            exports: 'angular'
        },
        'angular-sanitize':{
            deps: ['angular']
        },
        'angular-cookies':{
            deps: ['angular']
        },
        'angular-messages':{
            deps: ['angular']
        },
        'angular-animate':{
            deps: ['angular']
        },
        'angular-ui-router' :{
            deps: ['angular']
        },
        'angular-bootstrap' :{
            deps: ['angular']
        },
        'angular-ui-select' :{
            deps: ['angular']
        },
        'angular-ui-utils' :{
            deps: ['angular']
        },
        'angular-ui-grid' :{
            deps: ['angular']
        },
        'angular-block-ui' :{
            deps: ['angular']
        },
        'angular-ladda' :{
            deps: ['angular']
        },
        'restangular' :{
            deps: ["underscore", "angular"]
        },
        underscore: {
            exports: '_'
        },
        ocLazyLoad: {
            deps: ['angular']
        },
        'angular-mocks': {
            deps: [
                'angular'
            ],
            exports: 'angular.mock'
        },

        jquery: {
            exports: 'jquery'
        },
        'TweenMax':{
            deps: ['jquery']
        },
        'perfect-scrollbar':{
            deps: ['jquery']
        },
        'joinable':{
            deps: ['jquery']
        },
        'resizeable':{
            deps: ['jquery']
        },
        'xenon-custom':{
            deps: ['jquery']
        }
    },
    deps: ['./boot'],
    priority: [
        'jquery',
        'angular'
    ]
});

require([
    //'jquery',
    'angular',
    'app',
    'controllers',
    'directives',
    'services',
    'xenon-controllers',
    'xenon-directives',
    'xenon-factories',
    'xenon-services',
    'angular-sanitize',
    'angular-cookies',
    'angular-messages',
    'angular-animate',
    'angular-ui-router',
    'angular-bootstrap',
    'angular-ui-select',
    'angular-ui-utils',
    'angular-ui-grid',
    'angular-block-ui',
    'angular-ladda',
    'restangular',
    'underscore',
    'ocLazyLoad'

    /*'TweenMax',
    'perfect-scrollbar',
    'joinable',
    'resizeable',
    'xenon-custom'*/

], function(jquery, angular, app) {

});


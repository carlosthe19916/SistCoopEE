require.config({
    paths: {
/*

    <script src="scripts/js/resizeable.js" id="script-resource-9"></script>
    <!-- App -->
    <script src="scripts/js/app.js" id="script-resource-10"></script>
    <script src="scripts/js/controllers.js" id="script-resource-11"></script>
    <script src="scripts/js/directives.js" id="script-resource-12"></script>
    <script src="scripts/js/factory.js" id="script-resource-13"></script>
    <script src="scripts/js/services.js" id="script-resource-14"></script>
    <!-- JavaScripts initializations and stuff -->
    <script src="scripts/js/xenon-custom.js" id="script-resource-15"></script>

    */
        jquery: '../../bower_components/jquery/dist/jquery',
        angular: '../../bower_components/angular/angular',
        keycloak: '../../bower_components/keycloak/dist/keycloak',
        'angular-sanitize': '../../bower_components/angular-sanitize/angular-sanitize',
        'angular-cookies': '../../bower_components/angular-cookies/angular-cookies',
        'angular-messages': '../../bower_components/angular-messages/angular-messages',
        'angular-animate': '../../bower_components/angular-animate/angular-animate',
        'angular-aria': '../../bower_components/angular-aria/angular-aria',
        'angular-mocks': '../../bower_components/angular-mocks/angular-mocks',
        'angular-scenario': '../../bower_components/angular-scenario/angular-scenario',
        'angular-touch': '../../bower_components/angular-touch/angular-touch',
        'angular-ui-router': '../../bower_components/angular-ui-router/release/angular-ui-router',
        'angular-bootstrap': '../../bower_components/angular-bootstrap/ui-bootstrap-tpls',
        'angular-ui-select': '../../bower_components/angular-ui-select/dist/select',
        'angular-ui-utils': '../../bower_components/angular-ui-utils/ui-utils',
        'angular-ui-grid': '../../bower_components/angular-ui-grid/ui-grid',
        'angular-block-ui': '../../bower_components/angular-block-ui/dist/angular-block-ui',
        'angular-ladda': '../../bower_components/angular-ladda/dist/angular-ladda.min',
        oclazyload: '../../bower_components/oclazyload/dist/ocLazyLoad.min',
        FBAngular: '../../bower_components/angular-fullscreen/src/angular-fullscreen',
        restangular: '../../bower_components/restangular/dist/restangular',
        underscore: '../../bower_components/underscore/underscore',
        TweenLite: '../../bower_components/gsap/src/uncompressed/TweenLite',
        TweenMax: '../../bower_components/gsap/src/uncompressed/TweenMax',
        TimelineLite: '../../bower_components/gsap/src/uncompressed/TimelineLite',
        TimelineMax: '../../bower_components/gsap/src/uncompressed/TimelineMax',
        EasePack: '../../bower_components/gsap/src/uncompressed/easing/EasePack',
        CSSPlugin: '../../bower_components/gsap/src/uncompressed/plugins/CSSPlugin',
        BezierPlugin: '../../bower_components/gsap/src/uncompressed/plugins/BezierPlugin',
        AttrPlugin: '../../bower_components/gsap/src/uncompressed/plugins/AttrPlugin',
        DirectionalRotationPlugin: '../../bower_components/gsap/src/uncompressed/plugins/DirectionalRotationPlugin',
        'jquery-autosize': '../../bower_components/jquery-autosize/jquery.autosize',
        'scrollMonitor': '../../bower_components/scrollMonitor/scrollMonitor',
        'perfect-scrollbar': '../../bower_components/perfect-scrollbar/src/perfect-scrollbar',
        'jquery-hoverIntent': '../../bower_components/jquery-hoverIntent/jquery.hoverIntent',
        'cookies-js': '../../bower_components/cookies-js/src/cookies',

        'requirejs-domready': '../../bower_components/requirejs-domready/domReady',
        'angular-fullscreen': '../../bower_components/angular-fullscreen/src/angular-fullscreen'
    },
    shim: {
        jquery: {
            exports: 'jquery'
        },
        angular: {
            exports: 'angular'
        },
        keycloak: {
            exports: 'keycloak'
        },
        'angular-sanitize': {
            deps: [
                'angular'
            ]
        },
        'angular-cookies': {
            deps: [
                'angular'
            ]
        },
        'angular-messages': {
            deps: [
                'angular'
            ]
        },
        'angular-animate': {
            deps: [
                'angular'
            ]
        },
        'angular-aria': {
            deps: [
                'angular'
            ]
        },
        'angular-scenario': {
            deps: [
                'angular'
            ]
        },
        'angular-touch': {
            deps: [
                'angular'
            ]
        },
        'angular-ui-router': {
            deps: [
                'angular'
            ]
        },
        'angular-bootstrap': {
            deps: [
                'angular'
            ]
        },
        'angular-ui-select': {
            deps: [
                'angular'
            ]
        },
        'angular-ui-utils': {
            deps: [
                'angular'
            ]
        },
        'angular-ui-grid': {
            deps: [
                'angular'
            ]
        },
        'angular-block-ui': {
            deps: [
                'angular'
            ]
        },
        'angular-ladda': {
            deps: [
                'angular'
            ]
        },
        oclazyload: {
            deps: [
                'angular'
            ]
        },
        FBAngular: {
            deps: [
                'angular'
            ]
        },
        restangular: {
            deps: [
                'underscore',
                'angular'
            ]
        },
        underscore: {
            exports: '_'
        },
        TweenLite: {
            deps: [
                'jquery'
            ]
        },
        TweenMax: {
            deps: [
                'jquery'
            ]
        },
        TimelineLite: {
            deps: [
                'jquery'
            ]
        },
        TimelineMax: {
            deps: [
                'jquery'
            ]
        },
        EasePack: {
            deps: [
                'jquery'
            ]
        },
        CSSPlugin: {
            deps: [
                'jquery'
            ]
        },
        BezierPlugin: {
            deps: [
                'jquery'
            ]
        },
        AttrPlugin: {
            deps: [
                'jquery'
            ]
        },
        DirectionalRotationPlugin: {
            deps: [
                'jquery'
            ]
        },
        'jquery-autosize': {
            deps: [
                'jquery'
            ]
        },
        'scrollMonitor': {
            deps: [
                'jquery'
            ]
        },
        'perfect-scrollbar': {
            deps: [
                'jquery'
            ]
        },
        'jquery-hoverIntent': {
            deps: [
                'jquery'
            ]
        },
        'cookies-js': {
            deps: [
                'jquery'
            ]
        },
        'angular-mocks': {
            deps: [
                'angular'
            ],
            exports: 'angular.mock'
        }
    },
    deps: [
        './boot'
    ],
    priority: [
        'jquery',
        'angular'
    ],
    packages: [

    ]
});

require([
    'jquery',
    'angular',
    'keycloak',
    'app',

    'angular-sanitize',
    'angular-cookies',
    'angular-messages',
    'angular-animate',
    'angular-aria',

    'angular-ui-router',
    'angular-bootstrap',
    'angular-ui-select',
    'angular-ui-utils',
    'angular-ui-grid',
    'angular-block-ui',
    'angular-ladda',
    'oclazyload',
    'FBAngular',
    'restangular',
    'underscore',

    'perfect-scrollbar',
    'jquery-hoverIntent',
    'jquery-autosize',
    'TweenLite',
    'TweenMax',
    'TimelineLite',
    'TimelineMax',
    'EasePack',
    'CSSPlugin',
    'BezierPlugin',
    'AttrPlugin',
    'DirectionalRotationPlugin'

], function() {

});


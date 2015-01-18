var tests = [];
for (var file in window.__karma__.files) {
  if (window.__karma__.files.hasOwnProperty(file)) {
    // Removed "Spec" naming from files
    if (/Spec\.js$/.test(file)) {
      tests.push(file);
    }
  }
}

requirejs.config({
    // Karma serves files from '/base'
    baseUrl: '/base/app/scripts',

    paths: {
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
        'perfect-scrollbar': '../../bower_components/perfect-scrollbar/src/perfect-scrollbar',
        'jquery-hoverIntent': '../../bower_components/jquery-hoverIntent/jquery.hoverIntent',
        'jquery-autosize': '../../bower_components/jquery-autosize/jquery.autosize',
        TweenLite: '../../bower_components/gsap/src/uncompressed/TweenLite',
        TweenMax: '../../bower_components/gsap/src/uncompressed/TweenMax',
        TimelineLite: '../../bower_components/gsap/src/uncompressed/TimelineLite',
        TimelineMax: '../../bower_components/gsap/src/uncompressed/TimelineMax',
        EasePack: '../../bower_components/gsap/src/uncompressed/easing/EasePack',
        CSSPlugin: '../../bower_components/gsap/src/uncompressed/plugins/CSSPlugin',
        BezierPlugin: '../../bower_components/gsap/src/uncompressed/plugins/BezierPlugin',
        AttrPlugin: '../../bower_components/gsap/src/uncompressed/plugins/AttrPlugin',
        DirectionalRotationPlugin: '../../bower_components/gsap/src/uncompressed/plugins/DirectionalRotationPlugin',
        'requirejs-domready': '../../bower_components/requirejs-domready/domReady',
        'angular-fullscreen': '../../bower_components/angular-fullscreen/src/angular-fullscreen',
        gsap: '../../bower_components/gsap/src/uncompressed/TweenMax'
    },

    shim: {
        'angular' : {'exports' : 'angular'},
        'angular-cookies': ['angular'],
        'angular-sanitize': ['angular'],
        'angular-animate': ['angular'],
        'angular-touch': ['angular'],
        'angular-mocks': {
          deps:['angular'],
          'exports':'angular.mock'
        }
    },

    // ask Require.js to load these files (all our tests)
    deps: tests,

    // start test run, once Require.js is done
    callback: window.__karma__.start
});

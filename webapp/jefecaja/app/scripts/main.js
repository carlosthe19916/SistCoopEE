/*jshint unused: vars */
require.config({
    paths: {

    },
    shim: {

    },
    priority: [

    ]
});

//http://code.angularjs.org/1.2.1/docs/guide/bootstrap#overview_deferred-bootstrap
window.name = 'NG_DEFER_BOOTSTRAP!';

require([
    'appRequire'
], function() {
    console.log("entroo");
    angular.bootstrap();
});


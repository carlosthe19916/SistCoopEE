/*jshint unused: vars */
define(['angular', 'controllers/main', 'controllers/about']/*deps*/, function (angular, MainCtrl, AboutCtrl)/*invoke*/ {
  'use strict';

  /**
   * @ngdoc overview
   * @name xenonApp
   * @description
   * # xenonApp
   *
   * Main module of the application.
   */
  return angular
    .module('xenonApp', ['xenonApp.controllers.MainCtrl',
'xenonApp.controllers.AboutCtrl',
/*angJSDeps*/
    'ngCookies',
    'ngAria',
    'ngMessages',
    'ngResource',
    'ngSanitize',
    'ngRoute',
    'ngAnimate',
    'ngTouch'
  ])
    .config(function ($routeProvider) {
      $routeProvider
        .when('/', {
          templateUrl: 'views/main.html',
          controller: 'MainCtrl'
        })
        .when('/about', {
          templateUrl: 'views/about.html',
          controller: 'AboutCtrl'
        })
        .otherwise({
          redirectTo: '/'
        });
    });
});

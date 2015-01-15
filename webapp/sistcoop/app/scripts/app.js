/*jshint unused: vars */
define(['angular', 'controllers/main']/*deps*/, function (angular, MainCtrl)/*invoke*/ {
  'use strict';

  /**
   * @ngdoc overview
   * @name sistcoopApp
   * @description
   * # sistcoopApp
   *
   * Main module of the application.
   */
  return angular
    .module('sistcoopApp', ['sistcoopApp.controllers.MainCtrl',
/*angJSDeps*/
    'ngCookies',
    'ngAria',
    'ngMessages',
    'ngSanitize'
  ]);
});

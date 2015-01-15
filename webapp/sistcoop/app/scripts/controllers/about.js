define(['angular'], function (angular) {
  'use strict';

  /**
   * @ngdoc function
   * @name xenonApp.controller:AboutCtrl
   * @description
   * # AboutCtrl
   * Controller of the xenonApp
   */
  angular.module('xenonApp.controllers.AboutCtrl', [])
    .controller('AboutCtrl', function ($scope) {
      $scope.awesomeThings = [
        'HTML5 Boilerplate',
        'AngularJS',
        'Karma'
      ];
    });
});

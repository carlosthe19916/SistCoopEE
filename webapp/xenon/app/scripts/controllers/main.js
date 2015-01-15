define(['angular'], function (angular) {
  'use strict';

  /**
   * @ngdoc function
   * @name xenonApp.controller:MainCtrl
   * @description
   * # MainCtrl
   * Controller of the xenonApp
   */
  angular.module('xenonApp.controllers.MainCtrl', [])
    .controller('MainCtrl', function ($scope) {
      $scope.awesomeThings = [
        'HTML5 Boilerplate',
        'AngularJS',
        'Karma'
      ];
    });
});

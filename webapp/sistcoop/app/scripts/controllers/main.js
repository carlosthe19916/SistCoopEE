define(['angular'], function (angular) {
  'use strict';

  /**
   * @ngdoc function
   * @name sistcoopApp.controller:MainCtrl
   * @description
   * # MainCtrl
   * Controller of the sistcoopApp
   */
  angular.module('sistcoopApp.controllers.MainCtrl', [])
    .controller('MainCtrl', function ($scope) {
      $scope.awesomeThings = [
        'HTML5 Boilerplate',
        'AngularJS',
        'Karma'
      ];
    });
});

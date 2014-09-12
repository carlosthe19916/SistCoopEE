define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('MainController', function($scope, $timeout){

        $scope.successAlerts = [];
        $scope.infoAlerts = [];
        $scope.warningAlerts = [];
        $scope.dangerAlerts = [];

        //add message
        $scope.addSuccessMessage = function(msg){
            var index = $scope.successAlerts.push({ type: 'success', msg: msg });
            $timeout(function(){
                $scope.closeSuccessAlert(index - 1);
            }, 4000);
        };
        $scope.addInfoMessage = function(msg){
            var index = $scope.infoAlerts.push({ type: 'info', msg: msg });
            $timeout(function(){
                $scope.closeInfoAlert(index - 1);
            }, 4000);
        };
        $scope.addWarningMessage = function(msg){
            var index = $scope.warningAlerts.push({ type: 'warning', msg: msg });
            $timeout(function(){
                $scope.closeWarningAlert(index - 1);
            }, 4000);
        };
        $scope.addDangerMessage = function(msg){
            var index = $scope.dangerAlerts.push({ type: 'danger', msg: msg });
            $timeout(function(){
                $scope.closeDangerAlert(index - 1);
            }, 4000);
        };

        //close
        $scope.closeSuccessAlert = function(index) {
            $scope.successAlerts.splice(index, 1);
        };
        $scope.closeInfoAlert = function(index) {
            $scope.infoAlerts.splice(index, 1);
        };
        $scope.closeWarningAlert = function(index) {
            $scope.warningAlerts.splice(index, 1);
        };
        $scope.closeDangerAlert = function(index) {
            $scope.dangerAlerts.splice(index, 1);
        };

        $scope.addSuccessMessage("dfdfd");

    });
});
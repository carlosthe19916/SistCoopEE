define(['../module'], function (module) {
    'use strict';

    module.controller('EditarSucursalCtrl', function($scope, $state, Notifications){

        $scope.view = {
            sucursal: undefined,
            sucursalDB: undefined
        };

        $scope.loadParams = function(){
            $scope.view.sucursalDB = $scope.params.object;
            $scope.view.sucursal = angular.copy($scope.view.sucursalDB);
        };
        $scope.loadParams();

        $scope.submit = function(){
            if ($scope.form.$valid) {
                $scope.blockControl();
                $scope.view.sucursal.$save().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Sucursal actualizada");
                        $scope.view.sucursalDB = angular.copy($scope.view.sucursal);
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            }
        };

    });
});
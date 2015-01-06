define(['../../../module'], function (module) {
    'use strict';

    module.controller('CajaAbrirCtrl', function($scope, $state, Sucursal, Agencia, Currency, activeProfile, Notifications){

        $scope.loadParams = function(){
            $scope.view.boveda = $scope.params.object;
            $scope.view.boveda.detalle = $scope.view.boveda.$getDetalle().$object;
        };
        $scope.loadParams();

        $scope.abrir = function(){
            if ($scope.form.$valid) {
                $scope.blockControl();
                $scope.view.boveda.$abrir().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success('Boveda abierta');
                        $state.go('^.resumen');
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

define(['../../../module'], function (module) {
    'use strict';

    module.controller('BovedaCerrarCtrl', function($scope, $state, Sucursal, Agencia, Currency, activeProfile, Notifications){

        $scope.loadParams = function(){
            $scope.view.boveda = $scope.params.object;
            $scope.view.boveda.$getDetalle().then(function(response){
                $scope.view.boveda.detalle = response;
                angular.forEach($scope.view.boveda.detalle, function(row){
                    row.getSubTotal = function(){
                        return this.valor * this.cantidad;
                    };
                });
            });
        };
        $scope.loadParams();

        $scope.getTotal = function(){
            var total = 0;
            for(var i = 0; i < $scope.view.boveda.detalle.length; i++) {
                total = total + $scope.view.boveda.detalle[i].getSubTotal();
            }
            return total;
        };

        $scope.abrir = function(){
            if ($scope.form.$valid) {
                $scope.blockControl();
                $scope.view.boveda.$cerrar().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success('Boveda cerrada');
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

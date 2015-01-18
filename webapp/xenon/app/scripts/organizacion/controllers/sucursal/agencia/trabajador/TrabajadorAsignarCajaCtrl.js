define(['../../../module'], function (module) {
    'use strict';

    module.controller('TrabajadorAsignarCajaCtrl', function($scope, $state, Agencia, Notifications, Dialog){

        $scope.combo = {
            caja: undefined
        };
        $scope.combo.selected = {
            caja: undefined
        };

        $scope.loadCombo = function(){
            $scope.combo.caja = Agencia.$new($scope.view.trabajador.agencia.id).$getCajas().$object;
        };
        $scope.loadCombo();

        $scope.removeCaja = function(){
            Dialog.confirm('Desvincular', 'Estas seguro de quitar la caja para el trabajador?', function() {
                $scope.blockControl();
                $scope.view.trabajador.$removeCaja().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Trabajador actualizado.");
                        $scope.combo.selected.caja = undefined;
                        $scope.view.trabajador.caja = undefined;
                        $scope.view.trabajadorDB = angular.copy($scope.view.trabajador);
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            });
        };

        $scope.setCaja = function(){
            if ($scope.form.$valid) {
                $scope.blockControl();
                $scope.view.trabajador.$addCaja($scope.combo.selected.caja).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Caja asignada a Trabajador.");
                        $scope.view.trabajador.caja = $scope.combo.selected.caja;
                        $scope.view.trabajadorDB = angular.copy($scope.view.trabajador);
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
       
define(['../../../module'], function (module) {
    'use strict';

    module.controller('TrabajadorAsignarCajaCtrl', function($scope, $state, Agencia, Notifications){

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

        $scope.setCaja = function(){
            if ($scope.form.$valid) {
                $scope.blockControl();
                $scope.view.trabajador.$addCaja($scope.combo.selected.caja).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Caja asignada a Trabajador.");
                        $scope.view.trabajadorDB = angular.copy($scope.view.trabajador);
                        $scope.view.cajas.push($scope.combo.selected.caja);
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
       
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
                $scope.view.trabajador.caja = $scope.combo.selected.caja;
                $scope.view.trabajador.$save().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Trabajador actualizado.");
                        $scope.view.trabajadorDB = angular.copy($scope.view.trabajador);
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
       
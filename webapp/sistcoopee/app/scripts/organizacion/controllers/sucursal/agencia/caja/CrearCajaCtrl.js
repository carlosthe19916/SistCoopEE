define(['../../../module'], function (module) {
    'use strict';

    module.controller('CrearCajaCtrl', function($scope, $state, Sucursal, activeProfile, Notifications){

        $scope.view = {
            sucursal: undefined,
            agencia: undefined
        };

        $scope.combo = {
            sucursal: undefined
        };
        $scope.combo.selected = {
            sucursal: undefined
        };
        $scope.loadCombo = function(){
            if(angular.isUndefined($scope.view.sucursal)){
                $scope.combo.sucursal = Sucursal.$search().$object;
            }
        };
        $scope.loadCombo();

        $scope.addAgencia = function(){
            if($scope.form.$valid){
                $scope.combo.selected.sucursal.$addAgencia($scope.view.agencia).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Agencia creada");
                        $state.go('app.organizacion.estructura.editarAgencia.resumen', {id: response.id});
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            } else {
                $scope.form.$setSubmitted();
            }
        };

    }).controller('CrearCajaFromAgenciaCtrl', function($scope, $state, Currency, Sucursal, Notifications){

        $scope.view = {
            agencia: $scope.$parent.view.agenciaDB,
            caja: undefined
        };

        $scope.combo = {
            boveda: undefined
        };
        $scope.combo.selected = {
            boveda: undefined
        };
        $scope.loadCombo = function(){
            $scope.combo.boveda = $scope.view.agencia.$getBovedas().$object;
        };
        $scope.loadCombo();

        $scope.addCaja = function(){
            if($scope.form.$valid){
                $scope.view.caja.bovedas = $scope.combo.selected.boveda;
                $scope.blockControl();
                $scope.view.agencia.$addCaja($scope.view.caja).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Caja creada");
                        $state.go('^.^.resumen');
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

define(['../../../module'], function (module) {
    'use strict';

    module.controller('CrearCajaCtrl', function($scope, $state, Sucursal, Agencia, activeProfile, Notifications){

        $scope.view = {
            caja: undefined
        };

        $scope.combo = {
            sucursal: undefined,
            agencia: undefined,
            boveda: undefined
        };
        $scope.combo.selected = {
            sucursal: undefined,
            agencia: undefined,
            boveda: []
        };
        $scope.$watch('combo.selected.sucursal', function(){
            if(angular.isDefined($scope.combo.selected.sucursal)){
                $scope.combo.agencia = $scope.combo.selected.sucursal.$getAgencias().$object;
            }
        }, true);
        $scope.$watch('combo.selected.agencia', function(){
            if(angular.isDefined($scope.combo.selected.agencia)){
                $scope.combo.boveda = Agencia.$new($scope.combo.selected.agencia.id).$getBovedas().$object;
            }
        }, true);
        $scope.loadCombo = function(){
            $scope.combo.sucursal = Sucursal.$search().$object;
        };
        $scope.loadCombo();

        $scope.addCaja = function(){
            if($scope.form.$valid){
                $scope.view.caja.bovedas = $scope.combo.selected.boveda;
                Agencia.$new($scope.combo.selected.agencia.id).$addCaja($scope.view.caja).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Caja creada");
                        $state.go('app.organizacion.estructura.editarCaja.resumen', {id: response.id});
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

define(['../../../module'], function (module) {
    'use strict';

    module.controller('CrearBovedaCtrl', function($scope, $state, Sucursal, activeProfile, Notifications){

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

    }).controller('CrearBovedaFromAgenciaCtrl', function($scope, $state, Moneda, Sucursal, Notifications){

        $scope.view = {
            agencia: $scope.$parent.view.agenciaDB,
            boveda: undefined
        };

        $scope.combo = {
            moneda: undefined
        };
        $scope.combo.selected = {
            moneda: undefined
        };
        $scope.loadCombo = function(){
            $scope.combo.moneda = Moneda.$search().$object;
        };
        $scope.loadCombo();

        $scope.addBoveda = function(){
            if($scope.form.$valid){
                $scope.view.agencia.$addBoveda($scope.view.boveda).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Boveda creada");
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

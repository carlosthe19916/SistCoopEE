define(['../../../module'], function (module) {
    'use strict';

    module.controller('BovedaAbrirCtrl', function($scope, $state, Sucursal, Agencia, Currency, activeProfile, Notifications){

        $scope.view = {
            boveda: undefined
        };

        $scope.combo = {
            sucursal: undefined,
            agencia: undefined,
            moneda: undefined
        };
        $scope.combo.selected = {
            sucursal: undefined,
            agencia: undefined,
            moneda: undefined
        };
        $scope.$watch('combo.selected.sucursal', function(){
            if(angular.isDefined($scope.combo.selected.sucursal)){
                $scope.combo.agencia = $scope.combo.selected.sucursal.$getAgencias().$object;
            }
        }, true);
        $scope.loadCombo = function(){
            $scope.combo.sucursal = Sucursal.$search().$object;
            $scope.combo.moneda = Currency.$search().$object;
        };
        $scope.loadCombo();

        $scope.addBoveda = function(){
            if($scope.form.$valid){
                $scope.view.boveda.moneda = $scope.combo.selected.moneda.code;
                Agencia.$new($scope.combo.selected.agencia.id).$addBoveda($scope.view.boveda).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Boveda creada");
                        $state.go('app.organizacion.estructura.editarBoveda.resumen', {id: response.id});
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

    });
});

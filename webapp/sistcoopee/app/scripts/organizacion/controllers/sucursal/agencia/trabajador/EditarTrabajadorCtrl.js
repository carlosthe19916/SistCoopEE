define(['../../../module'], function (module) {
    'use strict';

    module.controller('EditarTrabajadorCtrl', function($scope, $state, PersonaNatural, Notifications){

        $scope.view = {
            trabajador: undefined,
            trabajadorDB: undefined,
            persona: undefined,
            cajas: []
        };

        $scope.loadParams = function(){
            $scope.view.trabajador = $scope.params.object;
            $scope.view.trabajadorDB = angular.copy($scope.params.object);
        };
        $scope.loadParams();

        $scope.loadPersona = function(){
            $scope.view.persona = PersonaNatural.$findByTipoNumeroDocumento($scope.view.trabajador.tipoDocumento, $scope.view.trabajador.numeroDocumento).$object;
        };
        $scope.loadPersona();
        $scope.loadCajas = function(){
            $scope.view.cajas =  $scope.view.trabajadorDB.$getCajas().$object;
        };
        $scope.loadCajas();

        $scope.combo = {
            sucursal: undefined,
            agencia: undefined,
            tipoDocumento: undefined
        };
        $scope.combo.selected = {
            sucursal: undefined,
            agencia: undefined,
            tipoDocumento: undefined
        };

        $scope.submit = function(){
            if ($scope.form.$valid) {
                if(angular.isUndefined($scope.view.persona)){
                    Notifications.warn("Debe de seleccionar una persona.");
                    return;
                }
                $scope.blockControl();
                $scope.view.trabajador.agencia = $scope.combo.selected.agencia;
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


define(['../../../module'], function (module) {
    'use strict';

    module.controller('CrearTrabajadorCtrl', function($scope, $state, Sucursal, Agencia, Trabajador, PersonaNatural, TipoDocumento, Notifications, activeProfile){

        $scope.view = {
            trabajador: Trabajador.$build()
        };

        $scope.view.loaded = {
            persona: undefined,
            trabajador: undefined
        };

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
                if(angular.isUndefined($scope.view.loaded.persona)){
                    Notifications.warn("Debe de seleccionar una persona.");
                    return;
                }
                if(angular.isDefined($scope.view.loaded.trabajador)){
                    Notifications.warn("El trabajador ya fue registrado, no puede continuar.");
                    return;
                }
                $scope.blockControl();
                $scope.view.trabajador.agencia = $scope.combo.selected.agencia;
                $scope.view.trabajador.tipoDocumento = $scope.combo.selected.tipoDocumento.abreviatura;
                $scope.view.trabajador.$save().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Trabajador creado.");
                        $state.go('app.organizacion.rrhh.editarTrabajador.resumen', {id: response.id});
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
       
define(['../../../module'], function (module) {
    'use strict';

    module.controller('TrabajadorDatosPrincipalesCtrl', function($scope, $state, Sucursal, Agencia, Trabajador, PersonaNatural, TipoDocumento, Notifications, activeProfile){

        var comboSucursalListener = $scope.$watch('combo.selected.sucursal', function(){
            if(angular.isDefined($scope.combo.selected.sucursal)){
                $scope.combo.agencia = $scope.combo.selected.sucursal.$getAgencias().$object;
            }
        }, true);
        $scope.loadCombo = function(){
            if(activeProfile.hasRole('ORGANIZACION', ['ADMIN', 'GERENTE_GENERAL'], 'OR')){
                $scope.combo.sucursal = Sucursal.$search().$object;
            } else if(activeProfile.hasRole('ORGANIZACION', ['ADMINISTRADOR_GENERAL'], 'OR')){
                $scope.combo.sucursal = [];
                $scope.combo.sucursal[0] = $scope.auth.user.sucursal;
                $scope.combo.selected.sucursal = $scope.combo.sucursal[0];
            } else if(activeProfile.hasRole('ORGANIZACION', ['ADMINISTRADOR', 'JEFE_CAJA'], 'OR')){
                comboSucursalListener();
                $scope.combo.sucursal = [];
                $scope.combo.sucursal[0] = $scope.auth.user.sucursal;
                $scope.combo.agencia = [];
                $scope.combo.agencia[0] = $scope.auth.user.agencia;
                $scope.combo.selected.sucursal = $scope.combo.sucursal[0];
                $scope.combo.selected.agencia = $scope.combo.agencia[0];
            }

            $scope.combo.tipoDocumento = TipoDocumento.$search({tipoPersona: 'natural'}).$object
        };
        $scope.loadCombo();

        $scope.check = function($event){
            if(!angular.isUndefined($event))
                $event.preventDefault();
            if(!angular.isUndefined($scope.combo.selected.tipoDocumento) && !angular.isUndefined($scope.view.trabajador.numeroDocumento)){
                PersonaNatural.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.trabajador.numeroDocumento).then(function(data){
                    if(!data){
                        Notifications.warn("Persona no encontrada.");
                        $scope.view.loaded.persona = undefined;
                    } else {
                        $scope.view.loaded.persona = data;
                    }
                });

                Trabajador.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.trabajador.numeroDocumento).then(function(data){
                    if(data){
                        Notifications.warn("El trabajador ya fue registrado, no puede continuar.");
                        $scope.view.loaded.trabajador = data;
                    } else {
                        $scope.view.loaded.trabajador = undefined;
                    }
                });
            }
        };

        $scope.addPersona = function(){
            $state.go('app.administracion.personas.crearPersonaNatural.datosPrincipales');
        };

    });
});
       
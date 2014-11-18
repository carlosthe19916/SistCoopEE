/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers')

        .controller('PersonaJuridicaRepresentanteLegalCtrl', function($scope, $state, TipoDocumento, PersonaNatural, Notifications, Navigation){

            $scope.refreshPage = function(){
                $scope.form.$setPristine();
            };
            $scope.refreshPage();

            $scope.view.representante = $scope.view.hasOwnProperty('representante') ? $scope.view.representante : undefined;

            $scope.combo = {
                tipoDocumento: TipoDocumento.$search({tipoPersona: 'natural'}).$object
            };
            $scope.combo.selected = {
                tipoDocumento: undefined
            };
            $scope.combo.synchronize = function(){
                $scope.view.persona.representanteLegal.tipoDocumento = $scope.combo.selected.tipoDocumento ? $scope.combo.selected.tipoDocumento.abreviatura: undefined;
            };

            $scope.setRepresentante = function($event){
                if(!angular.isUndefined($event))
                    $event.preventDefault();
                if(angular.isDefined($scope.combo.selected.tipoDocumento)
                    && angular.isDefined($scope.view.representante.numeroDocumento)){
                    PersonaNatural.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.representante.numeroDocumento).then(function(data){
                        if(data)
                            $scope.view.persona.representanteLegal = data;
                        else
                            Notifications.warn("Persona no encontrada.");
                    });
                }
            };

            $scope.goTabPrincipal = function(){
                $state.go('app.administracion.crearPersonaJuridica.principal');
            };
            $scope.goCrearPersonaNatural = function(){
                $scope.combo.synchronize();
                Navigation.addState({
                    name: 'Crear persona juridica...',
                    state: 'app.administracion.crearPersonaJuridica.representante',
                    params:{id: $scope.view.persona.id},
                    object: $scope.view
                });
                $state.go('app.administracion.crearPersonaNatural');
            };
        });

})(window, window.angular);


        
/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers')
        .controller('PersonaJuridicaDatosPrincipalesCtrl', function($scope, $state, Pais, TipoDocumento, TipoEmpresa, PersonaJuridica, Notifications){

            $scope.combo = {
                pais: Pais.$search().$object,
                tipoDocumento: TipoDocumento.$search({tipoPersona: 'juridica'}).$object,
                tipoEmpresa: TipoEmpresa.$search().$object
            };
            $scope.combo.selected = {
                pais: undefined,
                tipoDocumento: undefined,
                tipoEmpresa: undefined
            };

            $scope.checkPersona = function($event){
                if(!angular.isUndefined($event))
                    $event.preventDefault();
                if(!angular.isUndefined($scope.combo.selected.tipoDocumento)
                    && !angular.isUndefined($scope.view.persona.numeroDocumento)){
                    PersonaJuridica.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.persona.numeroDocumento).then(function(data){
                        if(!data)
                            Notifications.info("Documento de identidad disponible.");
                        else
                            Notifications.warn("Documento de identidad no disponible.");
                    });
                }
            };

            $scope.goTabRepresentante = function(){
                if($scope.form.$valid){
                    $state.go('app.administracion.crearPersonaJuridica.representante');
                } else {
                    $scope.form.$setSubmitted();
                }
            };

        });

})(window, window.angular);


        
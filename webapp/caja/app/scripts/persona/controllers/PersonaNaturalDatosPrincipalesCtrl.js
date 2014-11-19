/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers')

        .controller('PersonaNaturalDatosPrincipalesCtrl', function($scope, $state, Storage, Pais, Sexo, EstadoCivil, PersonaNatural, TipoDocumento, Notifications){

            $scope.refreshPage = function(){
                $scope.form.$setPristine();
            };
            $scope.refreshPage();

            $scope.combo = {
                pais: Pais.$search().$object,
                tipoDocumento: TipoDocumento.$search({tipoPersona: 'natural'}).$object,
                sexo: Sexo.$search().$object,
                estadoCivil: EstadoCivil.$search().$object
            };
            $scope.combo.selected = {
                pais: undefined,
                tipoDocumento: undefined,
                sexo: undefined,
                estadoCivil: undefined
            };

            $scope.check = function($event){
                if(!angular.isUndefined($event))
                    $event.preventDefault();
                if(!angular.isUndefined($scope.combo.selected.tipoDocumento) && !angular.isUndefined($scope.view.persona.numeroDocumento)){
                    PersonaNatural.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.persona.numeroDocumento).then(function(data){
                        if(!data)
                            Notifications.info("Documento de identidad disponible.");
                        else
                            Notifications.warn("Documento de identidad no disponible.");
                    });
                }
            };

            $scope.submit = function(){
                console.log("carlos");
            };

        });

})(window, window.angular);
       
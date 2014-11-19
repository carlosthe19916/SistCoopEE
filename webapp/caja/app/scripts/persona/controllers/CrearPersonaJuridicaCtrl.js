/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers')
        .controller('CrearPersonaJuridicaCtrl', function($scope, $state, PersonaJuridica, Notifications){

            $scope.view = {
                persona: PersonaJuridica.$build()
            };

            $scope.loadParams = function(){
                $scope.view.persona.tipoDocumento = $scope.params.tipoDocumento;
                $scope.view.persona.numeroDocumento = $scope.params.numeroDocumento;
            };
            $scope.loadParams();

            $scope.submit = function(){
                if ($scope.form.$valid) {

                    if(angular.isUndefined($scope.view.persona.representanteLegal)){
                        Notifications.warn('Representante legal no definido.');
                        return;
                    }
                    if(angular.isUndefined($scope.view.persona.representanteLegal.id)){
                        Notifications.warn('Representante legal no definido.');
                        return;
                    }

                    $scope.blockControl();
                    PersonaJuridica.$findByTipoNumeroDocumento($scope.view.persona.tipoDocumento, $scope.view.persona.numeroDocumento).then(function(response){
                        if(response) {
                            $scope.unblockControl();
                            Notifications.error("Documento de identidad no disponible.");
                        } else {
                            $scope.save();
                        }
                    });
                }
            };

            $scope.save = function(){
                $scope.blockControl();
                $scope.view.persona.representanteLegal = {
                    tipoDocumento: $scope.view.persona.representanteLegal.tipoDocumento,
                    numeroDocumento: $scope.view.persona.representanteLegal.numeroDocumento
                };
                $scope.view.persona.$save().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Persona creada");
                        $state.go('app.administracion.editarPersonaJuridica', {id: response.data.id});
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            };

        });

})(window, window.angular);

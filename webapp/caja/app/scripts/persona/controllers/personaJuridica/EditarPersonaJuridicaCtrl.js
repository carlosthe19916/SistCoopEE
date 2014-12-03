define(['module'], function (module) {
    'use strict';

    module.controller('EditarPersonaJuridicaCtrl', function($scope, $state, $modal, Pais, Sexo, EstadoCivil, PersonaJuridica, TipoDocumento, Notifications){

        $scope.view = {
            persona: undefined,
            personaDB: undefined
        };

        $scope.loadParams = function(){
            $scope.view.personaDB = $scope.params.object;
            $scope.view.personaDB.accionistas = $scope.params.object.$getAccionistas().$object;
            $scope.view.persona = angular.copy($scope.view.personaDB);
        };
        $scope.loadParams();

        $scope.submit = function(){
            if ($scope.form.$valid) {
                $scope.blockControl();
                var save = function(){
                    $scope.view.persona.$save().then(
                        function(data){
                            $scope.unblockControl();
                            $scope.view.personaDB = angular.copy($scope.view.persona);
                            Notifications.success("Persona actualizada");
                        },
                        function error(error){
                            $scope.unblockControl();
                            Notifications.error(error.data+".");
                        }
                    );
                };
                PersonaJuridica.$findByTipoNumeroDocumento($scope.view.persona.tipoDocumento, $scope.view.persona.numeroDocumento).then(function(data){
                    if(data && data.id == $scope.view.persona.id){
                        Notifications.info("Documento de identidad disponible.");
                        save();
                    }
                    else {
                        Notifications.warn("Documento de identidad no disponible.");
                    }
                });
            }
        };
    });
});


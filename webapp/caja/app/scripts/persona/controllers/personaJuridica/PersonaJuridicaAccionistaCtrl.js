define(['module'], function (module) {
    'use strict';

    module.controller('AccionistaCtrl', function($scope, $state, TipoDocumento, PersonaNatural, Notifications, Navigation){

        $scope.entradas = {
            tipoDocumento: undefined,
            numeroDocumento: undefined,
            porcentaje: undefined
        };
        $scope.buscados = {
            persona: undefined
        };

        $scope.combo = {
            tipoDocumento: TipoDocumento.$search({tipoPersona: 'natural'}).$object
        };
        $scope.combo.selected = {
            tipoDocumento: undefined
        };

        $scope.checkAccionista = function($event){
            if(!angular.isUndefined($event))
                $event.preventDefault();
            if(!angular.isUndefined($scope.combo.selected.tipoDocumento)
                && !angular.isUndefined($scope.entradas.numeroDocumento)){
                PersonaNatural.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.entradas.numeroDocumento).then(function(response){
                    if(!response)
                        Notifications.warn("Persona no encontrada.");
                    $scope.buscados.persona = response;
                });
            }
        };

        $scope.crearAccionista = function(){
            if($scope.form.$valid){
                var accionista = {
                    tipoDocumento: $scope.entradas.tipoDocumento,
                    numeroDocumento: $scope.entradas.numeroDocumento,
                    porcentajeParticipacion: $scope.entradas.porcentaje
                };
                $scope.view.persona.$addAccionista(accionista).then(
                    function(data){
                        $scope.unblockControl();
                        Notifications.success("Accionista agregado");
                        $scope.buscados.persona.porcentajeParticipacion = $scope.entradas.porcentaje;
                        $scope.view.persona.accionistas.push($scope.buscados.persona);
                        $scope.view.personaDB.accionistas.push($scope.buscados.persona);
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            }
        };

        $scope.editarPersonaNatural = function(item){
            $state.go('app.administracion.editarPersonaNatural', {id:item.id});
        };

        $scope.goCrearPersonaNatural = function(){
            //$scope.combo.synchronize();
            Navigation.addState({name: 'Editar P.juridica', state: 'app.administracion.editarPersonaJuridica.accionista', params:{id: $scope.view.personaDB.id}, object: $scope.view});
            $state.go('app.administracion.crearPersonaNatural');
        };

    });
});






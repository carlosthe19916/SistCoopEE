
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers')
        .controller('AccionistaCtrl', function($scope, $state, TipoDocumento, PersonaNatural, Notifications, Navigation){

            $scope.refreshPage = function(){
                $scope.form.$setPristine();
                $scope.view.persona = angular.copy($scope.view.personaDB);
            };
            $scope.refreshPage();

            $scope.view = {
                personaDB: $scope.view.personaDB,
                accionista: undefined,
                accionistaDB: undefined
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
                    && !angular.isUndefined($scope.view.accionista.numeroDocumento)){
                    PersonaNatural.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.accionista.numeroDocumento).then(function(data){
                        if(!data)
                            Notifications.warn("Persona no encontrada.");
                        $scope.view.accionistaDB = data;
                    });
                }
            };

            $scope.crearAccionista = function(){
                var accionista = {
                    tipoDocumento: $scope.view.accionistaDB.tipoDocumento,
                    numeroDocumento: $scope.view.accionistaDB.numeroDocumento,
                    porcentajeParticipacion: $scope.view.accionista.porcentajeParticipacion
                };
                $scope.view.personaDB.$addAccionista(accionista).then(
                    function(data){
                        $scope.unblockControl();
                        Notifications.success("Accionista agregado");
                        $scope.view.personaDB.accionistas.push($scope.view.accionistaDB);
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            };

            $scope.goCrearPersonaNatural = function(){
                //$scope.combo.synchronize();
                Navigation.addState({name: 'Editar P.juridica', state: 'app.administracion.editarPersonaJuridica.accionista', params:{id: $scope.view.personaDB.id}, object: $scope.view});
                $state.go('app.administracion.crearPersonaNatural');
            };

        })
    ;

})(window, window.angular);


define(['../module'], function (module) {
    'use strict';

    module.controller('PersonaNaturalDatosPrincipalesCtrl', function($scope, $state, Storage, Pais, Sexo, EstadoCivil, PersonaNatural, TipoDocumento, Notifications){

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
});
       
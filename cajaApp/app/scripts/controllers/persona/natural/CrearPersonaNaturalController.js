define(['../../module'], function (controllers) {
    'use strict';
    controllers.controller('CrearPersonaNaturalController', ['$scope','$state','PersonaNatural','Country','TipoDocumento','Sexo','EstadoCivil','HelperService',
        function($scope,$state,PersonaNatural,Country,TipoDocumento,Sexo,EstadoCivil,HelperService) {

            $scope.combo = {
                pais: Country.$search(),
                tipoDocumento: TipoDocumento.$search({tipoPersona: 'NATURAL'}),
                sexo: Sexo.$search(),
                estadoCivil: EstadoCivil.$search()
            };

            $scope.view = {
                personaNatural: PersonaNatural.$build()
            };
/*
            $scope.tipoDocumentoLength = 0;
            $scope.$watch('view.personaNatural.tipoDocumento', function(){
                if(!angular.isUndefined($scope.view.personaNatural.tipoDocumento)){
                    $scope.tipoDocumentoLength = HelperService.getTipoDocumentoLength($scope.combo.tipoDocumento, $scope.view.personaNatural.tipoDocumento);
                }
                $scope.view.personaNatural.numeroDocumento = '';
            });*/

            $scope.crearTransaccion = function(){
                console.log($scope.formCrearPersonanatural);
                $scope.addSuccessMessage("Persona creada satisfactoriamente.");
            };

            $scope.tamanio = 3;

        }]);
});
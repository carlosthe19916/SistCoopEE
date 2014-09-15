define(['../../module'], function (controllers) {
    'use strict';
    controllers.controller('CrearPersonaNaturalController', ['$scope','$state','PersonaNatural','Country','TipoDocumento','Sexo','EstadoCivil','HelperService',
        function($scope,$state,PersonaNatural,Country,TipoDocumento,Sexo,EstadoCivil,HelperService) {

            $scope.combo = {
                pais: Country.$search(),
                tipoDocumento: TipoDocumento.$search(),
                sexo: Sexo.$search(),
                estadoCivil: EstadoCivil.$search()
            };

            $scope.view = {
                personaNatural: PersonaNatural.$build()
            };

            $scope.getTipoDocumentoLength = function(){
                return HelperService.getTipoDocumentoLength($scope.combo.tipoDocumento, $scope.view.personaNatural.tipoDocumento);
            };

            $scope.crearTransaccion = function(){
                console.log($scope.formCrearPersonanatural);
                $scope.addSuccessMessage("Persona creada satisfactoriamente.");
            };

            $scope.tamanio = 3;

        }]);
});
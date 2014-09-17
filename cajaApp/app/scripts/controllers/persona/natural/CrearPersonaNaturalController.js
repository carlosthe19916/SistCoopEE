define(['../../module'], function (controllers) {
    'use strict';
    controllers.controller('CrearPersonaNaturalController', ['$scope','$state','PersonaNatural','Country','TipoDocumento','Sexo','EstadoCivil','HelperService',
        function($scope,$state,PersonaNatural,Country,TipoDocumento,Sexo,EstadoCivil,HelperService) {

            $scope.combo = {
                pais: Country.$search(),
                sexo: Sexo.$search(),
                estadoCivil: EstadoCivil.$search()
            };

            $scope.view = {
                personaNatural: PersonaNatural.$build()
            };

            $scope.crearTransaccion = function(){
                console.log($scope.formCrearPersonanatural);
                $scope.addSuccessMessage("Persona creada satisfactoriamente.");
            };


        }]);
});
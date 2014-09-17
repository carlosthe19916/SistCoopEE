define(['../../module'], function (controllers) {
    'use strict';
    controllers.controller('CrearPersonaNaturalController', ['$scope','$state','PersonaNatural','Country','TipoDocumento','Sexo','EstadoCivil',
        function($scope,$state,PersonaNatural,Country,TipoDocumento,Sexo,EstadoCivil) {

            $scope.combo = {
                pais: Country.$search(),
                sexo: ['MASCULINO','FEMENINO'],
                estadoCivil: ['SOLTERO', 'CASADO', 'DIVORCIADO', 'VIUDO']
            };

            $scope.view = {
                personaNatural: PersonaNatural.$build()
            };

            $scope.crearTransaccion = function(){
                if ($scope.formCrearPersonanatural.$valid) {
                    $scope.control.inProcess = true;

                    console.log($scope.view.personaNatural);
                    $scope.view.personaNatural.$save();
                    $scope.addSuccessMessage("Persona creada satisfactoriamente.");
                }
            };

            $scope.cancelar = function () {
                console.log("cancelando");
            };

        }]);
});
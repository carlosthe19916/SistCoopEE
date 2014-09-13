define(['../../module'], function (controllers) {
    'use strict';
    controllers.controller('CrearPersonaNaturalController', ['$scope','$state','PersonaNatural','Country','TipoDocumento','Sexo','EstadoCivil',
        function($scope,$state,PersonaNatural,Country,TipoDocumento,Sexo,EstadoCivil) {

            $scope.combo = {
                pais: Country.$search(),
                tipoDocumento: TipoDocumento.$search(),
                sexo: Sexo.$search(),
                estadoCivil: EstadoCivil.$search()
            };

            $scope.view = {
                personaNatural: PersonaNatural.$build()
            };

            $scope.loadParametros = function(){
                $scope.view.personaNatural.numeroDocumento = $scope.params.numeroDocumento;
                $scope.view.personaNatural.tipoDocumento = $scope.params.tipoDocumento;
            };

            $scope.crearTransaccion = function(){
                console.log($scope.view.personaNatural);
                $scope.addSuccessMessage("Persona creada satisfactoriamente.");
            };

        }]);
});
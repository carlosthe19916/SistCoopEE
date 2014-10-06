angular.module('persona.controllers', [])
    .controller('CrearPersonaNaturalController', function($scope, focus, Country, Departamento, Sexo, EstadoCivil){

        $scope.view = {
            personaNatural: undefined
        };

        $scope.combo = {
            pais: Country.$search(),
            sexo: Sexo.$search(),
            estadoCivil: EstadoCivil.$search()
        };

        $scope.combo.selected = {
            pais: undefined,
            sexo: undefined
        };

        $scope.prueba = function(sexo){
            console.log(sexo);
        };

        $scope.crearTransaccion = function(){
            console.log($scope.formCrearPersonanatural.apellidoPaterno);
        };

    });

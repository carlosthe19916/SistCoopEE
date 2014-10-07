angular.module('persona.controllers', [])
    .controller('CrearPersonaNaturalController', function($scope, focus, Country, Departamento, Sexo, EstadoCivil, PersonaNatural){



        $scope.view = {
            personaNatural: PersonaNatural.$build()
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


        /*Cargar parametros de URL*/
        $scope.loadParams = function(){
            $scope.view.personaNatural.tipoDocumento = $scope.params.tipoDocumento;
            $scope.view.personaNatural.numeroDocumento = $scope.params.numeroDocumento;
        };
        $scope.loadParams();

        $scope.crearTransaccion = function(){
            console.log($scope.formCrearPersonanatural.apellidoPaterno);
        };

    });

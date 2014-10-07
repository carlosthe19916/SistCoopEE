angular.module('persona.controllers', [])
    .controller('CrearPersonaNaturalController', function($scope, focus, Country, Departamento, Sexo, EstadoCivil, PersonaNatural){

        /*Datos de la vista*/
        $scope.view = {
            personaNatural: PersonaNatural.$build()
        };

        /*combos*/
        $scope.combo = {
            pais: Country.$search(),
            sexo: Sexo.$search(),
            estadoCivil: EstadoCivil.$search()
        };
        $scope.combo.selected = {
            pais: undefined,
            sexo: undefined,
            estadoCivil: undefined
        };

        /*Cargar parametros de URL*/
        $scope.loadParams = function(){
            $scope.view.personaNatural.tipoDocumento = $scope.params.tipoDocumento;
            $scope.view.personaNatural.numeroDocumento = $scope.params.numeroDocumento;
        };
        $scope.loadParams();

        /*Operacion principal*/
        $scope.crearTransaccion = function(){
            if ($scope.formCrearPersonanatural.$valid) {
                $scope.view.personaNatural.codigoPais = $scope.combo.selected.pais ? $scope.combo.selected.pais.alpha3Code: null;
                $scope.view.personaNatural.sexo = $scope.combo.selected.sexo ? $scope.combo.selected.sexo.denominacion : null;
                $scope.view.personaNatural.estadoCivil = $scope.combo.selected.estadoCivil ? $scope.combo.selected.estadoCivil.denominacion : null;

                $scope.view.personaNatural.$save();
            } else {
                console.log($scope.formCrearPersonanatural);
            }
        };

    });

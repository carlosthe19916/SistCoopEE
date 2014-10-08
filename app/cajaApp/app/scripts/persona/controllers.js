angular.module('persona.controllers', [])
    .controller('CrearPersonaNaturalController', function($scope, focus, blockUI, Country, Departamento, Sexo, EstadoCivil, PersonaNatural){

        /*Poner foco inicial*/
        $scope.focusPais = function() {
            focus('focusPais');
        };
        $scope.focusPais();

        /*Datos de la vista*/
        $scope.view = {
            personaNatural: PersonaNatural.$find(2)
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
            console.log($scope.view.personaNatural.$response.data);
            if ($scope.formCrearPersonanatural.$valid) {

                var aa = PersonaNatural.$find(1);

                var personaServer = PersonaNatural.findByTipoNumeroDocumento($scope.view.personaNatural.tipoDocumento, $scope.view.personaNatural.numeroDocumento);
                personaServer.$then(function(_persona) {
                    console.log(_persona);
                });

               /* aa.$then(function(_persona) {
                    console.log(aa);
                });*/


                $scope.view.personaNatural.codigoPais = $scope.combo.selected.pais ? $scope.combo.selected.pais.alpha3Code: null;
                $scope.view.personaNatural.sexo = $scope.combo.selected.sexo ? $scope.combo.selected.sexo.denominacion : null;
                $scope.view.personaNatural.estadoCivil = $scope.combo.selected.estadoCivil ? $scope.combo.selected.estadoCivil.denominacion : null;

                $scope.view.personaNatural.$save();
            }
        };

    });

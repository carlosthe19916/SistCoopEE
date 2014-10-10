angular.module('persona.controllers', [])
    .controller('CrearPersonaNaturalController', function($scope, focus, Country, Departamento, Sexo, EstadoCivil, PersonaNatural){

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

                $scope.view.personaNatural.codigoPais = $scope.combo.selected.pais ? $scope.combo.selected.pais.alpha3Code: null;
                $scope.view.personaNatural.sexo = $scope.combo.selected.sexo ? $scope.combo.selected.sexo.denominacion : null;
                $scope.view.personaNatural.estadoCivil = $scope.combo.selected.estadoCivil ? $scope.combo.selected.estadoCivil.denominacion : null;

                $scope.view.personaNatural.$save();
            }
        };

    })
    .controller('BuscarPersonaNaturalController', function($scope, $timeout){

    })
    .controller('BuscarPersonaJuridicaController', function($scope, focus, PersonaJuridica){

        $scope.focusPais = function() {
            focus('focusFilterText');
        };
        $scope.focusPais();

        $scope.filterOptions = {
            filterText: undefined,
            result: []
        };

        $scope.gridOptions = {
            data: $scope.filterOptions.result,
            columnDefs: [
                {field: 'tipoDocumento', displayName: 'Documento'},
                {field: 'numeroDocumento', displayName: 'Numero'},
                {field: 'razonSocial', displayName: 'Razon social'},
                {field: 'nombreComercial', displayName: 'Nombre comercial'},
                {field: 'tipoEmpresa', displayName: 'Tipo Empresa'},
                {field: 'numeroDocumento', displayName: 'Numero'},
                {name: 'edit', displayName: 'Edit', cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="edit(row.entity)" >Edit</button> '}
            ]
        };


        $scope.search = function(){
            PersonaJuridica.$search({filterText: $scope.filterOptions.filterText});
        };
    });

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
    .controller('BuscarPersonaNaturalController', function($scope, focus, PersonaNatural){
        $scope.focusForm = function() {
            focus('focusFilterText');
        };
        $scope.focusForm();

        $scope.filterOptions = {
            filterText: undefined,
            result: []
        };

        $scope.gridOptions = {
            data: [{tipoDocumento: 'RUC', numeroDocumento: '12345678910', razonSocial: 'Softgreen', nombreComercial: 'Softgreen soluciones', tipoEmpresa: 'PRIVADA'}],
            columnDefs: [
                {field: 'tipoDocumento', displayName: 'Documento'},
                {field: 'numeroDocumento', displayName: 'Numero'},
                {field: 'razonSocial', displayName: 'Razon social'},
                {field: 'nombreComercial', displayName: 'Nombre comercial'},
                {field: 'tipoEmpresa', displayName: 'Tipo Empresa'},
                {name: 'edit', displayName: 'Edit', cellTemplate: '<div style="text-align: center;"><button id="editBtn" type="button" class="btn btn-info btn-small" ng-click="edit(row.entity)">Edit</button></div>'}
            ]
        };

        $scope.search = function(){
            PersonaNatural.$search({filterText: $scope.filterOptions.filterText});
        };
    })
    .controller('BuscarPersonaJuridicaController', function($scope, focus, PersonaJuridica){
        this.form = $scope.form;

        $scope.focusForm = function() {
            focus('focusFilterText');
        };
        $scope.focusForm();

        $scope.filterOptions = {
            filterText: undefined,
            result: []
        };

        $scope.gridOptions = {
            data: [{tipoDocumento: 'RUC', numeroDocumento: '12345678910', razonSocial: 'Softgreen', nombreComercial: 'Softgreen soluciones', tipoEmpresa: 'PRIVADA'}],
            columnDefs: [
                {field: 'tipoDocumento', displayName: 'Documento'},
                {field: 'numeroDocumento', displayName: 'Numero'},
                {field: 'razonSocial', displayName: 'Razon social'},
                {field: 'nombreComercial', displayName: 'Nombre comercial'},
                {field: 'tipoEmpresa', displayName: 'Tipo Empresa'},
                {name: 'edit', displayName: 'Edit', cellTemplate: '<div style="text-align: center;"><button id="editBtn" type="button" class="btn btn-info btn-small" ng-click="edit(row.entity)">Edit</button></div>'}
            ]
        };

        $scope.search = function(){
            PersonaJuridica.$search({filterText: $scope.filterOptions.filterText});
        };
    });

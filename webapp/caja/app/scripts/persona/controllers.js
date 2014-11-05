/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers', [])
        .controller('CrearPersonaNaturalController', function($scope, $state, Pais, Sexo, EstadoCivil, PersonaNatural, TipoDocumento, Notifications){

            /*Datos de la vista*/
            $scope.view = {
                personaNatural: PersonaNatural.$build()
            };

            /*combos*/
            $scope.combo = {
                pais: Pais.$search().$object,
                tipoDocumento: TipoDocumento.$search({tipoPersona: 'natural'}).$object,
                sexo: Sexo.$search().$object,
                estadoCivil: EstadoCivil.$search().$object
            };
            $scope.combo.selected = {
                pais: undefined,
                tipoDocumento: undefined,
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
                if ($scope.form.$valid) {
                    $scope.blockControl();
                    var save = function(){
                        $scope.view.personaNatural.codigoPais = $scope.combo.selected.pais ? $scope.combo.selected.pais.alpha3Code: null;
                        $scope.view.personaNatural.tipoDocumento = $scope.combo.selected.tipoDocumento ? $scope.combo.selected.tipoDocumento.abreviatura: null;
                        $scope.view.personaNatural.sexo = $scope.combo.selected.sexo ? $scope.combo.selected.sexo.denominacion : null;
                        $scope.view.personaNatural.estadoCivil = $scope.combo.selected.estadoCivil ? $scope.combo.selected.estadoCivil.denominacion : null;
                        $scope.view.personaNatural.$save().then(
                            function(data){
                                $scope.unblockControl();
                                Notifications.success("Persona creada");
                                $state.go('app.administracion.buscarPersonaNatural');
                            },
                            function error(error){
                                Notifications.error(error.data+".");
                            }
                        );
                    };
                    PersonaNatural.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.personaNatural.numeroDocumento).then(function(data){
                        if(data)
                            Notifications.error("Documento de identidad no disponible.");
                        else
                            save();
                    });
                }
            };

            /*Verificar persona*/
            $scope.check = function($event){
                if(!angular.isUndefined($event))
                    $event.preventDefault();
                if(!angular.isUndefined($scope.combo.selected.tipoDocumento) && !angular.isUndefined($scope.view.personaNatural.numeroDocumento)){
                    PersonaNatural.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.personaNatural.numeroDocumento).then(function(data){
                        if(!data)
                            Notifications.info("Documento de identidad disponible.");
                        else
                            Notifications.warn("Documento de identidad no disponible.");
                    });
                }
            };

        })

        .controller('BuscarController', function($scope){
            $scope.filterOptions = {
                filterText: undefined,
                offset: 0,
                limit: 10
            };
        })
        .controller('BuscarPersonaNaturalController', function($scope, $state, PersonaNatural){
            $scope.nuevo = function(){
                $state.go('app.administracion.crearPersonaNatural');
            };
            $scope.gridOptions = {
                data: [],
                enableRowSelection: false,
                enableRowHeaderSelection: false,
                multiSelect: false,
                columnDefs: [
                    {field: 'tipoDocumento', displayName: 'Documento'},
                    {field: 'numeroDocumento', displayName: 'Numero'},
                    {field: 'apellidoPaterno', displayName: 'Ap.paterno'},
                    {field: 'apellidoMaterno', displayName: 'Ap.materno'},
                    {field: 'nombres', displayName: 'Nombres'},
                    {field: 'sexo', displayName: 'Sexo'},
                    {
                        name: 'edit',
                        displayName: 'Edit',
                        cellTemplate: '<div style="text-align: center; padding-top: 5px;"><button type="button" ng-click="getExternalScopes().edit(row.entity)" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-edit"></span>Editar</button></div>'
                    }
                ]
            };
            $scope.gridActions = {
                edit: function(row){
                    $state.go('app.administracion.editarPersonaNatural', {id: row.id});
                }
            };

            $scope.removeColumn = function(){
                if(!$scope.activeProfile.hasPermission('PERSONA', 'UPDATE')){
                    $scope.gridOptions.columnDefs.splice($scope.gridOptions.columnDefs.length - 1, 1);
                }
            };
            $scope.removeColumn();

            $scope.search = function(){
                $scope.gridOptions.data = PersonaNatural.$search($scope.filterOptions).$object;
            };
        })
        .controller('BuscarPersonaJuridicaController', function($scope, $state, PersonaJuridica){
            $scope.nuevo = function(){
                $state.go('app.administracion.crearPersonaJuridica');
            };
            $scope.gridOptions = {
                data: [],
                enableRowSelection: false,
                enableRowHeaderSelection: false,
                multiSelect: false,
                columnDefs: [
                    {field: 'tipoDocumento', displayName: 'Documento'},
                    {field: 'numeroDocumento', displayName: 'Numero'},
                    {field: 'razonSocial', displayName: 'Razon social'},
                    {field: 'nombreComercial', displayName: 'Nombre comercial'},
                    {field: 'tipoEmpresa', displayName: 'Tipo Empresa'},
                    {
                        name: 'edit',
                        displayName: 'Edit',
                        cellTemplate: '<div style="text-align: center; padding-top: 5px;"><button type="button" ng-click="getExternalScopes().edit(row.entity)" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-edit"></span>Editar</button></div>'
                    }
                ]
            };
            $scope.gridActions = {
                edit: function(row){
                    $state.go('app.administracion.editarPersonaJuridica', {id: row.id});
                }
            };

            $scope.removeColumn = function(){
                if(!$scope.activeProfile.hasPermission('PERSONA', 'UPDATE')){
                    $scope.gridOptions.columnDefs.splice($scope.gridOptions.columnDefs.length - 1, 1);
                }
            };
            $scope.removeColumn();

            $scope.search = function(){
                $scope.gridOptions.data = PersonaJuridica.$search($scope.filterOptions).$object;
            };
        });


})(window, window.angular);
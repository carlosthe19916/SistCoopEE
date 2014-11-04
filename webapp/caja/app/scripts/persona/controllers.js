/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers', [])
        .controller('CrearPersonaNaturalController', function($scope, Sexo, EstadoCivil, PersonaNatural, TipoDocumento){

            /*Datos de la vista*/
            $scope.view = {
                personaNatural: PersonaNatural.$build()
            };

            /*combos*/
            $scope.combo = {
                pais: undefined,
                tipoDocumento: TipoDocumento.$search({tipoPersona: 'natural'}),
                sexo: Sexo.$search(),
                estadoCivil: EstadoCivil.$search()
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

            /*Verificar persona*/
            $scope.check = function($event){
                if(!angular.isUndefined($event))
                    $event.preventDefault();
                if(!angular.isUndefined($scope.$scope.combo.selected.tipoDocumento) && !angular.isUndefined($scope.view.personaNatural.numeroDocumento)){
                    var result = PersonaNatural.$single(personaConfig.urlPrefix + '/personas/naturales/buscar').$fetch({tipoDocumento:$scope.tipoDocumento, numeroDocumento: $scope.numeroDocumento});
                    result.$then(function(data) {
                        if(!data.$response.data){
                            Notifications.info("Documento de identidad disponible.");
                        } else {
                            Notifications.warn("Documento de identidad no disponible.");
                        }
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
            $scope.viewActions = {
                nuevo: function(){
                    console.log("Method unimplemented");
                }
            };
            $scope.gridOptions = {
                data: [],
                columnDefs: []
            };
        })
        .controller('BuscarPersonaController', function($scope){
            $scope.gridOptions = {
                enableRowSelection: true,
                enableRowHeaderSelection: false,
                multiSelect: false,
                columnDefs: [
                    {
                        name: 'edit',
                        displayName: 'Edit',
                        cellTemplate: '<div style="text-align: center; padding-top: 5px;"><button type="button" ng-click="edit(row.entity)" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-edit"></span>Editar</button></div>'
                    }
                ]
            };
            $scope.removeColumn = function(){
                if(!$scope.activeProfile.hasPermission('persona', 'update')){
                    $scope.gridOptions.columnDefs.splice($scope.gridOptions.columnDefs.length - 1, 1);
                }
            };
            $scope.removeColumn();
        })
        .controller('BuscarPersonaNaturalController', function($scope, $state, PersonaNatural, TipoDocumento){

            $scope.viewActions.nuevo = function(){
                $state.go('app.administracion.crearPersonaNatural');
            };

            $scope.gridOptions.columnDefs.push({field: 'tipoDocumento', displayName: 'Documento'});
            $scope.gridOptions.columnDefs.push({field: 'numeroDocumento', displayName: 'Numero'});
            $scope.gridOptions.columnDefs.push({field: 'apellidoPaterno', displayName: 'Ap.paterno'});
            $scope.gridOptions.columnDefs.push({field: 'apellidoMaterno', displayName: 'Ap.materno'});
            $scope.gridOptions.columnDefs.push({field: 'nombres', displayName: 'Nombres'});
            $scope.gridOptions.columnDefs.push({field: 'sexo', displayName: 'Sexo'});

            $scope.moveEditColum = function(){
                if(!angular.isUndefined($scope.gridOptions.columnDefs[0].name == 'edit')){
                    $scope.gridOptions.columnDefs.push($scope.gridOptions.columnDefs.shift());
                }
            };
            $scope.moveEditColum();

            $scope.search = function(){
                $scope.gridOptions.data = PersonaNatural.$search($scope.filterOptions);
            };

        })
        .controller('BuscarPersonaJuridicaController', function($scope, $state, PersonaJuridica){
            $scope.viewActions.nuevo = function(){
                $state.go('app.administracion.crearPersonaJuridica');
            };

            $scope.gridOptions.columnDefs.push({field: 'tipoDocumento', displayName: 'Documento'});
            $scope.gridOptions.columnDefs.push({field: 'numeroDocumento', displayName: 'Numero'});
            $scope.gridOptions.columnDefs.push({field: 'razonSocial', displayName: 'Razon social'});
            $scope.gridOptions.columnDefs.push({field: 'nombreComercial', displayName: 'Nombre comercial'});
            $scope.gridOptions.columnDefs.push({field: 'tipoEmpresa', displayName: 'Tipo Empresa'});

            $scope.moveEditColum = function(){
                if(!angular.isUndefined($scope.gridOptions.columnDefs[0].name == 'edit')){
                    $scope.gridOptions.columnDefs.push($scope.gridOptions.columnDefs.shift());
                }
            };
            $scope.moveEditColum();

            $scope.search = function(){
                $scope.gridOptions.data = PersonaJuridica.$search($scope.filterOptions);
            };
        });


})(window, window.angular);
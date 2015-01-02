define(['../../../module'], function (module) {
    'use strict';

    module.controller('BuscarBovedaCtrl', function($scope, $state, activeProfile, Sucursal, Agencia){

        $scope.combo = {
            sucursal: undefined,
            agencia: undefined
        };
        $scope.combo.selected = {
            sucursal: undefined,
            agencia: undefined
        };
        $scope.$watch('combo.selected.sucursal', function(){
            if(angular.isDefined($scope.combo.selected.sucursal)){
                $scope.combo.agencia = $scope.combo.selected.sucursal.$getAgencias().$object;
            }
        }, true);
        $scope.loadCombo = function(){
            if(angular.isUndefined($scope.view) && activeProfile.hasRole('ORGANIZACION', ['ADMIN', 'GERENTE_GENERAL'], 'OR')){
                $scope.combo.sucursal = Sucursal.$search().$object;
            }
        };
        $scope.loadCombo();

        $scope.filterOptions = {
            filterText: undefined,
            offset: 0,
            limit: 10
        };
        $scope.gridOptions = {
            data: [],
            enableRowSelection: false,
            enableRowHeaderSelection: false,
            multiSelect: false,
            columnDefs: [
                {field: 'moneda', displayName: 'Moneda'},
                {field: 'denominacion', displayName: 'Denominacion'},
                {field: 'abierto', displayName: 'Abierto', cellFilter: 'si_no: "Abierto"'},
                {field: 'estadoMovimiento', displayName: 'Movimiento', cellFilter: 'si_no: "Congelado"'},
                {field: 'saldo', displayName: 'Saldo', cellFilter: 'currency: ""'},
                {field: 'estado', displayName: 'Estado', cellFilter: 'si_no : "Activo"'},
                {
                    name: 'edit',
                    displayName: 'Edit',
                    cellTemplate: '<div style="text-align: center; padding-top: 5px;"><button type="button" ng-click="getExternalScopes().edit(row.entity)" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-edit"></span>Editar</button></div>'
                }
            ]
        };
        $scope.gridActions = {
            edit: function(row){
                $state.go('app.organizacion.estructura.editarBoveda.resumen', {id: row.id});
            }
        };
        $scope.nuevo = function(){
            $state.go('app.organizacion.estructura.crearBoveda.datosPrincipales');
        };
        $scope.search = function(){
            if($scope.combo.selected.sucursal && $scope.combo.selected.agencia){
                $scope.gridOptions.data = Agencia.$new($scope.combo.selected.agencia.id).$getBovedas().$object;
            }
        };
        $scope.search();

    }).controller('BuscarBovedaFromAgenciaCtrl', function($scope, $state){

        $scope.filterOptions = {
            filterText: undefined,
            offset: 0,
            limit: 10
        };
        $scope.gridOptions = {
            data: [],
            enableRowSelection: false,
            enableRowHeaderSelection: false,
            multiSelect: false,
            columnDefs: [
                {field: 'moneda', displayName: 'Moneda'},
                {field: 'denominacion', displayName: 'Denominacion'},
                {field: 'abreviatura', displayName: 'Abreviatura'},
                {field: 'abierto', displayName: 'Abierto', cellFilter: 'si_no'},
                {field: 'estadoMovimiento', displayName: 'Movimiento'},
                {field: 'saldo', displayName: 'Saldo', cellFilter: 'currency: ""'},
                {field: 'estado', displayName: 'Estado'},
                {
                    name: 'edit',
                    displayName: 'Edit',
                    cellTemplate: '<div style="text-align: center; padding-top: 5px;"><button type="button" ng-click="getExternalScopes().edit(row.entity)" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-edit"></span>Editar</button></div>'
                }
            ]
        };
        $scope.gridActions = {
            edit: function(row){
                $state.go('app.organizacion.estructura.editarBoveda.resumen', {id: row.id});
            }
        };
        $scope.nuevo = function(){
            $state.go('app.organizacion.estructura.editarAgencia.crearBoveda.datosPrincipales');
        };
        $scope.search = function(){
            $scope.gridOptions.data = $scope.view.agenciaDB.$getBovedas().$object;
        };
        $scope.search();

    });
});
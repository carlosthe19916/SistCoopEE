define(['../../module'], function (module) {
    'use strict';

    module.controller('BuscarAgenciaCtrl', function($scope, $state, activeProfile, Sucursal){

        //admin, gerente general, administrador general, administrador, jefecaja, cajero
        $scope.combo = {
            sucursal: undefined
        };
        $scope.combo.selected = {
            sucursal: undefined
        };
        $scope.loadCombo = function(){
            if(activeProfile.hasRole('ORGANIZACION', ['ADMIN', 'GERENTE_GENERAL'], 'OR')){
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
                {field: 'codigo', displayName: 'Codigo'},
                {field: 'denominacion', displayName: 'Denominacion'},
                {field: 'abreviatura', displayName: 'Abreviatura'},
                {field: 'ubigeo', displayName: 'Ubigeo'},
                {field: 'estado', cellFilter: 'si_no : "activo" | uppercase', displayName: 'Estado'},
                {
                    name: 'edit',
                    displayName: 'Edit',
                    cellTemplate: '<div style="text-align: center; padding-top: 5px;"><button type="button" ng-click="getExternalScopes().edit(row.entity)" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-edit"></span>Editar</button></div>'
                }
            ]
        };
        $scope.gridActions = {
            edit: function(row){
                $state.go('app.organizacion.editarSucursal.editarAgencia', {id: row.id});
            }
        };
        $scope.nuevo = function(){
            $state.go('app.organizacion.estructura.crearAgencia.datosPrincipales');
        };
        $scope.search = function(){
            if($scope.combo.selected.sucursal)
                $scope.gridOptions.data = $scope.combo.selected.sucursal.$getAgencias().$object;
        };
        $scope.search();

    }).controller('BuscarAgenciaFromSucursalCtrl', function($scope, $state){

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
                {field: 'codigo', displayName: 'Codigo'},
                {field: 'denominacion', displayName: 'Denominacion'},
                {field: 'abreviatura', displayName: 'Abreviatura'},
                {field: 'ubigeo', displayName: 'Ubigeo'},
                {field: 'estado', cellFilter: 'si_no : "activo" | uppercase', displayName: 'Estado'},
                {
                    name: 'edit',
                    displayName: 'Edit',
                    cellTemplate: '<div style="text-align: center; padding-top: 5px;"><button type="button" ng-click="getExternalScopes().edit(row.entity)" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-edit"></span>Editar</button></div>'
                }
            ]
        };
        $scope.gridActions = {
            edit: function(row){
                $state.go('app.organizacion.estructura.editarAgencia', {id: row.id});
            }
        };
        $scope.nuevo = function(){
            $state.go('app.organizacion.estructura.crearAgencia.datosPrincipales');
        };
        $scope.search = function(){
            $scope.gridOptions.data = $scope.view.sucursalDB.$getAgencias().$object;
        };
        $scope.search();

    });
});
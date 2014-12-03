define(['module'], function (module) {
    'use strict';

    module.controller('BuscarBovedaCtrl', function($scope, $state, activeProfile, Sucursal){

        $scope.combo = {
            sucursal: undefined,
            agencia: undefined
        };
        $scope.combo.selected = {
            sucursal: undefined,
            agencia: undefined
        };
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
                {field: 'codigo', displayName: 'Codigo'},
                {field: 'denominacion', displayName: 'Denominacion'},
                {field: 'abreviatura', displayName: 'Abreviatura'},
                {field: 'ubigeo', displayName: 'Ubigeo'},
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
                $state.go('app.organizacion.editarSucursal.editarAgencia', {id: row.id});
            }
        };
        $scope.nuevo = function(){
            $state.go('app.organizacion.editarSucursal.crearAgencia');
        };
        $scope.search = function(){
            if($scope.view){
                $scope.gridOptions.data = $scope.view.sucursalDB.$getAgencias().$object;
            } else {
                if($scope.combo.selected.sucursal)
                    $scope.gridOptions.data = $scope.combo.selected.sucursal.$getAgencias().$object;
            }
        };
        $scope.search();

    });
});
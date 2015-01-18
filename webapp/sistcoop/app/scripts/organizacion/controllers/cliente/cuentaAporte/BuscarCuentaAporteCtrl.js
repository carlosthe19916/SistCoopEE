define(['../../module'], function (module) {
    'use strict';

    module.controller('BuscarCuentaAporteCtrl', function($scope, $state, Sucursal){

        $scope.nuevo = function(){
            $state.go('app.organizacion.estructura.crearSucursal.datosPrincipales');
        };

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
                {field: 'abreviatura', displayName: 'Abreviatura'},
                {field: 'denominacion', displayName: 'Denominacion'},
                {field: 'estado', cellFilter: 'si_no : "activo" | uppercase', displayName: 'Estado'},
                {
                    name: 'edit',
                    displayName: 'Edit',
                    cellTemplate: '<div style="text-align: center; padding-top: 4px;"><button type="button" ng-click="getExternalScopes().edit(row.entity)" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-edit"></span>Editar</button></div>'
                }
            ]
        };
        $scope.gridActions = {
            edit: function(row){
                $state.go('app.organizacion.estructura.editarSucursal.resumen', {id: row.id});
            }
        };

        $scope.search = function(){
            $scope.gridOptions.data = Sucursal.$search($scope.filterOptions).$object;
        };

    });
});
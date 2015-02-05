define(['../module'], function (module) {
    'use strict';

    var buscarSucursalCtrl = function($scope, $state, Sucursal){

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

        $scope.search = function(){
            $scope.gridOptions.data = Sucursal.$search($scope.filterOptions).$object;
        };

        $scope.gridActions = {
            edit: function(row){
                $state.go('^.editarSucursal.resumen', {id: row.id});
            }
        };

        $scope.nuevo = function(){
            $state.go('^.crearSucursal.datosPrincipales');
        };

    };


    module.controller('BuscarSucursalCtrl_Admin', function($injector, $scope, $state){
        $injector.invoke(buscarSucursalCtrl, this, {$scope: $scope});
    }).controller('BuscarSucursalCtrl_Gerentegeneral', function($injector, $scope, $state){
        $injector.invoke(buscarSucursalCtrl, this, {$scope: $scope});
    });

});
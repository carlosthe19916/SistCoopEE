define(['../../../module'], function (module) {
    'use strict';

    module.controller('BuscarTrabajadorCtrl', function($scope, $state, activeProfile, Sucursal, Agencia){

        $scope.combo = {
            sucursal: undefined,
            agencia: undefined
        };
        $scope.combo.selected = {
            sucursal: undefined,
            agencia: undefined
        };
        var comboSucursalListener = $scope.$watch('combo.selected.sucursal', function(){
            if(angular.isDefined($scope.combo.selected.sucursal)){
                $scope.combo.agencia = $scope.combo.selected.sucursal.$getAgencias().$object;
            }
        }, true);
        $scope.loadCombo = function(){
            if(activeProfile.hasRole('ORGANIZACION', ['ADMIN', 'GERENTE_GENERAL'], 'OR')){
                $scope.combo.sucursal = Sucursal.$search().$object;
            } else if(activeProfile.hasRole('ORGANIZACION', ['ADMINISTRADOR_GENERAL'], 'OR')){
                $scope.combo.sucursal = [];
                $scope.combo.sucursal[0] = $scope.auth.user.sucursal;
                $scope.combo.selected.sucursal = $scope.combo.sucursal[0];
            } else if(activeProfile.hasRole('ORGANIZACION', ['ADMINISTRADOR'], 'OR')){
                comboSucursalListener();
                $scope.combo.sucursal = [];
                $scope.combo.sucursal[0] = $scope.auth.user.sucursal;
                $scope.combo.agencia = [];
                $scope.combo.agencia[0] = $scope.auth.user.agencia;
                $scope.combo.selected.sucursal = $scope.combo.sucursal[0];
                $scope.combo.selected.agencia = $scope.combo.agencia[0];
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
                if($scope.combo.selected.sucursal && $scope.combo.selected.agencia){
                    $scope.gridOptions.data = Agencia.$new($scope.combo.selected.agencia.id).$getCajas().$object;
                }
            }
        };
        $scope.search();

    });
});
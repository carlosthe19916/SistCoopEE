define(['../../../module'], function (module) {
    'use strict';

    module.controller('BuscarTrabajadorCtrl', function($scope, $state, activeProfile, Sucursal, Agencia, PersonaNatural){

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
            } else if(activeProfile.hasRole('ORGANIZACION', ['ADMINISTRADOR', 'JEFE_CAJA'], 'OR')){
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
                {field: 'tipoDocumento', displayName: 'T.Documento'},
                {field: 'numeroDocumento', displayName: 'N.Documento'},
                {field: 'persona.apellidoPaterno', displayName: 'A.Paterno'},
                {field: 'persona.apellidoMaterno', displayName: 'A.Materno'},
                {field: 'persona.nombres', displayName: 'Nombres'},
                {field: 'estado', displayName: 'Estado', cellFilter: 'si_no: "Activo"'},
                {
                    name: 'edit',
                    displayName: 'Edit',
                    cellTemplate: '<div style="text-align: center; padding-top: 5px;"><button type="button" ng-click="getExternalScopes().edit(row.entity)" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-edit"></span>Editar</button></div>'
                }
            ]
        };
        $scope.gridActions = {
            edit: function(row){
                $state.go('app.organizacion.rrhh.editarTrabajador.resumen', {id: row.id});
            }
        };
        $scope.nuevo = function(){
            $state.go('app.organizacion.rrhh.crearTrabajador.datosPrincipales');
        };
        $scope.search = function(){
            if($scope.combo.selected.sucursal && $scope.combo.selected.agencia){
                Agencia.$new($scope.combo.selected.agencia.id).$getTrabajadores($scope.filterOptions).then(function(response){
                    $scope.gridOptions.data = response;
                    angular.forEach($scope.gridOptions.data, function(row){
                        row.persona = PersonaNatural.$findByTipoNumeroDocumento(row.tipoDocumento, row.numeroDocumento).$object;
                    });
                });
            }
        };

    });
});
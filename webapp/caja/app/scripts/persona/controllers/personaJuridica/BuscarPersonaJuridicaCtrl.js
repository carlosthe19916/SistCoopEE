define(['../module'], function (module) {
    'use strict';

    module.controller('BuscarPersonaJuridicaCtrl', function($scope, $state, PersonaJuridica, Storage){

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
                    cellTemplate: '' +
                        '<div style="text-align: center; padding-top: 5px;">' +
                        '<button type="button" ng-click="getExternalScopes().edit(row.entity)" class="btn btn-info btn-xs">' +
                        '<span class="glyphicon glyphicon-edit"></span>Editar' +
                        '</button>' +
                        '</div>'
                }
            ]
        };
        $scope.gridActions = {
            edit: function(row){
                Storage.setObject(row);
                $state.go('app.administracion.editarPersonaJuridica', {id: row.id});
            }
        };

        $scope.search = function(){
            $scope.gridOptions.data = PersonaJuridica.$search($scope.filterOptions).$object;
        };

    });
});
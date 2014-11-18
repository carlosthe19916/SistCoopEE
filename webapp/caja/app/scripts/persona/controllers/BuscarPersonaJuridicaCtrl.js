/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers')
        .controller('BuscarPersonaJuridicaCtrl', function($scope, $state, PersonaJuridica, Storage){

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

            //verificar permisos de UPDATE
            $scope.removeColumn = function(){
                if(!$scope.activeProfile.hasPermission('PERSONA', 'UPDATE')){
                    $scope.gridOptions.columnDefs.splice($scope.gridOptions.columnDefs.length - 1, 1);
                }
            };
            $scope.removeColumn();

        });


})(window, window.angular);
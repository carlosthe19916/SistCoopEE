/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('organizacion.controllers')
        .controller('BuscarSucursalCtrl', function($scope, $state, Sucursal){

            $scope.nuevo = function(){
                $state.go('app.organizacion.crearSucursal');
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
                    $state.go('app.administracion.editarSucursal', {id: row.id});
                }
            };

            $scope.search = function(){
                $scope.gridOptions.data = Sucursal.$search($scope.filterOptions).$object;
            };
            $scope.search();

        });


})(window, window.angular);
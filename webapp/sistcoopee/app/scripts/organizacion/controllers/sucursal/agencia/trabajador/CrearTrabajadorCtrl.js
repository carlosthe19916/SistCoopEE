define(['../../../module'], function (module) {
    'use strict';

    module.controller('CrearTrabajadorCtrl', function($scope, $state, Usuario, Sucursal, Agencia, Trabajador, PersonaNatural, TipoDocumento, Notifications){

        $scope.view = {
            trabajador: Trabajador.$build(),
            persona: undefined
        };

        $scope.combo = {
            sucursal: undefined,
            agencia: undefined,
            usuario: undefined,

            tipoDocumento: undefined
        };
        $scope.combo.selected = {
            sucursal: undefined,
            agencia: undefined,
            usuario: undefined,

            tipoDocumento: undefined
        };
        $scope.$watch('combo.selected.sucursal', function(){
            if(angular.isDefined($scope.combo.selected.sucursal)){
                $scope.combo.agencia = $scope.combo.selected.sucursal.$getAgencias().$object;
            }
        }, true);
        $scope.loadCombo = function(){
            $scope.combo.sucursal = Sucursal.$search().$object;
            $scope.combo.tipoDocumento = TipoDocumento.$search({tipoPersona: 'natural'}).$object
        };
        $scope.loadCombo();

        $scope.submit = function(){
            if ($scope.form.$valid) {
                $scope.blockControl();
                $scope.view.trabajador.$save().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Trabajador creado.");
                        $state.go('app.organizacion.rrhh.editarTrabajador.resumen', {id: response.id});
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            }
        };

        $scope.check = function($event){
            if(!angular.isUndefined($event))
                $event.preventDefault();
            if(!angular.isUndefined($scope.combo.selected.tipoDocumento) && !angular.isUndefined($scope.view.trabajador.numeroDocumento)){
                PersonaNatural.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.trabajador.numeroDocumento).then(function(data){
                    if(!data){
                        Notifications.warn("Persona no encontrada.");
                        $scope.view.persona = undefined;
                    } else {
                        $scope.view.persona = data;
                    }
                });
            }
        };

        $scope.refreshComboUsuario = function(filterText){
            var queryParams = {
                search: filterText,
                first: 0,
                max: 5
            };
            $scope.combo.usuario = Usuario.$search(queryParams).$object;
        };

    });
});
       
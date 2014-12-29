define(['../../module'], function (module) {
    'use strict';

    module.controller('CrearAgenciaCtrl', function($scope, $state, Sucursal, activeProfile, Notifications){

        $scope.view = {
            sucursal: undefined,
            agencia: undefined
        };

        $scope.combo = {
            sucursal: undefined
        };
        $scope.combo.selected = {
            sucursal: undefined
        };
        $scope.loadCombo = function(){
            if(angular.isUndefined($scope.view.sucursal)){
                $scope.combo.sucursal = Sucursal.$search().$object;
            }
        };
        $scope.loadCombo();

        $scope.addAgenciaFromSucursal = function(){
            if($scope.form.$valid){
                $scope.$parent.view.sucursalDB.$addAgencia($scope.view.agencia).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Agencia creada");
                        $state.go('app.organizacion.editarSucursal.editarAgencia', {id: response.id});
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            }
        };

        $scope.addAgencia = function(){
            if($scope.form.$valid){
                $scope.combo.selected.sucursal.$addAgencia($scope.view.agencia).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Agencia creada");
                        $state.go('app.organizacion.estructura.editarAgencia.resumen', {id: response.id});
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            } else {
                $scope.form.$setSubmitted();
            }
        };

    }).controller('CrearAgenciaFromSucursalCtrl', function($scope, $state, Sucursal, activeProfile, Notifications){

        $scope.view = {
            sucursal: $scope.$parent.view.sucursalDB,
            agencia: undefined
        };

        $scope.addAgencia = function(){
            if($scope.form.$valid){
                $scope.view.sucursal.$addAgencia($scope.view.agencia).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Agencia creada");
                        $state.go('^.^.resumen');
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            }
        };

    });
});

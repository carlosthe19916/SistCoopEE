define(['../../../module'], function (module) {
    'use strict';

    module.controller('TrabajadorAccesoAlSistemaCtrl', function($scope, Usuario, Notifications, Dialog){

        $scope.combo = {
            usuario: undefined
        };
        $scope.combo.selected = {
            usuario: undefined
        };

        $scope.refreshComboUsuario = function(filterText){
            var queryParams = {
                search: filterText,
                first: 0,
                max: 5
            };
            $scope.combo.usuario = Usuario.$search(queryParams).$object;
        };

        $scope.desvincular = function(){
            Dialog.confirm('Desvincular', 'Estas seguro de quitar el usuario para el trabajador?', function() {
                $scope.blockControl();
                $scope.view.trabajador.usuario = undefined;
                $scope.view.trabajador.$save().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Trabajador actualizado.");
                        $scope.combo.selected.usuario = undefined;
                        $scope.view.trabajadorDB = angular.copy($scope.view.trabajador);
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            });
        };

        $scope.setUsuario = function(){
            if ($scope.form.$valid) {
                $scope.blockControl();
                $scope.view.trabajador.usuario = $scope.combo.selected.usuario.username;
                $scope.view.trabajador.$save().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Trabajador actualizado.");
                        $scope.view.trabajadorDB = angular.copy($scope.view.trabajador);
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
       
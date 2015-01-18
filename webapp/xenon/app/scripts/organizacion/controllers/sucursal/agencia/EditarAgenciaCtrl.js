define(['../../module'], function (module) {
    'use strict';

    module.controller('EditarAgenciaCtrl', function($scope, $state, Notifications, Dialog){

        $scope.view = {
            agencia: undefined,
            agenciaDB: undefined
        };

        $scope.loadParams = function(){
            $scope.view.agenciaDB = $scope.params.object;
            $scope.view.agencia = angular.copy($scope.view.agenciaDB);
        };
        $scope.loadParams();

        $scope.submit = function(){
            if ($scope.form.$valid) {
                $scope.blockControl();
                $scope.view.agencia.$save().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Agencia actualizada");
                        $scope.view.agenciaDB = angular.copy($scope.view.agencia);
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            }
        };

        $scope.desactivar = function(){
            Dialog.confirmDelete($scope.view.agenciaDB.denominacion, 'agencia', function() {
                $scope.blockControl();
                $scope.view.agenciaDB.$desactivar().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Agencia desactivada");
                        $state.go('app.organizacion.estructura.buscarAgencia');
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            });
        };

    });
});


define(['../../../module'], function (module) {
    'use strict';

    module.controller('EditarCajaCtrl', function($scope, $state, Notifications, Dialog){

        $scope.view = {
            boveda: undefined,
            bovedaDB: undefined
        };

        $scope.loadParams = function(){
            $scope.view.bovedaDB = $scope.params.object;
            $scope.view.bovedaDB.cajas = $scope.view.bovedaDB.$getCajas().$object;
            $scope.view.boveda = angular.copy($scope.view.bovedaDB);
        };
        $scope.loadParams();

        $scope.submit = function(){
            if ($scope.form.$valid) {
                $scope.blockControl();
                $scope.view.boveda.$abrir().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Boveda actualizada");
                        $scope.view.bovedaDB = angular.copy($scope.view.boveda);
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            }
        };

        $scope.desactivar = function(){
            Dialog.confirmDelete($scope.view.bovedaDB.denominacion, 'boveda', function() {
                $scope.blockControl();
                $scope.view.bovedaDB.$desactivar().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Boveda desactivada");
                        $state.go('app.organizacion.estructura.buscarBoveda');
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


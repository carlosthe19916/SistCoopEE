define(['../../../module'], function (module) {
    'use strict';

    module.controller('CajaBovedasCtrl', function($scope, $state, Notifications, Dialog){

        $scope.addBoveda = function(){
            if(angular.isUndefined($scope.combo.selected.boveda)){
                return;
            }
            if(angular.isDefined($scope.view.cajaDB.bovedas)){
                for(var i=0; i<$scope.view.cajaDB.bovedas.length; i++){
                    if($scope.view.cajaDB.bovedas[i].id == $scope.combo.selected.boveda.id){
                        Notifications.warn('Boveda ya fue asignada.');
                        return;
                    }
                }
            }
            Dialog.confirm('Vincular', '¿Estas seguro de vincular la caja para la boveda?', function() {
                $scope.blockControl();
                $scope.view.caja.$addBoveda($scope.combo.selected.boveda).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Trabajador actualizado.");

                        if(angular.isDefined($scope.view.caja.bovedas)){
                            $scope.view.caja.bovedas.push($scope.combo.selected.boveda);
                        } else {
                            $scope.view.caja.bovedas = [];
                            $scope.view.caja.bovedas.push($scope.combo.selected.boveda);
                        }

                        $scope.combo.selected.boveda = undefined;

                        $scope.view.cajaDB = angular.copy($scope.view.caja);
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            });
        };

        $scope.removeBoveda = function($index, item){
            Dialog.confirm('Eliminar', '¿Estas seguro de desvincular la boveda para la caja?. Debes de asegurarte que no existe saldo en caja en la moneda de la boveda para continuar.', function() {
                $scope.blockControl();
                $scope.view.caja.$removeBoveda(item.id).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Trabajador actualizado.");

                        $scope.view.cajaDB.bovedas.splice($index, 1);
                        $scope.view.caja = angular.copy($scope.view.cajaDB);
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
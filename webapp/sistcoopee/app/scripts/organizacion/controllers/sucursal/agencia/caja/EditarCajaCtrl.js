define(['../../../module'], function (module) {
    'use strict';

    module.controller('EditarCajaCtrl', function($scope, $state, Agencia, Notifications, Dialog){

        $scope.view = {
            caja: undefined,
            cajaDB: undefined
        };

        $scope.loadParams = function(){
            $scope.view.cajaDB = $scope.params.object;
            $scope.view.cajaDB.bovedas = $scope.view.cajaDB.$getBovedas().$object;
            $scope.view.cajaDB.trabajadores = $scope.view.cajaDB.$getTrabajadores().$object;
            $scope.view.caja = angular.copy($scope.view.cajaDB);
        };
        $scope.loadParams();

        $scope.combo = {
            boveda: undefined
        };
        $scope.combo.selected = {
            boveda: []
        };
        $scope.loadCombo = function() {
            $scope.combo.boveda = Agencia.$new($scope.view.cajaDB.agencia.id).$getBovedas().$object;
        };
        $scope.loadCombo();

        $scope.submit = function(){
            if ($scope.form.$valid) {
                $scope.blockControl();
                $scope.view.caja.$save().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Caja actualizada");
                        $scope.view.cajaDB = angular.copy($scope.view.caja);
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            }
        };

        $scope.desactivar = function(){
            Dialog.confirmDelete($scope.view.cajaDB.denominacion, 'caja', function() {
                $scope.blockControl();
                $scope.view.cajaDB.$desactivar().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Caja desactivada");
                        $state.go('app.organizacion.estructura.buscarCaja');
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


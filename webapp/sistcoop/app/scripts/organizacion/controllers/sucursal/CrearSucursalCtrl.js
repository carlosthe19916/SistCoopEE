define(['../module'], function (module) {
    'use strict';

    var crearSucursalCtrl = function($scope, $state, Sucursal, Notifications){

        $scope.view = {
            sucursal: Sucursal.$build()
        };

        $scope.submit = function(){
            if ($scope.form.$valid) {
                $scope.blockControl();
                $scope.view.sucursal.$save().then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Sucursal creada.");
                        $state.go('^.^.editarSucursal.resumen', {id: response.id});
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            }
        };

    };

    module.controller('CrearSucursalCtrl_Admin', function($injector, $scope, $state, Sucursal, Notifications){
        $injector.invoke(crearSucursalCtrl, this, {$scope: $scope});
    }).controller('CrearSucursalCtrl_Gerentegeneral', function($injector, $scope, $state, Sucursal, Notifications){
        $injector.invoke(crearSucursalCtrl, this, {$scope: $scope});
    });
});
define(['../../module'], function (module) {
    'use strict';

    module.controller('CrearAgenciaCtrl', function($scope, $state, Sucursal, Notifications){

        $scope.view = {
            agencia: undefined
        };

        $scope.addAgencia = function(){
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

    });
});

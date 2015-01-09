define(['../../../module'], function (module) {
    'use strict';

    module.controller('CrearTrabajadorCtrl', function($scope, $state, Trabajador, Notifications){

        $scope.view = {
            trabajador: Trabajador.$build()
        };

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

    });
});
       
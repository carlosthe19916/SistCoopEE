/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('organizacion.controllers')
        .controller('CrearAgenciaCtrl', function($scope, $state, Sucursal, Notifications){

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


})(window, window.angular);
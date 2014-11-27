/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('organizacion.controllers')
        .controller('CrearSucursalCtrl', function($scope, $state, Sucursal, Notifications){

            $scope.view = {
                sucursal: Sucursal.$build()
            };

            $scope.submit = function(){
                if ($scope.form.$valid) {
                    $scope.blockControl();
                    $scope.view.sucursal.$save().then(
                        function(response){
                            $scope.unblockControl();
                            Notifications.success("Sucursal creada");
                            $state.go('app.organizacion.editarSucursal', {id: response.id});
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
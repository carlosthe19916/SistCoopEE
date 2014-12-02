
/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('organizacion.controllers')

        .controller('EditarAgenciaCtrl', function($scope, $state, Notifications){

            $scope.view = {
                sucursal: undefined,
                sucursalDB: undefined
            };

            $scope.loadParams = function(){
                $scope.view.sucursalDB = $scope.params.object;
                $scope.view.sucursal = angular.copy($scope.view.sucursalDB);
            };
            $scope.loadParams();

            $scope.submit = function(){
                if ($scope.form.$valid) {
                    $scope.blockControl();
                    $scope.view.sucursal.$save().then(
                        function(response){
                            $scope.unblockControl();
                            Notifications.success("Sucursal actualizada");
                            $scope.view.sucursalDB = angular.copy($scope.view.sucursal);
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


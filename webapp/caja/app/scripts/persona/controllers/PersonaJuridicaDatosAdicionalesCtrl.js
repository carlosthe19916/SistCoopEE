/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers')
        .controller('DatosAdicionalesCtrl', function($scope, $state){

            $scope.form.$setPristine();
            $scope.view.persona = angular.copy($scope.view.personaDB);

            $scope.goTabRepresentante = function(){
                if($scope.form.$valid){
                    $scope.combo.synchronize();
                    $state.go('app.administracion.crearPersonaJuridica.representante');
                } else {
                    $scope.form.$setSubmitted();
                }
            };
            $scope.cancelar = function(){
                $state.go('app.administracion.buscarPersonaJuridica');
            };
        });

})(window, window.angular);


        
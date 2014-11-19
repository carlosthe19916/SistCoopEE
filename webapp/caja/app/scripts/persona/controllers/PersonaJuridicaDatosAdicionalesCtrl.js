/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers')
        .controller('PersonaJuridicaDatosAdicionalesCtrl', function($scope, $state){

            $scope.goTabRepresentante = function(){
                if($scope.form.$valid){
                    $state.go('app.administracion.crearPersonaJuridica.representante');
                } else {
                    $scope.form.$setSubmitted();
                }
            };

        });

})(window, window.angular);


        
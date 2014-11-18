/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers')
        .controller('PersonaJuridicaResumenCtrl', function($scope, $state){
            $scope.verPersona = function(item){
                $state.go('app.administracion.editarPersonaNatural.resumen', {id: item.id});
            };
        });

})(window, window.angular);



/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers')

        .controller('PersonaNaturalDatosAdicionalesCtrl', function($scope, $state, Storage, Pais, Sexo, EstadoCivil, PersonaNatural, TipoDocumento, Notifications){

            $scope.refreshPage = function(){
                $scope.form.$setPristine();
            };
            $scope.refreshPage();

        });

})(window, window.angular);
       
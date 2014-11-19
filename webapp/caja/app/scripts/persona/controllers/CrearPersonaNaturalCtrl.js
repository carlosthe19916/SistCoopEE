/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers')
        .controller('CrearPersonaNaturalCtrl', function($scope, $state, Pais, Sexo, EstadoCivil, PersonaNatural, TipoDocumento, Notifications){

            $scope.view = {
                persona: PersonaNatural.$build()
            };

            $scope.loadParams = function(){
                $scope.view.persona.tipoDocumento = $scope.params.tipoDocumento;
                $scope.view.persona.numeroDocumento = $scope.params.numeroDocumento;
            };
            $scope.loadParams();

            $scope.submit = function(){
                if ($scope.form.$valid) {
                    $scope.blockControl();
                    var save = function(){
                        $scope.view.persona.$save().then(
                            function(response){
                                $scope.unblockControl();
                                Notifications.success("Persona creada");
                                $state.go('app.administracion.editarPersonaNatural', {id: response.data.id});
                            },
                            function error(error){
                                $scope.unblockControl();
                                Notifications.error(error.data+".");
                            }
                        );
                    };
                    PersonaNatural.$findByTipoNumeroDocumento($scope.view.persona.tipoDocumento, $scope.view.persona.numeroDocumento).then(function(data){
                        if(data) {
                            Notifications.error("Documento de identidad no disponible.");
                            $scope.unblockControl();
                        } else {
                            save();
                        }
                    });
                }
            };

        });

})(window, window.angular);
       
define(['../module'], function (module) {
    'use strict';

    module.controller('PersonaJuridicaDatosAdicionalesCtrl', function($scope, $state){

        $scope.goTabRepresentante = function(){
            if($scope.form.$valid){
                $state.go('app.administracion.crearPersonaJuridica.representante');
            } else {
                $scope.form.$setSubmitted();
            }
        };

    });
});



        
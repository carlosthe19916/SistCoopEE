define(['module'], function (module) {
    'use strict';

    module.controller('PersonaNaturalDatosAdicionalesCtrl', function($scope, $state, Storage, Pais, Sexo, EstadoCivil, PersonaNatural, TipoDocumento, Notifications){

        $scope.refreshPage = function(){
            $scope.form.$setPristine();
        };
        $scope.refreshPage();

    });
});
       
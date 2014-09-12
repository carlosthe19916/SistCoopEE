define(['../../module'], function (controllers) {
    'use strict';
    controllers.controller('CrearPersonaNaturalController', ['$scope','$state','Country',
        function($scope,$state,Country) {

            $scope.cuntries = Country.$search();

            $scope.control = {
                success:false,
                inProcess: false,
                submitted : false
            };

            $scope.combo = {
                paises: undefined,
                tipoDocumentos: undefined,
                sexos: undefined,
                estadosCiviles: undefined
            };

            $scope.view = {
                id: undefined,
                idTipoDocumento: -1,
                numeroDocumento: undefined,
                apellidoPaterno: undefined,
                apellidoMaterno: undefined,
                nombres: undefined,
                fechaNacimiento: undefined,
                sexo: undefined,
                estadoCivil: undefined,
                ocupacion: undefined,
                direccion: undefined,
                referencia: undefined,
                telefono: undefined,
                celular: undefined,
                email: undefined,
                ubigeo: undefined,
                codigoPais: undefined
            };

            $scope.dateOptions = {
                formatYear: 'yyyy',
                startingDay: 1
            };

            $scope.open = function($event) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.opened = true;
            };

            $scope.loadParametros = function(){
                $scope.view.numeroDocumento = $scope.params.numeroDocumento;
                $scope.view.idTipoDocumento = parseInt($scope.params.idTipoDocumento);
            };


        }]);
});
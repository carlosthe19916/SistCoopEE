
/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers')

        .controller('EditarPersonaNaturalController', function($scope, $state, $modal, Pais, Sexo, EstadoCivil, PersonaNatural, TipoDocumento, Notifications){

            $scope.openModal = function (size) {
                var modalInstance = $modal.open({
                    templateUrl: '../../views/persona/natural/subirFoto.html',
                    //controller: 'ModalInstanceCtrl',
                    size: 'lg',
                    resolve: {
                        items: function () {
                            return [];
                        }
                    }
                });
                modalInstance.result.then(function (selectedItem) {
                    $scope.selected = selectedItem;
                }, function () {
                });
            };

            $scope.view = {
                personaNatural: undefined,
                personaNaturalDB: undefined
            };
            $scope.combo = {
                pais: Pais.$search().$object,
                tipoDocumento: TipoDocumento.$search({tipoPersona: 'natural'}).$object,
                sexo: Sexo.$search().$object,
                estadoCivil: EstadoCivil.$search().$object
            };
            $scope.combo.selected = {
                pais: undefined,
                tipoDocumento: undefined,
                sexo: undefined,
                estadoCivil: undefined
            };

            $scope.loadParams = function(){
                $scope.view.personaNatural = $scope.params.object;
                $scope.view.personaNaturalDB = angular.copy($scope.params.object);

                var comboPaisListener = $scope.$watch('combo.pais', function(newValue, oldValue) {
                    if($scope.combo.pais.length){
                        for(var i=0;i<$scope.combo.pais.length;i++){
                            if($scope.combo.pais[i].alpha3Code == $scope.params.object.codigoPais){
                                $scope.combo.selected.pais = $scope.combo.pais[i];
                                comboPaisListener();
                                break;
                            }
                        }
                    }
                }, true);

                var comboTipoDocumentoListener = $scope.$watch('combo.tipoDocumento', function(newValue, oldValue) {
                    if($scope.combo.tipoDocumento.length){
                        for(var i=0;i<$scope.combo.tipoDocumento.length;i++){
                            if($scope.combo.tipoDocumento[i].abreviatura == $scope.params.object.tipoDocumento){
                                $scope.combo.selected.tipoDocumento = $scope.combo.tipoDocumento[i];
                                comboTipoDocumentoListener();
                                break;
                            }
                        }
                    }
                }, true);

                var comboSexoListener = $scope.$watch('combo.sexo', function(newValue, oldValue) {
                    if($scope.combo.sexo.length){
                        for(var i=0;i<$scope.combo.sexo.length;i++){
                            if($scope.combo.sexo[i].denominacion == $scope.params.object.sexo){
                                $scope.combo.selected.sexo = $scope.combo.sexo[i];
                                comboSexoListener();
                                break;
                            }
                        }
                    }
                }, true);

                var comboEstadoCivilListener = $scope.$watch('combo.estadoCivil', function(newValue, oldValue) {
                    if($scope.combo.estadoCivil.length){
                        for(var i=0;i<$scope.combo.estadoCivil.length;i++){
                            if($scope.combo.estadoCivil[i].denominacion == $scope.params.object.estadoCivil){
                                $scope.combo.selected.estadoCivil = $scope.combo.estadoCivil[i];
                                comboEstadoCivilListener();
                                break;
                            }
                        }
                    }
                }, true);
            };
            $scope.loadParams();

            $scope.submit = function(){
                if ($scope.form.$valid) {
                    $scope.blockControl();
                    var save = function(){
                        $scope.view.personaNatural.codigoPais = $scope.combo.selected.pais ? $scope.combo.selected.pais.alpha3Code: null;
                        $scope.view.personaNatural.tipoDocumento = $scope.combo.selected.tipoDocumento ? $scope.combo.selected.tipoDocumento.abreviatura: null;
                        $scope.view.personaNatural.sexo = $scope.combo.selected.sexo ? $scope.combo.selected.sexo.denominacion : null;
                        $scope.view.personaNatural.estadoCivil = $scope.combo.selected.estadoCivil ? $scope.combo.selected.estadoCivil.denominacion : null;
                        $scope.view.personaNatural.$save().then(
                            function(data){
                                $scope.unblockControl();
                                Notifications.success("Persona actualizada");
                                $scope.view.personaNaturalDB = angular.copy($scope.view.personaNatural);
                            },
                            function error(error){
                                $scope.unblockControl();
                                Notifications.error(error.data+".");
                            }
                        );
                    };
                    PersonaNatural.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.personaNatural.numeroDocumento).then(function(data){
                        if(data && data.id != $scope.view.personaNatural.id){
                            Notifications.error("Documento de identidad no disponible.");
                            $scope.unblockControl();
                        }
                        else {
                            save();
                        }
                    });
                }
            };

            $scope.check = function($event){
                if(!angular.isUndefined($event))
                    $event.preventDefault();
                if(!angular.isUndefined($scope.combo.selected.tipoDocumento) && !angular.isUndefined($scope.view.personaNatural.numeroDocumento)){
                    PersonaNatural.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.personaNatural.numeroDocumento).then(function(data){
                        if(data && data.id == $scope.view.personaNatural.id){
                            Notifications.info("Documento de identidad disponible.");
                        }
                        else {
                            Notifications.warn("Documento de identidad no disponible.");
                        }
                    });
                }
            };

        });

})(window, window.angular);


/**
 * @license AngularJS v1.3.1-build.3436+sha.47e15aa
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    angular.module('persona.controllers', [])
        .controller('CrearPersonaNaturalController', function($scope, $state, Storage, Pais, Sexo, EstadoCivil, PersonaNatural, TipoDocumento, Notifications){
            $scope.view = {
                personaNatural: PersonaNatural.$build()
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
                $scope.view.personaNatural.tipoDocumento = $scope.params.tipoDocumento;
                $scope.view.personaNatural.numeroDocumento = $scope.params.numeroDocumento;
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
                                Notifications.success("Persona creada");
                                PersonaNatural.$url(data.headers('Location')).then(function(data){
                                    Storage.setObject(data);
                                    $state.go('app.administracion.editarPersonaNatural', {id: data.id});
                                });
                            },
                            function error(error){
                                $scope.unblockControl();
                                Notifications.error(error.data+".");
                            }
                        );
                    };
                    PersonaNatural.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.personaNatural.numeroDocumento).then(function(data){
                        if(data) {
                            Notifications.error("Documento de identidad no disponible.");
                            $scope.unblockControl();
                        } else {
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
                        if(!data)
                            Notifications.info("Documento de identidad disponible.");
                        else
                            Notifications.warn("Documento de identidad no disponible.");
                    });
                }
            };

            $scope.cancelar = function(){
                $state.go('app.administracion.buscarPersonaNatural');
            };

        })
        .controller('CrearPersonaJuridicaCtrl', function($scope, $state, PersonaJuridica, Notifications, Navigation, Storage){
            $scope.view = {
                persona: PersonaJuridica.$build(),
                representante: undefined
            };

            $scope.loadParams = function(){
                $scope.view.persona.tipoDocumento = $scope.params.tipoDocumento;
                $scope.view.persona.numeroDocumento = $scope.params.numeroDocumento;
                if($scope.params.object) $scope.view = $scope.params.object;
            };
            $scope.loadParams();

            $scope.submit = function(){
                if(angular.isUndefined($scope.view.persona.representanteLegal.id)){
                    Notifications.warn('Debe indicar al representante legal.');
                }
                if ($scope.form.$valid && angular.isDefined($scope.view.persona.representanteLegal.id)) {
                    $scope.blockControl();
                    var save = function(){
                        $scope.view.persona.representanteLegal = {
                            tipoDocumento: $scope.view.persona.representanteLegal.tipoDocumento,
                            numeroDocumento: $scope.view.persona.representanteLegal.numeroDocumento
                        };
                        $scope.view.persona.$save().then(
                            function(data){
                                $scope.unblockControl();
                                Notifications.success("Persona creada");
                                PersonaJuridica.$url(data.headers('Location')).then(function(data){
                                    Storage.setObject(data);
                                    $state.go('app.administracion.editarPersonaJuridica', {id: data.id});
                                });
                            },
                            function error(error){
                                $scope.unblockControl();
                                Notifications.error(error.data+".");
                            }
                        );
                    };
                    PersonaJuridica.$findByTipoNumeroDocumento($scope.view.persona.tipoDocumento, $scope.view.persona.numeroDocumento).then(function(data){
                        if(data) {
                            Notifications.error("Documento de identidad no disponible.");
                            $scope.unblockControl();
                        } else {
                            save();
                        }
                    });
                }
            };

        })
        .controller('DatosPrincipalesCtrl', function($scope, $state, Pais, TipoDocumento, TipoEmpresa, PersonaJuridica, Notifications){
            $scope.combo = {
                pais: Pais.$search().$object,
                tipoDocumento: TipoDocumento.$search({tipoPersona: 'juridica'}).$object,
                tipoEmpresa: TipoEmpresa.$search().$object
            };
            $scope.combo.selected = {
                pais: undefined,
                tipoDocumento: undefined,
                tipoEmpresa: undefined
            };
            $scope.combo.synchronize = function(){
                $scope.view.persona.codigoPais = $scope.combo.selected.pais ? $scope.combo.selected.pais.alpha3Code: undefined;
                $scope.view.persona.tipoDocumento = $scope.combo.selected.tipoDocumento ? $scope.combo.selected.tipoDocumento.abreviatura: undefined;
                $scope.view.persona.tipoEmpresa = $scope.combo.selected.tipoEmpresa ? $scope.combo.selected.tipoEmpresa.denominacion : undefined;
            };

            $scope.checkPersona = function($event){
                if(!angular.isUndefined($event))
                    $event.preventDefault();
                if(!angular.isUndefined($scope.combo.selected.tipoDocumento)
                    && !angular.isUndefined($scope.view.persona.numeroDocumento)){
                    PersonaJuridica.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.persona.numeroDocumento).then(function(data){
                        if(!data)
                            Notifications.info("Documento de identidad disponible.");
                        else
                            Notifications.warn("Documento de identidad no disponible.");
                    });
                }
            };

            $scope.goTabRepresentante = function(){
                if($scope.form.$valid){
                    $scope.combo.synchronize();
                    $scope.form.$setPristine();
                    $state.go('app.administracion.crearPersonaJuridica.representante');
                } else {
                    $scope.form.$setSubmitted();
                }
            };

            $scope.cancelar = function(){
                $state.go('app.administracion.buscarPersonaJuridica');
            };
        })
        .controller('RepresentanteLegalCtrl', function($scope, $state, TipoDocumento, PersonaNatural, Notifications, Navigation){
            $scope.combo = {
                tipoDocumento: TipoDocumento.$search({tipoPersona: 'natural'}).$object
            };
            $scope.combo.selected = {
                tipoDocumento: undefined,
            };
            $scope.combo.synchronize = function(){
                $scope.view.persona.representanteLegal.tipoDocumento = $scope.combo.selected.tipoDocumento ? $scope.combo.selected.tipoDocumento.abreviatura: undefined;
            };
            $scope.setRepresentante = function($event){
                if(!angular.isUndefined($event))
                    $event.preventDefault();
                if(!angular.isUndefined($scope.combo.selected.tipoDocumento)
                    && !angular.isUndefined($scope.view.representante.numeroDocumento)){
                    PersonaNatural.$findByTipoNumeroDocumento($scope.combo.selected.tipoDocumento.abreviatura, $scope.view.representante.numeroDocumento).then(function(data){
                        if(data)
                            $scope.view.persona.representanteLegal = data;
                        else
                            Notifications.warn("Persona no encontrada.");
                    });
                }
            };

            $scope.goTabPrincipal = function(){
                $state.go('app.administracion.crearPersonaJuridica.principal');
            };
            $scope.goCrearPersonaNatural = function(){
                $scope.combo.synchronize();
                Navigation.addState({name: 'Crear persona juridica', state: 'app.administracion.crearPersonaJuridica.representante', object: $scope.view});
                $state.go('app.administracion.crearPersonaNatural');
            };
        })
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

        })
        .controller('EditarPersonaJuridicaController', function($scope, $state, $modal, Pais, Sexo, EstadoCivil, PersonaNatural, TipoDocumento, Notifications){

            $scope.view = {
                persona: undefined,
                personaDB: undefined
            };

            $scope.loadParams = function(){
                $scope.view.persona = $scope.params.object;
                $scope.view.personaDB = angular.copy($scope.params.object);
            };
            $scope.loadParams();

        })
        .controller('BuscarController', function($scope){
            $scope.filterOptions = {
                filterText: undefined,
                offset: 0,
                limit: 10
            };
        })
        .controller('BuscarPersonaNaturalController', function($scope, $state, Storage, PersonaNatural){
            $scope.nuevo = function(){
                $state.go('app.administracion.crearPersonaNatural');
            };
            $scope.gridOptions = {
                data: [],
                enableRowSelection: false,
                enableRowHeaderSelection: false,
                multiSelect: false,
                columnDefs: [
                    {field: 'tipoDocumento', displayName: 'Documento'},
                    {field: 'numeroDocumento', displayName: 'Numero'},
                    {field: 'apellidoPaterno', displayName: 'Ap.paterno'},
                    {field: 'apellidoMaterno', displayName: 'Ap.materno'},
                    {field: 'nombres', displayName: 'Nombres'},
                    {field: 'sexo', displayName: 'Sexo'},
                    {
                        name: 'edit',
                        displayName: 'Edit',
                        cellTemplate: '<div style="text-align: center; padding-top: 5px;"><button type="button" ng-click="getExternalScopes().edit(row.entity)" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-edit"></span>Editar</button></div>'
                    }
                ]
            };
            $scope.gridActions = {
                edit: function(row){
                    Storage.setObject(row);
                    $state.go('app.administracion.editarPersonaNatural', {id: row.id});
                }
            };

            $scope.removeColumn = function(){
                if(!$scope.activeProfile.hasPermission('PERSONA', 'UPDATE')){
                    $scope.gridOptions.columnDefs.splice($scope.gridOptions.columnDefs.length - 1, 1);
                }
            };
            $scope.removeColumn();

            $scope.search = function(){
                $scope.gridOptions.data = PersonaNatural.$search($scope.filterOptions).$object;
            };
        })
        .controller('BuscarPersonaJuridicaController', function($scope, $state, Storage, PersonaJuridica){
            $scope.nuevo = function(){
                $state.go('app.administracion.crearPersonaJuridica');
            };
            $scope.gridOptions = {
                data: [],
                enableRowSelection: false,
                enableRowHeaderSelection: false,
                multiSelect: false,
                columnDefs: [
                    {field: 'tipoDocumento', displayName: 'Documento'},
                    {field: 'numeroDocumento', displayName: 'Numero'},
                    {field: 'razonSocial', displayName: 'Razon social'},
                    {field: 'nombreComercial', displayName: 'Nombre comercial'},
                    {field: 'tipoEmpresa', displayName: 'Tipo Empresa'},
                    {
                        name: 'edit',
                        displayName: 'Edit',
                        cellTemplate: '<div style="text-align: center; padding-top: 5px;"><button type="button" ng-click="getExternalScopes().edit(row.entity)" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-edit"></span>Editar</button></div>'
                    }
                ]
            };
            $scope.gridActions = {
                edit: function(row){
                    Storage.setObject(row);
                    $state.go('app.administracion.editarPersonaJuridica', {id: row.id});
                }
            };

            $scope.removeColumn = function(){
                if(!$scope.activeProfile.hasPermission('PERSONA', 'UPDATE')){
                    $scope.gridOptions.columnDefs.splice($scope.gridOptions.columnDefs.length - 1, 1);
                }
            };
            $scope.removeColumn();

            $scope.search = function(){
                $scope.gridOptions.data = PersonaJuridica.$search($scope.filterOptions).$object;
            };
        });


})(window, window.angular);
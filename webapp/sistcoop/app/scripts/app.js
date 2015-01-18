/*jshint unused: vars */
define([
        'angular',
        './xenon/main',
        './common/main',
        './organizacion/main',
        './persona/main',
        './ubigeo/main'
    ]/*deps*/,
    function (angular){

        'use strict';

        var app = angular.module('sistcoop-app',
            [
                /*xenon*/
                'xenon',

                /*angular*/
                'ngCookies',
                'ngAria',
                'ngSanitize',
                'ngMessages',
                'ngAnimate',

                /*ui modules*/
                'ui.router',
                'ui.bootstrap',
                'ui.select',
                'ui.utils',
                'ui.grid',
                'ui.grid.edit',
                'ui.grid.selection',
                'angular-ladda',
                'restangular',
                'blockUI',
                'FBAngular',
                'oc.lazyLoad',

                /*sistcoop*/
                'persona',
                'ubigeo',
                'organizacion',
                'common'
            ]);

        var resourceRequests = 0;
        var loadingTimer = -1;


        app.constant('CONFIGURATION', {
            'restUrl': {
                'persona': 'http://localhost:8080/restapi-persona/rest/v1',
                'ubigeo': 'http://localhost:8080/restapi-ubigeo/rest/v1',
                'organizacion': 'http://localhost:8080/restapi-organizacion/rest/v1',
                'keycloak': 'https://keycloak-softgreen.rhcloud.com/auth/admin/realms/SISTCOOP'
            }
        });


        app.factory('authInterceptor', function($q, Auth) {
            return {
                request: function (config) {
                    var deferred = $q.defer();
                    if (Auth.authz.token) {
                        Auth.authz.updateToken(5).success(function() {
                            config.headers = config.headers || {};
                            config.headers.Authorization = 'Bearer ' + Auth.authz.token;

                            deferred.resolve(config);
                        }).error(function() {
                            location.reload();
                        });
                    }
                    return deferred.promise;
                }
            };
        });

        app.config(function(RestangularProvider) {
            RestangularProvider.setBaseUrl('http://localhost:8080');

            //funcion que pone los @ correspondientes antes de enviar al servidor
            var wrapper = function(obj){
                angular.forEach(obj, function(value, key) {
                    if(angular.isDate(value)){
                        this['@' + key.toString()] = (value !== null ? value: undefined);
                        delete this[key];
                    } else if (angular.isArray(value)){
                        this[key.toString()] = [];
                        for(var i = 0; i< value.length; i++){
                            this[key.toString()][i] = wrapper(value[i]);
                        }
                    } else if (angular.isObject(value)){
                        this[key.toString()] = wrapper(value);
                    } else {
                        this['@' + key.toString()] = (value !== null ? value: undefined);
                        delete this[key];
                    }
                }, obj);
                return obj;
            };

            var unWrapper = function(obj){
                angular.forEach(obj, function(value, key) {
                    if(angular.isDate(value)){
                        if(key.substring(0,1) == '@') {
                            var newKey = key.replace('@', '');
                            this[newKey.toString()] = value;
                            delete this[key];
                        }
                    } else if (angular.isArray(value)){
                        this[key.toString()] = [];
                        for(var i = 0; i< value.length; i++){
                            this[key.toString()][i] = unWrapper(value[i]);
                        }
                    } else if (angular.isObject(value)){
                        this[key.toString()] = unWrapper(value);
                    } else {
                        if(key.substring(0,1) == '@') {
                            var newKey = key.replace('@', '');
                            this[newKey.toString()] = value;
                            delete this[key];
                        }
                    }
                }, obj);
                return obj;
            };

            //añade @ a los atributos
            RestangularProvider.addFullRequestInterceptor(function(element, operation, route, url, headers, params, httpConfig) {
                if(operation == 'post' || operation == 'put'){
                    var newElement;
                    if(element){
                        newElement = wrapper(element[Object.keys(element)[0]]);
                    }
                }
            });

            //saca el primer objeto
            RestangularProvider.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
                var extractedData;
                if(data){
                    if(!angular.isArray(data)){
                        extractedData = data[Object.keys(data)[0]];
                        extractedData.meta = data.meta;
                    } else {
                        extractedData = data;
                    }
                } else {
                    extractedData = data;
                }
                return extractedData;
            });
            //saca los @ de los atributos
            RestangularProvider.setResponseExtractor(function(response) {
                var newResponse = angular.copy(response);
                if(response){
                    newResponse = unWrapper(response);
                }
                return newResponse;
            });
        });

        app.factory('PersonaRestangular', function(Restangular, CONFIGURATION) {
            return Restangular.withConfig(function(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl(CONFIGURATION.restUrl.persona);
            });
        });

        app.factory('UbigeoRestangular', function(Restangular, CONFIGURATION) {
            return Restangular.withConfig(function(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl(CONFIGURATION.restUrl.ubigeo);
            });
        });

        app.factory('OrganizacionRestangular', function(Restangular, CONFIGURATION) {
            return Restangular.withConfig(function(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl(CONFIGURATION.restUrl.organizacion);
            });
        });

        app.factory('KeycloakRestangular', function(Restangular, CONFIGURATION) {
            return Restangular.withConfig(function(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl(CONFIGURATION.restUrl.keycloak);
            });
        });

        app.config(['$provide', function($provide){
            var profile = angular.copy(window.auth.authz);

            //modulesNames = ['PERSONA', 'UBIGEO', 'ORGANIZACION'];
            //operations = ['SELECT', 'CREATE', 'UPDATE', 'DELETE'];

            var apiModules = [
                {
                    module: 'PERSONA',
                    roles: {
                        available: [
                            {rol: 'PUBLIC', permissions: ['SELECT']},
                            {rol: 'USER', permissions: ['SELECT', 'CREATE']},
                            {rol: 'ADMIN', permissions: ['SELECT', 'CREATE', 'UPDATE', 'DELETE']}
                        ],
                        assigned: profile.resourceAccess.PERSONA_RESTAPI === undefined ? [] : profile.resourceAccess.PERSONA_RESTAPI.roles
                    }
                },
                {
                    module: 'UBIGEO',
                    roles: {
                        available: [
                            {rol: 'PUBLIC', permissions: ['SELECT']},
                            {rol: 'USER', permissions: ['SELECT', 'CREATE']},
                            {rol: 'ADMIN', permissions: ['SELECT', 'CREATE', 'UPDATE', 'DELETE']}
                        ],
                        assigned: profile.resourceAccess.UBIGEO_RESTAPI === undefined ? [] : profile.resourceAccess.UBIGEO_RESTAPI.roles
                    }
                },
                {
                    module: 'ORGANIZACION',
                    roles: {
                        available: [
                            {rol: 'ADMIN', permissions: ['SELECT', 'CREATE', 'UPDATE', 'DELETE']},
                            {rol: 'ADMINISTRADOR_GENERAL', permissions: ['SELECT', 'CREATE', 'UPDATE', 'DELETE']},
                            {rol: 'ADMINISTRADOR', permissions: ['SELECT', 'CREATE', 'UPDATE', 'DELETE']},
                            {rol: 'PLATAFORMA', permissions: ['SELECT', 'CREATE', 'UPDATE']},
                            {rol: 'JEFE_CAJA', permissions: ['SELECT', 'CREATE', 'UPDATE']},
                            {rol: 'CAJERO', permissions: ['SELECT', 'CREATE', 'UPDATE']}
                        ],
                        assigned: profile.resourceAccess.ORGANIZACION_RESTAPI === undefined ? [] : profile.resourceAccess.ORGANIZACION_RESTAPI.roles
                    }
                }
            ];

            var getModule = function(moduleName){
                for(var i = 0; i< apiModules.length; i++){
                    if(apiModules[i].module == moduleName.toUpperCase())
                        return apiModules[i];
                }
                return undefined;
            };

            var getRolesAssigned = function(moduleName){
                var module = getModule(moduleName);
                var result = [];
                for(var i=0;i<module.roles.available.length;i++){
                    if(module.roles.assigned.indexOf(module.roles.available[i].rol) != -1)
                        result.push(module.roles.available[i]);
                }
                return result;
            };

            //mode puede ser AND o OR
            profile.hasRole = function(moduleName, roles, operator){
                var module = getModule(moduleName);
                if(Array.isArray(roles)){
                    var result = true;
                    if(operator){
                        if(operator.toUpperCase() == 'OR')
                            result = false;
                    }
                    for(var i = 0; i< roles.length; i++){
                        if(operator && operator.toUpperCase() == 'OR'){
                            if(module.roles.assigned.indexOf(roles[i]) >= 0){
                                result = true;
                                break;
                            }
                        } else {
                            if(module.roles.assigned.indexOf(roles[i]) < 0){
                                result = false;
                                break;
                            }
                        }
                    }
                    return result;
                } else {
                    roles = roles.toUpperCase();
                    return module.roles.assigned.indexOf(roles) >= 0;
                }
            };

            profile.hasPermission = function(moduleName, operations){
                var rolesAssigned = getRolesAssigned(moduleName);
                if(Array.isArray(operations)){
                    var band = [];
                    for(var i = 0; i< rolesAssigned.length; i++){
                        for(var j = 0; j< operations.length; i++){
                            if(rolesAssigned[i].permissions.indexOf(operations[j]) > -1){
                                band.push(operations[j]);
                                break;
                            }
                        }
                    }
                    return band.length == operations.length;
                } else {
                    operations = operations.toUpperCase();
                    var result = false;
                    for(var i = 0; i< rolesAssigned.length; i++){
                        if(rolesAssigned[i].permissions.indexOf(operations) > -1){
                            result = true;
                            break;
                        }
                    }
                    return result;
                }
            };

            $provide.constant('activeProfile', profile);
        }]);

        app.config(function(uiSelectConfig) {
            uiSelectConfig.theme = 'select2';
        });

        app.config(function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

            //$urlRouterProvider.otherwise('/app/home');
            $urlRouterProvider.otherwise('/app/layout-and-skins');

            $stateProvider.
                // Main Layout Structure
                state('app', {
                    abstract: true,
                    url: '/app',
                    templateUrl: appHelper.templatePath('layout/body'),
                    controller: function($rootScope){
                        $rootScope.isLoginPage        = false;
                        $rootScope.isLightLoginPage   = false;
                        $rootScope.isLockscreenPage   = false;
                        $rootScope.isMainPage         = true;
                    }
                })

                // Dashboards
                .state('app.layout-and-skins', {
                    url: '/layout-and-skins',
                    templateUrl: appHelper.templatePath('layout-and-skins')
                })

                .state('app.home', {
                    url: '/home',
                    templateUrl: appHelper.templatePath('dashboards/home')
                }).state('app.organizacion', {
                    url: '/organizacion',
                    templateUrl: appHelper.templatePath('layout/app-body')
                }).state('app.cliente', {
                    url: '/cliente',
                    templateUrl: appHelper.templatePath('layout/app-body')
                }).state('app.transaccion', {
                    url: '/cliente',
                    templateUrl: appHelper.templatePath('layout/app-body')
                }).state('app.administracion', {
                    url: '/administracion',
                    templateUrl: appHelper.templatePath('layout/app-body')
                }).state('app.organizacion.estructura', {
                    url: '/estructura',
                    template: '<div ui-view></div>'
                }).state('app.organizacion.rrhh', {
                    url: '/rrhh',
                    template: '<div ui-view></div>'
                }).state('app.cliente.socio', {
                    url: '/rrhh',
                    template: '<div ui-view></div>'
                }).state('app.cliente.cuentaBancaria', {
                    url: '/rrhh',
                    template: '<div ui-view></div>'
                }).state('app.administracion.personas', {
                    url: '/personas',
                    template: '<div ui-view></div>'


                    /**************** ESTRUCTURA ****************/
                }).state('app.organizacion.estructura.buscarSucursal', {
                    url: '/sucursal/buscar',
                    templateUrl: appHelper.viewsPath('organizacion/sucursal/form-buscar-sucursal'),
                    controller: 'BuscarSucursalCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.buscarAgencia', {
                    url: '/agencia/buscar',
                    templateUrl: appHelper.viewsPath('organizacion/sucursal/agencia/form-buscar-agencia'),
                    controller: 'BuscarAgenciaCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.buscarBoveda', {
                    url: '/boveda/buscar',
                    templateUrl: appHelper.viewsPath('organizacion/sucursal/agencia/boveda/form-buscar-boveda'),
                    controller: 'BuscarBovedaCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.buscarCaja', {
                    url: '/caja/buscar',
                    templateUrl: appHelper.viewsPath('organizacion/sucursal/agencia/caja/form-buscar-caja'),
                    controller: 'BuscarCajaCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.crearSucursal', {
                    url: '/sucursal',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/form-crear-sucursal"),
                    controller: 'CrearSucursalCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.crearSucursal.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/form-datosPrincipales-crear"),
                    controller: 'SucursalDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarSucursal', {
                    url: '/sucursal/{id:[0-9]{1,8}}',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/form-editar-sucursal"),
                    resolve: {
                        sucursal: function($state, $stateParams, Sucursal) {
                            return Sucursal.$find($stateParams.id);
                        }
                    },
                    controller: function($scope, $stateParams, sucursal) {
                        $scope.params = {};
                        $scope.params.id = $stateParams.id;
                        $scope.params.object = sucursal;
                    },
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarSucursal.resumen', {
                    url: "/resumen",
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/form-resumen"),
                    controller: 'SucursalDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarSucursal.datosPrincipales', {
                    url: "/principal",
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/form-datosPrincipales-editar"),
                    controller: 'SucursalDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarSucursal.crearAgencia', {
                    url: "/agencia",
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/form-crear-agencia-from-sucursal"),
                    controller: 'CrearAgenciaFromSucursalCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarSucursal.crearAgencia.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/form-datosPrincipales-from-sucursal"),
                    controller: 'AgenciaDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.crearAgencia', {
                    url: '/agencia',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/form-crear-agencia"),
                    controller: 'CrearAgenciaCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.crearAgencia.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/form-datosPrincipales-crear"),
                    controller: 'AgenciaDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarAgencia', {
                    url: '/agencia/{id:[0-9]{1,8}}',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/form-editar-agencia"),
                    resolve: {
                        agencia: function($state, $stateParams, Agencia) {
                            return Agencia.$find($stateParams.id);
                        }
                    },
                    controller: function($scope, $stateParams, agencia) {
                        $scope.params = {};
                        $scope.params.id = $stateParams.id;
                        $scope.params.object = agencia;
                    },
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarAgencia.resumen', {
                    url: '/resumen',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/form-resumen"),
                    controller: 'AgenciaResumenCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarAgencia.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/form-datosPrincipales-editar"),
                    controller: 'AgenciaDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarAgencia.crearBoveda', {
                    url: "/boveda",
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/boveda/form-crear-boveda-from-agencia"),
                    controller: 'CrearBovedaFromAgenciaCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarAgencia.crearBoveda.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/boveda/form-datosPrincipales-from-agencia"),
                    controller: 'BovedaDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarAgencia.crearCaja', {
                    url: "/caja",
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/caja/form-crear-caja-from-agencia"),
                    controller: 'CrearCajaFromAgenciaCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarAgencia.crearCaja.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/caja/form-datosPrincipales-from-agencia"),
                    controller: 'CajaDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.crearBoveda', {
                    url: '/boveda',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/boveda/form-crear-boveda"),
                    controller: 'CrearBovedaCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.crearBoveda.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/boveda/form-datosPrincipales-crear"),
                    controller: 'BovedaDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarBoveda', {
                    url: '/boveda/{id:[0-9]{1,8}}',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/boveda/form-editar-boveda"),
                    resolve: {
                        boveda: function($state, $stateParams, Boveda) {
                            return Boveda.$find($stateParams.id);
                        }
                    },
                    controller: function($scope, $stateParams, boveda) {
                        $scope.params = {};
                        $scope.params.id = $stateParams.id;
                        $scope.params.object = boveda;
                    },
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarBoveda.resumen', {
                    url: '/resumen',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/boveda/form-resumen"),
                    controller: 'BovedaResumenCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarBoveda.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/boveda/form-datosPrincipales-editar"),
                    controller: 'BovedaDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarBoveda.abrir', {
                    url: '/abrir',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/boveda/form-abrir"),
                    controller: 'BovedaAbrirCtrl',
                    module: 'ORGANIZACION',
                    roles: ['JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarBoveda.cerrar', {
                    url: '/cerrar',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/boveda/form-cerrar"),
                    controller: 'BovedaCerrarCtrl',
                    module: 'ORGANIZACION',
                    roles: ['JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.crearCaja', {
                    url: '/caja',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/caja/form-crear-caja"),
                    controller: 'CrearCajaCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.crearCaja.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/caja/form-datosPrincipales-crear"),
                    controller: 'CajaDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarCaja', {
                    url: '/caja/{id:[0-9]{1,8}}',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/caja/form-editar-caja"),
                    resolve: {
                        caja: function($state, $stateParams, Caja) {
                            return Caja.$find($stateParams.id);
                        }
                    },
                    controller: function($scope, $stateParams, caja) {
                        $scope.params = {};
                        $scope.params.id = $stateParams.id;
                        $scope.params.object = caja;
                    },
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA', 'CAJERO'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarCaja.resumen', {
                    url: '/resumen',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/caja/form-resumen"),
                    controller: 'CajaResumenCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA', 'CAJERO'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarCaja.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/caja/form-datosPrincipales-editar"),
                    controller: 'CajaDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarCaja.bovedas', {
                    url: '/bovedas',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/caja/form-bovedas-editar"),
                    controller: 'CajaBovedasCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarCaja.abrir', {
                    url: '/abrir',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/caja/form-abrir"),
                    controller: 'CajaAbrirCtrl',
                    module: 'ORGANIZACION',
                    roles: ['JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.estructura.editarCaja.cerrar', {
                    url: '/cerrar',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/caja/form-cerrar"),
                    controller: 'CajaCerrarCtrl',
                    roles: ['CAJERO'],
                    operator: 'OR'


                    /**************** RRHH ****************/
                }).state('app.organizacion.rrhh.buscarTrabajador', {
                    url: '/trabajador/buscar',
                    templateUrl: appHelper.viewsPath('organizacion/sucursal/agencia/trabajador/form-buscar-trabajador'),
                    controller: 'BuscarTrabajadorCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.rrhh.buscarUsuario', {
                    url: '/usuario/buscar',
                    templateUrl: appHelper.viewsPath('organizacion/usuario/form-buscar-usuario'),
                    controller: 'BuscarUsuarioCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                }).state('app.organizacion.rrhh.crearTrabajador', {
                    url: '/trabajador',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/trabajador/form-crear-trabajador"),
                    controller: 'CrearTrabajadorCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                }).state('app.organizacion.rrhh.crearTrabajador.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/trabajador/form-datosPrincipales-crear"),
                    controller: 'TrabajadorDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                }).state('app.organizacion.rrhh.editarTrabajador', {
                    url: '/trabajador/{id:[0-9]{1,8}}',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/trabajador/form-editar-trabajador"),
                    resolve: {
                        trabajador: function($state, $stateParams, Trabajador) {
                            return Trabajador.$find($stateParams.id);
                        }
                    },
                    controller: function($scope, $stateParams, trabajador) {
                        $scope.params = {};
                        $scope.params.id = $stateParams.id;
                        $scope.params.object = trabajador;
                    },
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'PLATAFORMA', 'JEFE_CAJA', 'CAJERO'],
                    operator: 'OR'
                }).state('app.organizacion.rrhh.editarTrabajador.resumen', {
                    url: '/resumen',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/trabajador/form-resumen"),
                    controller: 'TrabajadorResumenCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'PLATAFORMA', 'JEFE_CAJA', 'CAJERO'],
                    operator: 'OR'
                }).state('app.organizacion.rrhh.editarTrabajador.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/trabajador/form-datosPrincipales-editar"),
                    controller: 'TrabajadorDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                }).state('app.organizacion.rrhh.editarTrabajador.accesoAlSistema', {
                    url: '/acceso',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/trabajador/form-accesoAlSistema"),
                    controller: 'TrabajadorAccesoAlSistemaCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                }).state('app.organizacion.rrhh.editarTrabajador.asignarCaja', {
                    url: '/caja',
                    templateUrl: appHelper.viewsPath("organizacion/sucursal/agencia/trabajador/form-asignarCaja"),
                    controller: 'TrabajadorAsignarCajaCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'

                    /************* CUENTA APORTE*****************/
                }).state('app.cliente.socio.buscarCuentaAporte', {
                    url: '/cuentaaporte/buscar',
                    templateUrl: appHelper.viewsPath('organizacion/cliente/cuentaAporte/form-buscar-cuenta-aporte'),
                    controller: 'BuscarCuentaAporteCtrl'
                }).state('app.cliente.cuentaBancaria.buscarCuentaBancaria', {
                    url: '/cuentabancaria/buscar',
                    templateUrl: appHelper.viewsPath('organizacion/cliente/cuentaBancaria/form-buscar-cuenta-bancaria'),
                    controller: 'BuscarCuentaBancariaCtrl'
                }).state('app.cliente.socio.crearCuentaAporte', {
                    url: '/cuentaaporte',
                    templateUrl: appHelper.viewsPath("organizacion/cliente/cuentaAporte/form-crear-cuenta-aporte"),
                    controller: 'CrearCuentaAporteCtrl'
                }).state('app.cliente.socio.crearCuentaAporte.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/cliente/cuentaAporte/form-datosPrincipales-crear"),
                    controller: 'CuentaAporteDatosPrincipalesCtrl'
                }).state('app.cliente.socio.crearCuentaAporte.apoderado', {
                    url: '/apoderado',
                    templateUrl: appHelper.viewsPath("organizacion/cliente/cuentaAporte/form-apoderado"),
                    controller: 'CuentaAporteApoderadoCtrl'
                }).state('app.cliente.socio.editarCuentaAporte', {
                    url: '/cuentaaporte/{id:[0-9]{1,8}}',
                    templateUrl: appHelper.viewsPath("organizacion/cliente/cuentaAporte/form-editar-cuenta-aporte"),
                    resolve: {
                        cuentaAporte: function($state, $stateParams, CuentaAporte) {
                            return CuentaAporte.$find($stateParams.id);
                        }
                    },
                    controller: function($scope, $stateParams, cuentaAporte) {
                        $scope.params = {};
                        $scope.params.id = $stateParams.id;
                        $scope.params.object = cuentaAporte;
                    }
                }).state('app.cliente.socio.editarCuentaAporte.resumen', {
                    url: '/resumen',
                    templateUrl: appHelper.viewsPath("organizacion/cliente/cuentaAporte/form-resumen"),
                    controller: 'CuentaAporteResumenCtrl'
                }).state('app.cliente.socio.editarCuentaAporte.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("organizacion/cliente/cuentaAporte/form-datosPrincipales-editar"),
                    controller: 'CuentaAporteDatosPrincipalesCtrl'
                }).state('app.cliente.socio.editarCuentaAporte.apoderado', {
                    url: '/apoderado',
                    templateUrl: appHelper.viewsPath("organizacion/cliente/cuentaAporte/form-apoderado"),
                    controller: 'CuentaAporteApoderadoCtrl'



                    /************* ADMINISTRACION PERSONAS*****************/
                }).state('app.administracion.personas.buscarPersonaNatural', {
                    url: '/natural/buscar',
                    templateUrl: appHelper.viewsPath('persona/natural/form-buscar-personaNatural'),
                    controller: 'BuscarPersonaNaturalCtrl'
                }).state('app.administracion.personas.buscarPersonaJuridica', {
                    url: '/juridica/buscar',
                    templateUrl: appHelper.viewsPath('persona/juridica/form-buscar-personaJuridica'),
                    controller: 'BuscarPersonaJuridicaCtrl'
                }).state('app.administracion.personas.crearPersonaNatural', {
                    url: '/natural?tipoDocumento&numeroDocumento',
                    templateUrl: appHelper.viewsPath("persona/natural/form-crear-personaNatural"),
                    controller: function($scope, $stateParams) {
                        $scope.params = {};
                        $scope.params.tipoDocumento = $stateParams.tipoDocumento;
                        $scope.params.numeroDocumento = $stateParams.numeroDocumento;
                    }
                }).state('app.administracion.personas.crearPersonaNatural.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("persona/natural/form-datosPrincipales"),
                    controller: 'PersonaNaturalDatosPrincipalesCtrl'
                }).state('app.administracion.personas.editarPersonaNatural', {
                    url: '/natural/{id:[0-9]{1,8}}',
                    templateUrl: appHelper.viewsPath("persona/natural/form-editar-personaNatural"),
                    resolve: {
                        personaNatural: function($state, $stateParams, PersonaNatural) {
                            return PersonaNatural.$find($stateParams.id);
                        }
                    },
                    controller: function($scope, $stateParams, personaNatural) {
                        $scope.params = {};
                        $scope.params.id = $stateParams.id;
                        $scope.params.object = personaNatural;
                    }
                }).state('app.administracion.personas.editarPersonaNatural.resumen', {
                    url: '/resumen',
                    templateUrl: appHelper.viewsPath("persona/natural/form-resumen"),
                    controller: 'PersonaNaturalResumenCtrl'
                }).state('app.administracion.personas.editarPersonaNatural.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("persona/natural/form-datosPrincipales"),
                    controller: 'PersonaNaturalDatosPrincipalesCtrl'
                }).state('app.administracion.personas.editarPersonaNatural.datosAdicionales', {
                    url: '/adicionales',
                    templateUrl: appHelper.viewsPath("persona/natural/form-datosAdicionales"),
                    controller: 'PersonaNaturalDatosAdicionalesCtrl'
                }).state('app.administracion.personas.crearPersonaJuridica', {
                    url: '/juridica?tipoDocumento&numeroDocumento',
                    templateUrl: appHelper.viewsPath("persona/juridica/form-crear-personaJuridica"),
                    controller: function($scope, $stateParams) {
                        $scope.params = {};
                        $scope.params.tipoDocumento = $stateParams.tipoDocumento;
                        $scope.params.numeroDocumento = $stateParams.numeroDocumento;
                    }
                }).state('app.administracion.personas.crearPersonaJuridica.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("persona/juridica/form-datosPrincipales"),
                    controller: 'PersonaJuridicaDatosPrincipalesCtrl'
                }).state('app.administracion.personas.crearPersonaJuridica.representante', {
                    url: '/representante',
                    templateUrl: appHelper.viewsPath("persona/juridica/form-representante"),
                    controller: 'PersonaJuridicaRepresentanteLegalCtrl'
                }).state('app.administracion.personas.editarPersonaJuridica', {
                    url: '/juridica/{id:[0-9]{1,8}}',
                    templateUrl: appHelper.viewsPath("persona/juridica/form-editar-personaJuridica"),
                    resolve: {
                        personaJuridica: function($state, $stateParams, PersonaJuridica) {
                            return PersonaJuridica.$find($stateParams.id);
                        }
                    },
                    controller: function($scope, $stateParams, personaJuridica) {
                        $scope.params = {};
                        $scope.params.id = $stateParams.id;
                        $scope.params.object = personaJuridica;
                    }
                }).state('app.administracion.personas.editarPersonaJuridica.resumen', {
                    url: '/resumen',
                    templateUrl: appHelper.viewsPath("persona/juridica/form-resumen"),
                    controller: 'PersonaJuridicaResumenCtrl'
                }).state('app.administracion.personas.editarPersonaJuridica.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewsPath("persona/juridica/form-datosPrincipales"),
                    controller: 'PersonaJuridicaDatosPrincipalesCtrl'
                }).state('app.administracion.personas.editarPersonaJuridica.datosAdicionales', {
                    url: '/adicionales',
                    templateUrl: appHelper.viewsPath("persona/juridica/form-datosAdicionales"),
                    controller: 'PersonaJuridicaDatosAdicionalesCtrl'
                }).state('app.administracion.personas.editarPersonaJuridica.representante', {
                    url: '/representante',
                    templateUrl: appHelper.viewsPath("persona/juridica/form-representante"),
                    controller: 'PersonaJuridicaRepresentanteLegalCtrl'
                }).state('app.administracion.personas.editarPersonaJuridica.crearAccionista', {
                    url: '/accionista',
                    templateUrl: appHelper.viewsPath("persona/juridica/form-accionista"),
                    controller: 'PersonaJuridicaDatosAdicionalesCtrl'
                });

        });

        app.run(function(Restangular, Notifications) {
            Restangular.setErrorInterceptor(function(response, deferred, responseHandler) {
                if(response.status === 0) {
                    Notifications.error('Al parecer no se pudo realizar la conexion al sistema, actualice la pagina presionando F5.');
                    return false; // error handled
                }
                if(response.status === 403) {
                    return false; // error handled
                }
                if(response.status === 405) {
                    alert("405");
                    return false; // error handled
                }
                return true; // error not handled
            });
        });

        app.run(function($rootScope, $state, activeProfile) {
            $rootScope.$on('$stateChangeStart',
                function(event, toState, toParams, fromState, fromParams){
                    if(toState.module && toState.roles){
                        if(!activeProfile.hasRole(toState.module, toState.roles, toState.operator)){
                            event.preventDefault();
                            alert('State unauthorized.');
                        }
                    }
                })
        });

        app.config(function($httpProvider) {
            $httpProvider.interceptors.push('errorInterceptor');

            var spinnerFunction = function(data, headersGetter) {
                if (resourceRequests == 0) {
                    loadingTimer = window.setTimeout(function() {
                        //$('#loading').show();
                        loadingTimer = -1;
                    }, 500);
                }
                resourceRequests++;
                return data;
            };
            $httpProvider.defaults.transformRequest.push(spinnerFunction);

            $httpProvider.interceptors.push('spinnerInterceptor');
            $httpProvider.interceptors.push('authInterceptor');
        });

        app.factory('errorInterceptor', function($q, $window, $rootScope, $location,Notifications) {
            return function(promise) {
                return promise.then(function(response) {
                    return response;
                }, function(response) {
                    if (response.status == 401) {
                        console.log('session timeout?');
                        logout();
                    } else if (response.status == 403) {
                        Notifications.error("Forbidden");
                    } else if (response.status == 404) {
                        Notifications.error("Not found");
                    } else if (response.status) {
                        if (response.data && response.data.errorMessage) {
                            Notifications.error(response.data.errorMessage);
                        } else {
                            Notifications.error("An unexpected server error has occurred");
                        }
                    }
                    return $q.reject(response);
                });
            };
        });
        app.factory('spinnerInterceptor', function($q, $window, $rootScope, $location) {
            return function(promise) {
                return promise.then(function(response) {
                    resourceRequests--;
                    if (resourceRequests == 0) {
                        if(loadingTimer != -1) {
                            window.clearTimeout(loadingTimer);
                            loadingTimer = -1;
                        }
                        //$('#loading').hide();
                    }
                    return response;
                }, function(response) {
                    resourceRequests--;
                    if (resourceRequests == 0) {
                        if(loadingTimer != -1) {
                            window.clearTimeout(loadingTimer);
                            loadingTimer = -1;
                        }
                        //$('#loading').hide();
                    }

                    return $q.reject(response);
                });
            };
        });

        return app;
    }
);





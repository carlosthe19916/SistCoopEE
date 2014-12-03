/*jshint unused: vars */
define([
        'angular',
        './common/main',
        './organizacion/main',
        './persona/main',
        './ubigeo/main'
    ]/*deps*/,
    function (angular){

        'use strict';

        var consoleBaseUrl = window.location.href;
        consoleBaseUrl = consoleBaseUrl.substring(0, consoleBaseUrl.indexOf("/app/admin"));
        consoleBaseUrl = consoleBaseUrl + "/app/admin/admin";
        var configUrl = consoleBaseUrl + "/config";
        var logoutUrl = consoleBaseUrl + "/logout";
        var auth = {};
        var logout = function(){
            console.log('*** LOGOUT');
            window.location = logoutUrl;
        };

        var authUrl = window.location.href;
        authUrl = window.location.href.substring(0,  authUrl.indexOf('/admin/admin'));

        angular.element(document).ready(function ($http) {

            var keycloakAuth = new Keycloak(configUrl);

            keycloakAuth.onAuthLogout = function() {
                location.reload();
            };

            keycloakAuth.init({ onLoad: 'login-required' }).success(function () {
                auth.authz = keycloakAuth;
                if(keycloakAuth.realmAccess){
                    module.factory('Auth', function() {
                        return auth;
                    });
                    angular.bootstrap(document, ["sistcoop"]);
                } else {
                    keycloakAuth.logout();
                }
            }).error(function () {
                window.location.reload();
            });

        });

        var module = angular.module('sistcoop', [
            'ngSanitize',
            'ngMessages',
            //'ngAnimate',

            'persona',
            'ubigeo',
            'organizacion',
            'common',

            'restangular',
            'ui.bootstrap',
            'ui.router',
            'ui.select',
            'ui.utils',
            'ui.grid',
            'ui.grid.edit',
            'ui.grid.selection',
            'blockUI',
            'angular-ladda'
        ]);

        var resourceRequests = 0;
        var loadingTimer = -1;

        module.factory('authInterceptor', function($q, Auth) {
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

        module.config(function(RestangularProvider) {
            RestangularProvider.setBaseUrl('http://localhost:8080');

            //añade @ a los atributos
            RestangularProvider.addFullRequestInterceptor(function(element, operation, route, url, headers, params, httpConfig) {
                if(operation == 'post' || operation == 'put'){
                    var newElement;
                    if(element){
                        newElement = element[Object.keys(element)[0]];
                        angular.forEach(newElement, function(value, key) {
                            if(angular.isObject(value) && !angular.isDate(value) && !angular.isArray(value)){
                                var obj = angular.copy(value);
                                angular.forEach(value, function(val, k) {
                                    if(angular.isObject(value)){
                                        this['@' + k.toString()] = val !== null ? val: undefined;
                                        delete this[k];
                                    }
                                }, obj);
                                this[key.toString()] = obj;
                            } else {
                                this['@' + key.toString()] = value !== null ? value: undefined;
                                delete this[key];
                            }
                        }, newElement);
                    }
                }
            });

            //saca el primer objeto
            RestangularProvider.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
                var extractedData;
                if(data){
                    extractedData = data[Object.keys(data)[0]];
                    extractedData.meta = data.meta;
                } else {
                    extractedData = data;
                }
                return extractedData;
            });
            //saca los @ de los atributos
            RestangularProvider.setResponseExtractor(function(response) {
                var newResponse = angular.copy(response);
                if (angular.isArray(response)) {
                    angular.forEach(newResponse, function(value, key) {
                        newResponse[key].originalElement = angular.copy(value);
                    });
                } else {
                    if(response){
                        //newResponse.originalElement = angular.copy(response);
                        angular.forEach(response, function(value, key) {
                            if(key.substring(0,1) == '@') {
                                var newKey = key.replace('@', '');
                                this[newKey.toString()] = value;
                                delete this[key];
                            } else {
                                var obj = angular.copy(value);
                                angular.forEach(value, function(val, k) {
                                    if(k.substring(0,1) == '@') {
                                        var newKey = k.replace('@', '');
                                        this[newKey.toString()] = val;
                                        delete this[k];
                                    }
                                }, obj);
                                this[key.toString()] = obj;
                            }
                        }, newResponse);
                    }
                }
                return newResponse;
            });
        });

        module.factory('PersonaRestangular', function(Restangular) {
            return Restangular.withConfig(function(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl('http://localhost:8080/restapi-persona/rest/v1');
            });
        });

        module.factory('UbigeoRestangular', function(Restangular) {
            return Restangular.withConfig(function(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl('http://localhost:8080/restapi-ubigeo/rest/v1');
            });
        });

        module.factory('OrganizacionRestangular', function(Restangular) {
            return Restangular.withConfig(function(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl('http://localhost:8080/restapi-organizacion/rest/v1');
            });
        });

        module.config(['$provide', function($provide){
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

            //carlos
            profile.getSubmenu = function(menuName){
                menuName = menuName.toLowerCase();
                if(menuName == 'administracion'){

                } else if(menuName == 'organizacion'){
                    if(profile.hasRole('ORGANIZACION', 'ADMIN')){
                        return [
                            {'name':'ESTRUCTURA', 'state': 'app.organizacion', header: true},
                            {'name':'Sucursales', 'state': 'app.organizacion.buscarSucursal', header: false},
                            {'name':'Agencias', 'state': 'app.organizacion.buscarAgencia', header: false},
                            {'name':'Bovedas', 'state': 'app.organizacion.buscarBoveda', header: false},
                            {'name':'Cajas', 'state': 'app.organizacion.buscarCaja', header: false},

                            {'name':'RRHH', 'state': 'app.organizacion', header: true},
                            {'name':'Trabajadores', 'state': 'app.trabajador.buscarTrabajador', header: false},
                            {'name':'Usuarios', 'state': 'app.trabajador.buscarUsuarios', header: false}
                        ];
                    } else if(profile.hasRole('ORGANIZACION', 'GERENTE_GENERAL')){
                        return [
                            {'name':'ESTRUCTURA', 'state': 'app.organizacion', header: true},
                            {'name':'Sucursales', 'state': 'app.organizacion.buscarSucursal', header: false},
                            {'name':'Agencias', 'state': 'app.organizacion.buscarAgencia', header: false},
                            {'name':'Bovedas', 'state': 'app.organizacion.buscarBoveda', header: false},
                            {'name':'Cajas', 'state': 'app.organizacion.buscarCaja', header: false},

                            {'name':'RRHH', 'state': 'app.organizacion', header: true},
                            {'name':'Trabajadores', 'state': 'app.trabajador.buscarTrabajador', header: false},
                            {'name':'Usuarios', 'state': 'app.trabajador.buscarUsuarios', header: false}
                        ];
                    } else if(profile.hasRole('ORGANIZACION', 'ADMINISTRADOR_GENERAL')){
                        return [
                            {'name':'ESTRUCTURA', 'state': 'app.organizacion', header: true},
                            {'name':'Sucursales', 'state': 'app.organizacion.buscarSucursal', header: false},
                            {'name':'Agencias', 'state': 'app.organizacion.buscarAgencia', header: false},
                            {'name':'Bovedas', 'state': 'app.organizacion.buscarBoveda', header: false},
                            {'name':'Cajas', 'state': 'app.organizacion.buscarCaja', header: false},

                            {'name':'RRHH', 'state': 'app.organizacion', header: true},
                            {'name':'Trabajadores', 'state': 'app.trabajador.buscarTrabajador', header: false},
                            {'name':'Usuarios', 'state': 'app.trabajador.buscarUsuarios', header: false}
                        ];
                    } else if(profile.hasRole('ORGANIZACION', 'ADMINISTRADOR')){
                        return [
                            {'name':'ESTRUCTURA', 'state': 'app.organizacion', header: true},
                            {'name':'Sucursales', 'state': 'app.organizacion.buscarSucursal', header: false},
                            {'name':'Agencias', 'state': 'app.organizacion.buscarAgencia', header: false},
                            {'name':'Bovedas', 'state': 'app.organizacion.buscarBoveda', header: false},
                            {'name':'Cajas', 'state': 'app.organizacion.buscarCaja', header: false},

                            {'name':'RRHH', 'state': 'app.organizacion', header: true},
                            {'name':'Trabajadores', 'state': 'app.trabajador.buscarTrabajador', header: false},
                            {'name':'Usuarios', 'state': 'app.trabajador.buscarUsuarios', header: false}
                        ];
                    } else if(profile.hasRole('ORGANIZACION', 'PLATAFORMA')){
                        return undefined;
                    } else if(profile.hasRole('ORGANIZACION', 'JEFE_CAJA')){
                        return [
                            {'name':'ESTRUCTURA', 'state': 'app.organizacion', header: true},
                            {'name':'Bovedas', 'state': 'app.organizacion.buscarBoveda', header: false},
                            {'name':'Cajas', 'state': 'app.organizacion.buscarCaja', header: false}
                        ];
                    } else if(profile.hasRole('ORGANIZACION', 'CAJERO')){
                        return undefined;
                    } else {
                        return undefined;
                    }
                } else if(menuName == 'transaccion'){

                }  else if(menuName == 'socio'){

                } else {
                    return undefined;
                }
            };

            $provide.constant('activeProfile', profile);
        }]);

        module.config(function(uiSelectConfig) {
            uiSelectConfig.theme = 'bootstrap';
        });

        module.config(function(blockUIConfig) {
            blockUIConfig.message = 'Cargando...';
            blockUIConfig.template = '' +
                '<div class="row">' +
                '<div class="col-md-2 col-md-offset-6" style="top: 2.9em; position: fixed; z-index: 1500; text-align: center;">' +
                '<div class="alert alert-warning">' +
                '<span><strong>{{ state.message}}</strong></span>' +
                '</div>' +
                '</div>' +
                '</div>';
        });

        module.config([ '$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

            $urlRouterProvider.otherwise('/home');

            $stateProvider
                .state('home', {
                    url: '/home',
                    templateUrl: '../../views/themplate/themplate01.html'
                })
                .state('app', {
                    abstract: true,
                    url: '/app',
                    templateUrl: '../../views/themplate/themplate02.html'
                })

                .state('app.transaccion', {
                    url: "/transaccion",
                    views: {
                        "viewMenu":{
                            controller: function($scope){
                                $scope.menus = [
                                    {'name':'Cuenta aporte', 'state': 'app.transaccion', header: true},
                                    {'name':'Aporte', 'state': 'app.transaccion.aporte', header: false},

                                    {'name':'Cuenta bancaria', 'state': 'app.transaccion', header: true},
                                    {'name':'Deposito/retiro', 'state': 'app.transaccion.transaccionBancaria', header: false},
                                    {'name':'Transferencia', 'state': 'app.transaccion.transferenciaBancaria', header: false},

                                    {'name':'Otros', 'state': 'app.transaccion', header: true},
                                    {'name':'Compra/venta', 'state': 'app.transaccion.compraVenta', header: false},

                                    {'name':'Transacciones del dia', 'state': 'app.transaccion', header: true},
                                    {'name':'Transacciones', 'state': 'app.transaccion.buscarTransaccion', header: false},

                                    {'name':'Interno', 'state': 'app.transaccion', header: true},
                                    {'name':'Pendiente', 'state': 'app.transaccion.buscarPendientes', header: false},
                                    {'name':'Boveda/boveda', 'state': 'app.transaccion.buscarTransaccionBovedaBoveda', header: false},
                                    {'name':'Boveda/caja', 'state': 'app.transaccion.buscarTransaccionBovedaCaja', header: false},
                                    {'name':'Caja/caja', 'state': 'app.transaccion.buscarTransaccionCajaCaja', header: false},


                                    {'name':'Externo', 'state': 'app.transaccion', header: true},
                                    {'name':'Entidad/boveda', 'state': 'app.transaccion.buscarTransaccionEntidadBoveda', header: false}
                                ];
                            }
                        },
                        "viewContent":{
                            templateUrl: '../../views/themplate/themplate02-content.html',
                            controller: function($scope){
                                $scope.themplate = {};
                                $scope.themplate.header = 'Administracion';
                            }
                        }
                    }
                })
                .state('app.organizacion', {
                    url: "/organizacion",
                    views: {
                        "viewMenu":{
                            controller: function($scope, activeProfile){
                                $scope.menus = activeProfile.getSubmenu('organizacion');
                            }
                        },
                        "viewContent":{
                            templateUrl: '../../views/themplate/themplate02-content.html',
                            controller: function($scope){
                                $scope.themplate = {};
                                $scope.themplate.header = 'Organizacion';
                            }
                        }
                    }
                })
                .state('app.socio', {
                    url: "/socio",
                    views: {
                        "viewMenu":{
                            controller: function($scope){
                                $scope.menus = [
                                    {'name':'Socio', 'state': 'app.socio', header: true},
                                    {'name':'Buscar', 'state': 'app.socio.buscarSocio', header: false},

                                    {'name':'Cuenta aporte', 'state': 'app.socio', header: true},
                                    {'name':'Nuevo', 'state': 'app.socio.crearCuentaAporte', header: false},
                                    {'name':'Buscar', 'state': 'app.socio.buscarCuentaAporte', header: false},

                                    {'name':'Cuenta bancaria', 'state': 'app.socio', header: true},
                                    {'name':'Nuevo', 'state': 'app.socio.crearCuentaBancaria', header: false},
                                    {'name':'Buscar', 'state': 'app.socio.buscarCuentaBancaria', header: false}
                                ];
                            }
                        },
                        "viewContent":{
                            templateUrl: '../../views/themplate/themplate02-content.html',
                            controller: function($scope){
                                $scope.themplate = {};
                                $scope.themplate.header = 'Administracion';
                            }
                        }
                    }
                })
                .state('app.administracion', {
                    url: "/administracion",
                    views: {
                        "viewMenu":{
                            controller: function($scope){
                                $scope.menus = [
                                    {'name':'Persona Natural', 'state': 'app.administracion', header: true},
                                    {'name':'Nuevo', 'state': 'app.administracion.crearPersonaNatural', header: false},
                                    {'name':'Buscar', 'state': 'app.administracion.buscarPersonaNatural', header: false},

                                    {'name':'Persona Jurídica', 'state': 'app.administracion', header: true},
                                    {'name':'Nuevo', 'state': 'app.administracion.crearPersonaJuridica', header: false},
                                    {'name':'Buscar', 'state': 'app.administracion.buscarPersonaJuridica', header: false}
                                ];
                            }
                        },
                        "viewContent":{
                            templateUrl: '../../views/themplate/themplate02-content.html',
                            controller: function($scope){
                                $scope.themplate = {};
                                $scope.themplate.header = 'Administracion';
                            }
                        }
                    }
                });
        } ]);

        module.config([ '$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

            $urlRouterProvider.when('/app/organizacion/sucursal', '/app/organizacion/sucursal/principal');
            $urlRouterProvider.when('/app/organizacion/sucursal/{id:[0-9]{1,8}}', '/app/organizacion/sucursal/{id:[0-9]{1,8}}/resumen');

            $urlRouterProvider.when('/app/organizacion/sucursal/{id:[0-9]{1,8}}/agencias', '/app/organizacion/sucursal/{id:[0-9]{1,8}}/principal');

            $stateProvider
                .state('app.organizacion.buscarSucursal', {
                    url: '/sucursal/buscar',
                    templateUrl: "../../views/organizacion/sucursal/form-buscar-sucursal.html",
                    controller: function($scope) {
                        $scope.themplate.header = 'Buscar sucursal';
                    },
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                })
                .state('app.organizacion.buscarAgencia', {
                    url: '/agencia/buscar',
                    templateUrl: "../../views/organizacion/sucursal/agencia/form-buscar-agencia.html",
                    controller: function($scope) {
                        $scope.themplate.header = 'Buscar agencia';
                    },
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                })
                .state('app.organizacion.buscarBoveda', {
                    url: '/boveda/buscar',
                    templateUrl: "../../views/organizacion/sucursal/agencia/boveda/form-buscar-boveda.html",
                    controller: function($scope) {
                        $scope.themplate.header = 'Buscar boveda';
                    },
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                })
                .state('app.organizacion.buscarCaja', {
                    url: '/caja/buscar',
                    templateUrl: "../../views/organizacion/sucursal/agencia/caja/form-buscar-caja.html",
                    controller: function($scope) {
                        $scope.themplate.header = 'Buscar caja';
                    },
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR', 'JEFE_CAJA'],
                    operator: 'OR'
                })


                .state('app.organizacion.crearSucursal', {
                    url: '/sucursal',
                    templateUrl: "../../views/organizacion/sucursal/form-crear-sucursal.html",
                    controller: function($scope) {
                        $scope.themplate.header = 'Crear sucursal';
                    },
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                })
                .state('app.organizacion.crearSucursal.datosPrincipales', {
                    url: '/principal',
                    templateUrl: "../../views/organizacion/sucursal/form-datosPrincipales.html",
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                })
                .state('app.organizacion.editarSucursal', {
                    url: "/sucursal/:id",
                    templateUrl: "../../views/organizacion/sucursal/form-editar-sucursal.html",
                    resolve: {
                        sucursal: function($state, $stateParams, Sucursal) {
                            return Sucursal.$find($stateParams.id);
                        }
                    },
                    controller: function($scope, $stateParams, sucursal) {
                        $scope.themplate.header = 'Editar sucursal';

                        $scope.params = {};
                        $scope.params.id = $stateParams.id;
                        $scope.params.object = sucursal;
                    },
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                })
                .state('app.organizacion.editarSucursal.resumen', {
                    url: "/resumen",
                    templateUrl: "../../views/organizacion/sucursal/form-resumen.html",
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'GERENTE_GENERAL', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                })
                .state('app.organizacion.editarSucursal.datosPrincipales', {
                    url: '/principal',
                    templateUrl: "../../views/organizacion/sucursal/form-datosPrincipales.html",
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                })
                .state('app.organizacion.editarSucursal.buscarAgencias', {
                    url: '/agencias/buscar',
                    templateUrl: "../../views/organizacion/sucursal/agencia/form-buscar-agencia.html",
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'ADMINISTRADOR_GENERAL', 'ADMINISTRADOR'],
                    operator: 'OR'
                })
                .state('app.organizacion.editarSucursal.crearAgencia', {
                    url: '/agencias',
                    templateUrl: "../../views/organizacion/sucursal/agencia/form-crear-agencia.html",
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                })
                .state('app.organizacion.editarSucursal.crearAgencia.datosPrincipales', {
                    url: '/principal',
                    templateUrl: "../../views/organizacion/sucursal/agencia/form-datosPrincipales.html",
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                })
                .state('app.organizacion.editarSucursal.editarAgencia', {
                    url: '/agencias/:id',
                    templateUrl: "../../views/organizacion/sucursal/agencia/form-editar-agencia.html",
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                })
                .state('app.organizacion.editarSucursal.editarAgencia.resumen', {
                    url: '/resumen',
                    templateUrl: "../../views/organizacion/sucursal/agencia/form-resumen.html",
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                })
                .state('app.organizacion.editarSucursal.editarAgencia.datosPrincipales', {
                    url: '/principal',
                    templateUrl: "../../views/organizacion/sucursal/agencia/form-datosPrincipales.html",
                    module: 'ORGANIZACION',
                    roles: ['ADMIN', 'ADMINISTRADOR_GENERAL'],
                    operator: 'OR'
                });
        } ]);

        module.config([ '$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

            $urlRouterProvider.when('/app/administracion/persona/natural', '/app/administracion/persona/natural/principal');
            $urlRouterProvider.when('/app/administracion/persona/natural/{id:[0-9]{1,8}}', '/app/administracion/persona/natural/{id:[0-9]{1,8}}/resumen');

            $urlRouterProvider.when('/app/administracion/persona/juridica', '/app/administracion/persona/juridica/principal');
            $urlRouterProvider.when('/app/administracion/persona/juridica/{id:[0-9]{1,8}}', '/app/administracion/persona/juridica/{id:[0-9]{1,8}}/resumen');

            $stateProvider
                .state('app.administracion.buscarPersonaNatural', {
                    url: '/persona/natural/buscar',
                    templateUrl: "../../views/persona/natural/form-buscar-personaNatural.html",
                    controller: function($scope) {
                        $scope.themplate.header = 'Buscar persona natural';
                    },
                    module: 'PERSONA',
                    roles: ['PUBLIC']
                })
                .state('app.administracion.crearPersonaNatural', {
                    url: "/persona/natural?documento&numero",
                    templateUrl: "../../views/persona/natural/form-crear-personaNatural.html",
                    controller: function($scope, $stateParams) {
                        $scope.themplate.header = 'Crear persona natural';

                        $scope.params = {};
                        $scope.params.tipoDocumento = $stateParams.documento;
                        $scope.params.numeroDocumento = $stateParams.numero;
                    },
                    module: 'PERSONA',
                    roles: ['USER']
                })
                .state('app.administracion.crearPersonaNatural.datosPrincipales', {
                    url: "/principal",
                    templateUrl: "../../views/persona/natural/form-datosPrincipales.html",
                    module: 'PERSONA',
                    roles: ['USER']
                })
                .state('app.administracion.editarPersonaNatural', {
                    url: "/persona/natural/:id",
                    templateUrl: "../../views/persona/natural/form-editar-personaNatural.html",
                    resolve: {
                        persona: function($state, $stateParams, PersonaNatural) {
                            return PersonaNatural.$find($stateParams.id);
                        }
                    },
                    controller: function($scope, $stateParams, persona) {
                        $scope.themplate.header = 'Editar persona natural';

                        $scope.params = {};
                        $scope.params.id = $stateParams.id;
                        $scope.params.object = persona;
                    },
                    module: 'PERSONA',
                    roles: ['PUBLIC']
                })
                .state('app.administracion.editarPersonaNatural.resumen', {
                    url: "/resumen",
                    templateUrl: "../../views/persona/natural/form-resumen.html",
                    module: 'PERSONA',
                    roles: ['PUBLIC']
                })
                .state('app.administracion.editarPersonaNatural.datosPrincipales', {
                    url: "/principal",
                    templateUrl: "../../views/persona/natural/form-datosPrincipales.html",
                    module: 'PERSONA',
                    roles: ['ADMIN']
                })
                .state('app.administracion.editarPersonaNatural.datosAdicionales', {
                    url: "/adicionales",
                    templateUrl: "../../views/persona/natural/form-datosAdicionales.html",
                    module: 'PERSONA',
                    roles: ['ADMIN']
                })

                .state('app.administracion.buscarPersonaJuridica', {
                    url: '/persona/juridica/buscar',
                    templateUrl: "../../views/persona/juridica/form-buscar-personaJuridica.html",
                    controller: function($scope) {
                        $scope.themplate.header = 'Buscar persona juridica';
                    },
                    module: 'PERSONA',
                    roles: ['PUBLIC']
                })
                .state('app.administracion.crearPersonaJuridica', {
                    url: "/persona/juridica?documento&numero",
                    templateUrl: "../../views/persona/juridica/form-crear-personaJuridica.html",
                    controller: function($scope, $stateParams) {
                        $scope.themplate.header = 'Crear persona juridica';

                        $scope.params = {};
                        $scope.params.tipoDocumento = $stateParams.documento;
                        $scope.params.numeroDocumento = $stateParams.numero;
                    },
                    module: 'PERSONA',
                    roles: ['USER']
                })
                .state('app.administracion.crearPersonaJuridica.datosPrincipales', {
                    url: "/principal",
                    templateUrl: "../../views/persona/juridica/form-datosPrincipales.html",
                    module: 'PERSONA',
                    roles: ['USER']
                })
                .state('app.administracion.crearPersonaJuridica.representante', {
                    url: "/representante",
                    templateUrl: "../../views/persona/juridica/form-representante.html",
                    module: 'PERSONA',
                    roles: ['USER']
                })
                .state('app.administracion.editarPersonaJuridica', {
                    url: "/persona/juridica/:id",
                    templateUrl: "../../views/persona/juridica/form-editar-personaJuridica.html",
                    resolve: {
                        persona: function($state, $stateParams, PersonaJuridica) {
                            return PersonaJuridica.$find($stateParams.id);
                        }
                    },
                    controller: function($scope, $stateParams, persona) {
                        $scope.themplate.header = 'Editar persona juridica';

                        $scope.params = {};
                        $scope.params.id = $stateParams.id;
                        $scope.params.object = persona;
                    },
                    module: 'PERSONA',
                    roles: ['PUBLIC']
                })
                .state('app.administracion.editarPersonaJuridica.resumen', {
                    url: "/resumen",
                    templateUrl: "../../views/persona/juridica/form-resumen.html",
                    module: 'PERSONA',
                    roles: ['PUBLIC']
                })
                .state('app.administracion.editarPersonaJuridica.datosPrincipales', {
                    url: "/principal",
                    templateUrl: "../../views/persona/juridica/form-datosPrincipales.html",
                    module: 'PERSONA',
                    roles: ['ADMIN']
                })
                .state('app.administracion.editarPersonaJuridica.datosAdicionales', {
                    url: "/adicionales",
                    templateUrl: "../../views/persona/juridica/form-datosAdicionales.html",
                    module: 'PERSONA',
                    roles: ['ADMIN']
                })
                .state('app.administracion.editarPersonaJuridica.representante', {
                    url: "/representante",
                    templateUrl: "../../views/persona/juridica/form-representante.html",
                    module: 'PERSONA',
                    roles: ['ADMIN']
                })
                .state('app.administracion.editarPersonaJuridica.accionista', {
                    url: "/accionistas",
                    templateUrl: "../../views/persona/juridica/form-accionista.html",
                    module: 'PERSONA',
                    roles: ['ADMIN']
                });
        } ]);

        module.run(function(Restangular, Notifications) {
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

        module.run(function($rootScope, $state, activeProfile) {
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

        module.config(function($httpProvider) {
            $httpProvider.interceptors.push('errorInterceptor');

            var spinnerFunction = function(data, headersGetter) {
                if (resourceRequests == 0) {
                    loadingTimer = window.setTimeout(function() {
                        $('#loading').show();
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

        module.factory('errorInterceptor', function($q, $window, $rootScope, $location,Notifications) {
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

        module.factory('spinnerInterceptor', function($q, $window, $rootScope, $location) {
            return function(promise) {
                return promise.then(function(response) {
                    resourceRequests--;
                    if (resourceRequests == 0) {
                        if(loadingTimer != -1) {
                            window.clearTimeout(loadingTimer);
                            loadingTimer = -1;
                        }
                        $('#loading').hide();
                    }
                    return response;
                }, function(response) {
                    resourceRequests--;
                    if (resourceRequests == 0) {
                        if(loadingTimer != -1) {
                            window.clearTimeout(loadingTimer);
                            loadingTimer = -1;
                        }
                        $('#loading').hide();
                    }

                    return $q.reject(response);
                });
            };
        });

        return module;
    }
);





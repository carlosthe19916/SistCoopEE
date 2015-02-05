/*jshint unused: vars */
define([
        'angular',
        'TweenMax',
        './xenon/main',
        './common/main',
        './organizacion/main',
        './persona/main',
        './ubigeo/main'
    ]/*deps*/,
    function (angular, TweenMax){

        'use strict';

        var app = angular.module('sistcoop-app',
            [
                /*xenon*/
                'xenon',

                /*angular*/
                'ngCookies',
                //'ngAria',
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

        app.constant('TweenMax', TweenMax);

        app.run(function($rootScope, $timeout) {

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

            $urlRouterProvider.otherwise('/app/home');

            $stateProvider.
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

                /*HOME*/
                .state('app.home', {
                    url: '/home',
                    templateUrl: appHelper.templatePath('dashboards/home')
                })

                /*ROLES*/
                .state('app.admin', {
                    url: '/admin',
                    templateUrl: appHelper.templatePath('layout/app-body')
                }).state('app.gerentegeneral', {
                    url: '/gerentegeneral',
                    templateUrl: appHelper.templatePath('layout/app-body')
                }).state('app.administradorgeneral', {
                    url: '/administradorgeneral',
                    templateUrl: appHelper.templatePath('layout/app-body')
                }).state('app.administrador', {
                    url: '/administrador',
                    templateUrl: appHelper.templatePath('layout/app-body')
                }).state('app.jefecaja', {
                    url: '/jefecaja',
                    templateUrl: appHelper.templatePath('layout/app-body')
                }).state('app.plataforma', {
                    url: '/plataforma',
                    templateUrl: appHelper.templatePath('layout/app-body')
                }).state('app.cajero', {
                    url: '/cajero',
                    templateUrl: appHelper.templatePath('layout/app-body')
                })

                /**MENU ORGANIZACION**/
                .state('app.admin.organizacion', {
                    url: '/organizacion',
                    template: '<div ui-view></div>'
                }).state('app.gerentegeneral.organizacion', {
                    url: '/organizacion',
                    template: '<div ui-view></div>'
                }).state('app.administradorgeneral.organizacion', {
                    url: '/organizacion',
                    template: '<div ui-view></div>'
                })
                /*SUBMENU ESTRUCTURA*/
                .state('app.admin.organizacion.estructura', {
                    url: '/estructura',
                    template: '<div ui-view></div>'
                }).state('app.gerentegeneral.organizacion.estructura', {
                    url: '/estructura',
                    template: '<div ui-view></div>'
                }).state('app.administradorgeneral.organizacion.estructura', {
                    url: '/estructura',
                    template: '<div ui-view></div>'
                })

                .state('app.admin.organizacion.estructura.buscarSucursal', {
                    url: '/sucursal/buscar',
                    templateUrl: appHelper.viewPath('organizacion/sucursal/form-buscar-sucursal'),
                    controller: 'BuscarSucursalCtrl_Admin',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN'],
                }).state('app.admin.organizacion.estructura.crearSucursal', {
                    url: '/sucursal',
                    templateUrl: appHelper.viewPath("organizacion/sucursal/form-crear-sucursal"),
                    controller: 'CrearSucursalCtrl_Admin',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN']
                }).state('app.admin.organizacion.estructura.crearSucursal.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewPath("organizacion/sucursal/form-datosPrincipales-crear"),
                    controller: 'SucursalDatosPrincipalesCtrl_Admin',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN']
                }).state('app.admin.organizacion.estructura.editarSucursal', {
                    url: '/sucursal/{id:[0-9]{1,8}}',
                    templateUrl: appHelper.viewPath("organizacion/sucursal/form-editar-sucursal"),
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
                    roles: ['ADMIN'],
                }).state('app.admin.organizacion.estructura.editarSucursal.resumen', {
                    url: "/resumen",
                    templateUrl: appHelper.viewPath("organizacion/sucursal/form-resumen"),
                    controller: 'SucursalResumenCtrl_Admin',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN'],
                }).state('app.admin.organizacion.estructura.editarSucursal.datosPrincipales', {
                    url: "/principal",
                    templateUrl: appHelper.viewPath("organizacion/sucursal/form-datosPrincipales-editar"),
                    controller: 'SucursalDatosPrincipalesCtrl_Admin',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN'],
                }).state('app.admin.organizacion.estructura.editarSucursal.crearAgencia', {
                    url: "/agencia",
                    templateUrl: appHelper.viewPath("organizacion/sucursal/agencia/form-crear-agencia-from-sucursal"),
                    controller: 'CrearAgenciaFromSucursalCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN']
                }).state('app.admin.organizacion.estructura.editarSucursal.crearAgencia.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewPath("organizacion/sucursal/agencia/form-datosPrincipales-from-sucursal"),
                    controller: 'AgenciaDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMIN']
                })

                .state('app.gerentegeneral.organizacion.estructura.buscarSucursal', {
                    url: '/sucursal/buscar',
                    templateUrl: appHelper.viewPath('organizacion/sucursal/form-buscar-sucursal'),
                    controller: 'BuscarSucursalCtrl_Gerentegeneral',
                    module: 'ORGANIZACION',
                    roles: ['GERENTE_GENERAL'],
                }).state('app.gerentegeneral.organizacion.estructura.crearSucursal', {
                    url: '/sucursal',
                    templateUrl: appHelper.viewPath("organizacion/sucursal/form-crear-sucursal"),
                    controller: 'CrearSucursalCtrl_Gerentegeneral',
                    module: 'ORGANIZACION',
                    roles: ['GERENTE_GENERAL']
                }).state('app.gerentegeneral.organizacion.estructura.crearSucursal.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewPath("organizacion/sucursal/form-datosPrincipales-crear"),
                    controller: 'SucursalDatosPrincipalesCtrl_Gerentegeneral',
                    module: 'ORGANIZACION',
                    roles: ['GERENTE_GENERAL']
                }).state('app.gerentegeneral.organizacion.estructura.editarSucursal', {
                    url: '/sucursal/{id:[0-9]{1,8}}',
                    templateUrl: appHelper.viewPath("organizacion/sucursal/form-editar-sucursal"),
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
                    roles: ['GERENTE_GENERAL'],
                }).state('app.gerentegeneral.organizacion.estructura.editarSucursal.resumen', {
                    url: "/resumen",
                    templateUrl: appHelper.viewPath("organizacion/sucursal/form-resumen"),
                    controller: 'SucursalResumenCtrl_Gerentegeneral',
                    module: 'ORGANIZACION',
                    roles: ['GERENTE_GENERAL'],
                }).state('app.gerentegeneral.organizacion.estructura.editarSucursal.datosPrincipales', {
                    url: "/principal",
                    templateUrl: appHelper.viewPath("organizacion/sucursal/form-datosPrincipales-editar"),
                    controller: 'SucursalDatosPrincipalesCtrl_Gerentegeneral',
                    module: 'ORGANIZACION',
                    roles: ['GERENTE_GENERAL'],
                }).state('app.gerentegeneral.organizacion.estructura.editarSucursal.crearAgencia', {
                    url: "/agencia",
                    templateUrl: appHelper.viewPath("organizacion/sucursal/agencia/form-crear-agencia-from-sucursal"),
                    controller: 'CrearAgenciaFromSucursalCtrl',
                    module: 'ORGANIZACION',
                    roles: ['GERENTE_GENERAL']
                }).state('app.gerentegeneral.organizacion.estructura.editarSucursal.crearAgencia.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewPath("organizacion/sucursal/agencia/form-datosPrincipales-from-sucursal"),
                    controller: 'AgenciaDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['GERENTE_GENERAL']
                })

                .state('app.administradorgeneral.organizacion.estructura.editarSucursal', {
                    url: '/sucursal/{id:[0-9]{1,8}}',
                    templateUrl: appHelper.viewPath("organizacion/sucursal/form-editar-sucursal"),
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
                    roles: ['ADMINISTRADOR_GENERAL'],
                }).state('app.administradorgeneral.organizacion.estructura.editarSucursal.resumen', {
                    url: "/resumen",
                    templateUrl: appHelper.viewPath("organizacion/sucursal/form-resumen"),
                    controller: 'SucursalResumenCtrl_Administradorgeneral',
                    module: 'ORGANIZACION',
                    roles: ['ADMINISTRADOR_GENERAL'],
                }).state('app.administradorgeneral.organizacion.estructura.editarSucursal.datosPrincipales', {
                    url: "/principal",
                    templateUrl: appHelper.viewPath("organizacion/sucursal/form-datosPrincipales-editar"),
                    controller: 'SucursalDatosPrincipalesCtrl_Administradorgeneral',
                    module: 'ORGANIZACION',
                    roles: ['ADMINISTRADOR_GENERAL'],
                }).state('app.administradorgeneral.organizacion.estructura.editarSucursal.crearAgencia', {
                    url: "/agencia",
                    templateUrl: appHelper.viewPath("organizacion/sucursal/agencia/form-crear-agencia-from-sucursal"),
                    controller: 'CrearAgenciaFromSucursalCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMINISTRADOR_GENERAL']
                }).state('app.administradorgeneral.organizacion.estructura.editarSucursal.crearAgencia.datosPrincipales', {
                    url: '/principal',
                    templateUrl: appHelper.viewPath("organizacion/sucursal/agencia/form-datosPrincipales-from-sucursal"),
                    controller: 'AgenciaDatosPrincipalesCtrl',
                    module: 'ORGANIZACION',
                    roles: ['ADMINISTRADOR_GENERAL']
                })
                /*SUBMENU RRHH*/
                ;

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

        app.factory('errorInterceptor', function($q, $window, $rootScope, $location, Notifications) {
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





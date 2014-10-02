'use strict';

var consoleBaseUrl = window.location.href;
consoleBaseUrl = consoleBaseUrl.substring(0, consoleBaseUrl.indexOf("/app"));
consoleBaseUrl = consoleBaseUrl + "/app";
var configUrl = consoleBaseUrl + "/config";

var auth = {};
var authUrl = window.location.href.substring(0, window.location.href.indexOf('/admin/'));

var module = angular.module('cajaApp', [
    'cajaApp.services',
    'ui.bootstrap',
    'ui.router',

    /*Rest SDK*/,
    'ubigeoSDK',
    'personaSDK',

    /*Persona module*/
    'persona.controllers',

    /*Common module*/
    'common.controllers'

]);
var resourceRequests = 0;
var loadingTimer = -1;

angular.element(document).ready(function ($http) {
    var keycloakAuth = new Keycloak(configUrl);

    keycloakAuth.onAuthLogout = function() {
        location.reload();
    };

    keycloakAuth.init({ onLoad: 'login-required' }).success(function () {
        auth.authz = keycloakAuth;
        keycloakAuth.loadUserProfile().success(function(profile) {

            auth.user = profile;

            module.factory('Auth', function() {
                return auth;
            });

            angular.bootstrap(document, ["cajaApp"]);

        }).error(function() {
            alert('No se pudo cargar el usuario en session');
        });

    }).error(function () {
        window.location.reload();
    });
});

module.factory('authInterceptor', function($q, Auth) {
    return {
        request: function (config) {
            if (!config.url.match(/.html$/)) {
                var deferred = $q.defer();
                if (Auth.authz.token) {
                    Auth.authz.updateToken(5).success(function () {
                        config.headers = config.headers || {};
                        config.headers.Authorization = 'Bearer ' + Auth.authz.token;

                        deferred.resolve(config);
                    }).error(function () {
                        location.reload();
                    });
                }
                return deferred.promise;
            } else {
                return config;
            }
        }
    };
});




module.config([ '$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: 'views/themplate/themplate01.html'
        })
        .state('app', {
            abstract: true,
            url: '/app',
            templateUrl: 'views/themplate/themplate02.html'
        })
        .state('app.caja', {
            url: '/caja',
            views: {
                "viewMenu":{
                    controller: function($scope){
                        $scope.menus = [
                            {'name':'Panel Control', 'state': '', header: true},
                            {'name':'Panel', 'state': 'app.caja.panelControl', header: false},

                            {'name':'Caja', 'state': '', header: true},
                            {'name':'Abrir', 'state': 'app.caja.abrirCaja', header: false},
                            {'name':'Cerrar', 'state': 'app.caja.cerrarCaja', header: false},
                            {'name':'Pendientes', 'state': 'app.caja.pendiente', header: false},
                            {'name':'Historial', 'state': 'app.caja.historial', header: false},

                            {'name':'Transacciones Internas', 'state': '', header: true},
                            {'name':'Transaccion con Boveda', 'state': 'app.caja.buscarTransaccionBovedaCaja', header: false},
                            {'name':'Transaccion con Caja', 'state': 'app.caja.buscarTransaccionCajaCaja', header: false}
                        ];
                    }
                },
                "viewContent":{
                    template: '<div ui-view style="min-height: 472px;">prueba</br></br></div>'
                }
            }
        })
        .state('app.transaccion', {
            url: "/transaccion",
            views: {
                "viewMenu":{
                    controller: function($scope){
                        $scope.menus = [
                            {'name':'Cuenta aporte', 'state': '', header: true},
                            {'name':'Aporte', 'state': 'app.transaccion.aporte', header: false},

                            {'name':'Cuenta Bancaria', 'state': '', header: true},
                            {'name':'Deposito/Retiro', 'state': 'app.transaccion.depositoRetiro', header: false},
                            {'name':'Transferencia', 'state': 'app.transaccion.transferencia', header: false},
                            {'name':'Compra/Venta', 'state': 'app.transaccion.compraVenta', header: false},

                            {'name':'Transacciones Internas', 'state': '', header: true},
                            {'name':'Transaccion con Boveda', 'state': 'app.caja.buscarTransaccionBovedaCaja', header: false},
                            {'name':'Transaccion con Caja', 'state': 'app.caja.buscarTransaccionCajaCaja', header: false},

                            {'name':'Historial', 'state': '', header: true},
                            {'name':'Buscar Transacción', 'state': 'app.transaccion.buscarTransaccion', header: false}
                        ];
                    }
                },
                "viewContent":{
                    template: '<div>sss</div>'
                }
            }
        })
        .state('app.socio', {
            url: "/socio",
            views: {
                "viewMenu":{
                    controller: function($scope){
                        $scope.menus = [
                            {'name':'Socio', 'state': '', header: true},
                            {'name':'Buscar', 'state': 'app.socio.buscarSocio', header: false},

                            {'name':'Cuenta Aporte', 'state': '', header: true},
                            {'name':'Nuevo', 'state': 'app.socio.crearSocio', header: false},

                            {'name':'Cuentas Bancarias', 'state': '', header: true},
                            {'name':'Nuevo', 'state': 'app.socio.crearCuentaBancaria', header: false},
                            {'name':'Buscar', 'state': 'app.socio.buscarCuentaBancaria', header: false}
                        ];
                    }
                },
                "viewContent":{
                    template: 'prueba'
                }
            }
        })
        .state('app.administracion', {
            url: "/administracion",
            views: {
                "viewMenu":{
                    controller: function($scope){
                        $scope.menus = [
                            {'name':'Persona Natural', 'state': '', header: true},
                            {'name':'Nuevo', 'state': 'app.administracion.crearPersonaNatural', header: false},
                            {'name':'Buscar', 'state': 'app.administracion.buscarPersonaNatural', header: false},

                            {'name':'Persona Jurídica', 'state': '', header: true},
                            {'name':'Nuevo', 'state': 'app.administracion.crearPersonaJuridica', header: false},
                            {'name':'Buscar', 'state': 'app.administracion.buscarPersonaJuridica', header: false}
                        ];
                    }
                },
                "viewContent":{
                    templateUrl: 'views/themplate/themplate02-content.html',
                    controller: function($scope){
                        $scope.themplate = {};
                        $scope.themplate.header = 'Administracion';
                    }
                }
            }
        })

        .state('app.administracion.buscarPersonaNatural', {
            url: '/persona/natural/buscar',
            views: {
                "viewContent":{
                    templateUrl: 'views/cajero/persona/natural/buscarPersonaNatural.html',
                    controller: 'BuscarPersonaNaturalController'
                }
            }
        })
        .state('app.administracion.crearPersonaNatural', {
            url: "/persona/natural?tipoDocumento&numeroDocumento",
            templateUrl: "views/persona/natural/crearPersonaNatural.html",
            controller: function($scope, $stateParams) {
                $scope.themplate.header = 'Crear persona natural';

                $scope.params = {};
                $scope.params.idTipoDocumento = $stateParams.tipoDocumento;
                $scope.params.numeroDocumento = $stateParams.numeroDocumento;
            }
        })
        .state('app.administracion.editarPersonaNatural', {
            url: "/persona/natural/:id",
            views: {
                "viewContent":{
                    templateUrl: "views/cajero/persona/natural/editarPersonaNatural.html",
                    controller: function($scope, $stateParams) {
                        $scope.id = $stateParams.id;
                    }
                }
            }
        });
} ]);

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

module.factory('errorInterceptor', function($q, $window, $rootScope, $location, Notifications, Auth) {
    return function(promise) {
        return promise.then(function(response) {
            return response;
        }, function(response) {
            if (response.status == 401) {
                Auth.authz.logout();
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
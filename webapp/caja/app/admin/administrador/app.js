'use strict';

var consoleBaseUrl = window.location.href;
consoleBaseUrl = consoleBaseUrl.substring(0, consoleBaseUrl.indexOf("/app/admin/administrador"));
consoleBaseUrl = consoleBaseUrl + "/app/admin/administrador";
var configUrl = consoleBaseUrl + "/config";
var logoutUrl = consoleBaseUrl + "/logout";
var auth = {};
var logout = function(){
    console.log('*** LOGOUT');
    window.location = logoutUrl;
};

var authUrl = window.location.href;
authUrl = window.location.href.substring(0,  authUrl.indexOf('/admin/administrador'));

var module = angular.module('sistcoop', [
    'ngSanitize',
    'ngMessages',

    'persona',

    'ui.bootstrap',
    'ui.router',
    'ui.select',
    'ui.utils',
    'ui.grid',
    'ui.grid.edit'
]);

var resourceRequests = 0;
var loadingTimer = -1;
/*
angular.element(document).ready(function ($http) {
    var keycloakAuth = new Keycloak(configUrl);
    keycloakAuth.onAuthLogout = function() {
        window.location.reload();
    };
    keycloakAuth.init({ onLoad: 'login-required' }).success(function (authenticated) {
        if(keycloakAuth.realmAccess === undefined) {
            keycloakAuth.logout();
        } else {
            auth.authz = keycloakAuth;
            module.factory('Auth', function() {
                return auth;
            });
            angular.bootstrap(document, ["sistcoop"]);
        }
    }).error(function () {
         window.location.reload();
    });
});*/

angular.element(document).ready(function ($http) {
    var keycloakAuth = new Keycloak(configUrl);

    keycloakAuth.onAuthLogout = function() {
        location.reload();
    };

    keycloakAuth.init({ onLoad: 'login-required' }).success(function () {
        auth.authz = keycloakAuth;
        module.factory('Auth', function() {
            return auth;
        });
        angular.bootstrap(document, ["sistcoop"]);
    }).error(function () {
        window.location.reload();
    });
});

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

module.config(function(uiSelectConfig, restmodProvider) {
    uiSelectConfig.theme = 'bootstrap';

    restmodProvider.rebase({
        $config: {
            urlPrefix: 'http://localhost:8080/cajaApp/app'
        }
    });
});

module.config(function(restmodProvider) {
    restmodProvider.rebase({
        $config: {
            urlPrefix: 'http://localhost:8080/cajaApp/app'
        }
    });
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
                    templateUrl: '../../views/themplate/themplate02-content.html',
                    controller: function($scope){
                        $scope.themplate = {};
                        $scope.themplate.header = 'Administracion';
                    }
                }
            }
        })

        .state('app.administracion.buscarPersonaNatural', {
            url: '/persona/natural/buscar',
            templateUrl: "../../views/persona/natural/buscarPersonaNatural.html",
            controller: function($scope, $stateParams) {
                $scope.themplate.header = 'Buscar persona natural';
            }
        })
        .state('app.administracion.crearPersonaNatural', {
            url: "/persona/natural?documento&numero",
            templateUrl: "../../views/persona/natural/crearPersonaNatural.html",
            controller: function($scope, $stateParams) {
                $scope.themplate.header = 'Crear persona natural';

                $scope.params = {};
                $scope.params.tipoDocumento = $stateParams.documento;
                $scope.params.numeroDocumento = $stateParams.numero;
            }
        })
        .state('app.administracion.editarPersonaNatural', {
            url: "/persona/natural/:id",
            templateUrl: "../../views/persona/natural/editarPersonaNatural.html",
            controller: function($scope, $stateParams) {
                $scope.themplate.header = 'Editar persona natural';

                $scope.params = {};
                $scope.params.id = $stateParams.id;
            }
        })

        .state('app.administracion.buscarPersonaJuridica', {
            url: '/persona/juridica/buscar',
            templateUrl: "../../views/persona/juridica/buscarPersonaJuridica.html",
            controller: function($scope, $stateParams) {
                $scope.themplate.header = 'Buscar persona juridica';
            }
        })
        .state('app.administracion.crearPersonaJuridica', {
            url: "/persona/juridica?documento&numero",
            templateUrl: "../../views/persona/juridica/crearPersonaJuridica.html",
            controller: function($scope, $stateParams) {
                $scope.themplate.header = 'Crear persona juridica';

                $scope.params = {};
                $scope.params.tipoDocumento = $stateParams.documento;
                $scope.params.numeroDocumento = $stateParams.numero;
            }
        })
        .state('app.administracion.editarPersonaJuridica', {
            url: "/persona/juridica/:id",
            views: {
                "viewContent":{
                    templateUrl: "../../views/persona/juridica/editarPersonaJuridica.html",
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
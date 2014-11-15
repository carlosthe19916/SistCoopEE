'use strict';

var consoleBaseUrl = window.location.href;
consoleBaseUrl = consoleBaseUrl.substring(0, consoleBaseUrl.indexOf("/app/admin"));
consoleBaseUrl = consoleBaseUrl + "/app/admin";
var configUrl = consoleBaseUrl + "/config";
var logoutUrl = consoleBaseUrl + "/logout";
var auth = {};
var logout = function(){
    console.log('*** LOGOUT');
    window.location = logoutUrl;
};

var authUrl = window.location.href;
authUrl = window.location.href.substring(0,  authUrl.indexOf('/admin'));

var module = angular.module('sistcoop', [
    'ngSanitize',
    'ngMessages',
    //'ngAnimate',

    'persona',
    'ubigeo',
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
    //RestangularProvider.setDefaultHttpFields({cache: true});

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

module.config(['$provide', function($provide){
    var profile = angular.copy(window.auth.authz);

    //modulesNames = ['PERSONA', 'UBIGEO'];
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

    profile.hasRole = function(moduleName, roles){
        var module = getModule(moduleName);
        if(Array.isArray(roles)){
            var result = true;
            for(var i = 0; i< roles.length; i++){
                if(module.roles.assigned.indexOf(roles[i]) < 0){
                    result = false;
                    break;
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

    $urlRouterProvider.when('/app/administracion/persona/juridica', '/app/administracion/persona/juridica/principal');
    $urlRouterProvider.when('/app/administracion/persona/juridica/{id:[0-9]{1,8}}', '/app/administracion/persona/juridica/{id:[0-9]{1,8}}/resumen');
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
        })

        .state('app.administracion.buscarPersonaNatural', {
            url: '/persona/natural/buscar',
            templateUrl: "../../views/persona/natural/buscarPersonaNatural.html",
            controller: function($scope) {
                $scope.themplate.header = 'Buscar persona natural';
            },
            module: 'PERSONA',
            roles: ['PUBLIC']
        })
        .state('app.administracion.crearPersonaNatural', {
            url: "/persona/natural?documento&numero",
            templateUrl: "../../views/persona/natural/crearPersonaNatural.html",
            controller: function($scope, $stateParams) {
                $scope.themplate.header = 'Crear persona natural';

                $scope.params = {};
                $scope.params.tipoDocumento = $stateParams.documento;
                $scope.params.numeroDocumento = $stateParams.numero;
            },
            module: 'PERSONA',
            roles: ['USER']
        })
        .state('app.administracion.editarPersonaNatural', {
            url: "/persona/natural/:id",
            templateUrl: "../../views/persona/natural/editarPersonaNatural.html",
            resolve: {
                persona: function($state, $stateParams, Storage, PersonaNatural) {
                    var savedObject = Storage.getObject();
                    if(savedObject){
                        return savedObject;
                    } else {
                        return PersonaNatural.$find($stateParams.id);
                    }
                }
            },
            controller: function($scope, $stateParams, persona) {
                $scope.themplate.header = 'Editar persona natural';

                $scope.params = {};
                $scope.params.id = $stateParams.id;
                $scope.params.object = persona;
            },
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
            resolve: {
                persona: function($state, Storage) {
                    return Storage.getObject();
                }
            },
            controller: function($scope, $stateParams, persona) {
                $scope.themplate.header = 'Crear persona juridica';

                $scope.params = {};
                $scope.params.tipoDocumento = $stateParams.documento;
                $scope.params.numeroDocumento = $stateParams.numero;
                $scope.params.object = persona;
            },
            module: 'PERSONA',
            roles: ['USER']
        })
        .state('app.administracion.crearPersonaJuridica.principal', {
            url: "/principal",
            templateUrl: "../../views/persona/juridica/form-principal.html",
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
                persona: function($state, $stateParams, Storage, PersonaJuridica) {
                    var savedObject = Storage.getObject();
                    if(savedObject){
                        return savedObject;
                    } else {
                        return PersonaJuridica.$find($stateParams.id);
                    }
                }
            },
            controller: function($scope, $stateParams, persona) {
                $scope.themplate.header = 'Editar persona juridica';

                $scope.params = {};
                $scope.params.id = $stateParams.id;
                $scope.params.object = persona;
            },
            module: 'PERSONA',
            roles: ['ADMIN']
        })
        .state('app.administracion.editarPersonaJuridica.resumen', {
            url: "/resumen",
            templateUrl: "../../views/persona/juridica/form-resumen.html",
            module: 'PERSONA',
            roles: ['USER']
        })
        .state('app.administracion.editarPersonaJuridica.principal', {
            url: "/principal",
            templateUrl: "../../views/persona/juridica/form-principal.html",
            module: 'PERSONA',
            roles: ['USER']
        })
        .state('app.administracion.editarPersonaJuridica.representante', {
            url: "/representante",
            templateUrl: "../../views/persona/juridica/form-representante.html",
            module: 'PERSONA',
            roles: ['USER']
        })
        .state('app.administracion.editarPersonaJuridica.accionista', {
            url: "/accionistas",
            templateUrl: "../../views/persona/juridica/form-accionista.html",
            module: 'PERSONA',
            roles: ['USER']
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

module.run(function($rootScope, activeProfile) {
    $rootScope.$on('$stateChangeStart',
        function(event, toState, toParams, fromState, fromParams){
            if(toState.module && toState.roles){
                if(!activeProfile.hasRole(toState.module, toState.roles)){
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

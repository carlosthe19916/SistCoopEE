'use strict';

var consoleBaseUrl = window.location.href;
consoleBaseUrl = consoleBaseUrl.substring(0, consoleBaseUrl.indexOf("/console"));
consoleBaseUrl = consoleBaseUrl + "/console";
var configUrl = consoleBaseUrl + "/config";

var auth = {};
var authUrl = window.location.href.substring(0, window.location.href.indexOf('/admin/'));

var module = angular.module('keycloak', [ 'keycloak.services', 'keycloak.loaders' ]);
var resourceRequests = 0;
var loadingTimer = -1;

angular.element(document).ready(function ($http) {
    var keycloakAuth = new Keycloak(configUrl);

    keycloakAuth.onAuthLogout = function() {
        //location.reload();
    };

    keycloakAuth.init({ onLoad: 'login-required' }).success(function () {
        auth.authz = keycloakAuth;
        module.factory('Auth', function() {
            return auth;
        });
        angular.bootstrap(document, ["keycloak"]);
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

module.config([ '$routeProvider', function($routeProvider) {

    $routeProvider
        .when('/create/realm', {
            templateUrl : 'partials/realm-create.html',
            resolve : {

            },
            controller : 'RealmCreateCtrl'
        });
} ]);

module.config(function($httpProvider) {
    $httpProvider.responseInterceptors.push('errorInterceptor');

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

    $httpProvider.responseInterceptors.push('spinnerInterceptor');
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

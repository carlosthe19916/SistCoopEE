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

    'persona',

    'ui.bootstrap',
    'ui.router',
    'ui.select',
    'ui.utils',
    'ui.grid',
    'ui.grid.edit',
    'ui.grid.selection'
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

module.config(['$provide', function($provide){
    var profile = angular.copy(window.auth.authz);

    var apiModules = [
        {
            name: 'persona',
            roles: {
                available: ['ADMIN', 'USER', 'PUBLIC'],
                assigned: profile.resourceAccess.PERSONA_RESTAPI === undefined ? [] : profile.resourceAccess.PERSONA_RESTAPI.roles
            }
        },
        {name: 'ubigeo'}
    ];

    var operations = ['select', 'create', 'update', 'delete'];

    profile.hasPermission = function(module, operation){
        if(module.toLowerCase() == 'persona'){
            if(operation.toLowerCase() == operations[0])
                return apiModules[0].roles.assigned.indexOf('PUBLIC') >= 0;
            else if(operation.toLowerCase() == operations[1])
                return apiModules[0].roles.assigned.indexOf('USER') >= 0;
            else if(operation.toLowerCase() == operations[2])
                return apiModules[0].roles.assigned.indexOf('ADMIN') >= 0;
            else if(operation.toLowerCase() == operations[3])
                return apiModules[0].roles.assigned.indexOf('ADMIN') >= 0;
            else
                return false;
        } else {
            if(module.toLowerCase() == 'ubigeo'){

            } else {
                return false;
            }
        }
    };

    $provide.constant('activeProfile', profile);
}]);

module.config(function(uiSelectConfig) {
    uiSelectConfig.theme = 'bootstrap';
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

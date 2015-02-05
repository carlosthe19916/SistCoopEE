define(['./app'], function(app) {
    'use strict';

    var resourceRequests = 0;
    var loadingTimer = -1;

    app.factory('authInterceptor', function($q, Auth) {
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

    app.config(function($httpProvider) {
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

    app.factory('errorInterceptor', function($q, $window, $rootScope, $location, Notifications, Auth) {
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

    app.factory('spinnerInterceptor', function($q, $window, $rootScope, $location) {
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

    return app;

});
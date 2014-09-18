define(['./module'], function (directives) {
    'use strict';
    directives.factory('authInterceptor', function($q, Auth) {
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
});
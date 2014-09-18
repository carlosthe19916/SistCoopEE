define(['./module'], function (directives) {
    'use strict';
    directives.factory('errorInterceptor', function($q, $window, $rootScope, $location, Notifications, Auth) {
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
});
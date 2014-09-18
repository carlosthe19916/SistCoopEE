define(['./module'], function (directives) {
    'use strict';
    directives.factory('spinnerInterceptor', function($q, $window, $rootScope, $location) {
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
});
(function(window, angular, undefined) {'use strict';

    angular.module('ubigeo.models', ['restangular'])
        .config(function(RestangularProvider) {
            RestangularProvider.setBaseUrl('http://localhost:8080/restapi-ubigeo/rest/v1');

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

        }).factory('Pais', function(Restangular) {
            var url = "paises";
            return {
                $search: function(queryParams){
                    return Restangular.all(url).getList(queryParams).$object;
                }
            };
        });

})(window, window.angular);
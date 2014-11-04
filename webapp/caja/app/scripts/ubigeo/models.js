(function(window, angular, undefined) {'use strict';

    angular.module('ubigeo.models', [])
        .config(function() {
        }).factory('Pais', function(UbigeoRestangular) {
            var url = "paises";
            return {
                $search: function(queryParams){
                    return UbigeoRestangular.all(url).getList(queryParams);
                }
            };
        });

})(window, window.angular);
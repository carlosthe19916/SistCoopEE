(function(window, angular, undefined) {'use strict';

    angular.module('organizacion.models', [])
        .config(function() {
        }).factory('Sucursal', function(OrganizacionRestangular) {
            var url = "sucursales";
            return {
                $search: function(queryParams){
                    return OrganizacionRestangular.all(url).getList(queryParams);
                }
            };
        });

})(window, window.angular);
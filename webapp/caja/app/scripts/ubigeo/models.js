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
        }).factory('Departamento', function(UbigeoRestangular) {
            var url = "departamentos";
            return {
                $search: function(queryParams){
                    return UbigeoRestangular.all(url).getList(queryParams);
                }
            };
        }).factory('Provincia', function(UbigeoRestangular) {
            var url = "provincias";
            return {
                $search: function(queryParams){
                    return UbigeoRestangular.all(url).getList(queryParams);
                }
            };
        }).factory('Distrito', function(UbigeoRestangular) {
            var url = "distritos";
            return {
                $search: function(queryParams){
                    return UbigeoRestangular.all(url).getList(queryParams);
                }
            };
        });

})(window, window.angular);
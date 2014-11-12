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

            UbigeoRestangular.extendModel(url, function(obj) {
                obj.provincias = {
                    $fetch: function(){
                        return UbigeoRestangular.all(url+'/'+obj.codigo+'/provincias').getList();
                    }
                };
                return obj;
            });

            return {
                $search: function(queryParams){
                    return UbigeoRestangular.all(url).getList(queryParams);
                }
            };
        }).factory('Provincia', function(UbigeoRestangular) {
            var url = "provincias";

            /*UbigeoRestangular.extendModel(url, function(obj) {
                obj.distritos = {
                    $fetch: function(codigoDepartamento){
                        return UbigeoRestangular.all('departamentos/'+codigoDepartamento+'/'+url+'/'+obj.codigo+'/distritos').getList();
                    }
                };
                return obj;
            });*/

            return {
                $search: function(queryParams){
                    return UbigeoRestangular.all(url).getList(queryParams);
                },
                distritos: function(codigoDepartamento, codigoProvincia){
                    return UbigeoRestangular.all('departamentos/'+codigoDepartamento+'/'+url+'/'+codigoProvincia+'/distritos').getList();
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
define(['module'], function (module) {
    'use strict';

    module.factory('Provincia', function(UbigeoRestangular) {
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
    });
});
define(['./module'], function (module) {
    'use strict';

    module.factory('Departamento', function(UbigeoRestangular) {
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
    });
});
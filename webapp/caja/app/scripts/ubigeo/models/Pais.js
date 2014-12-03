define(['module'], function (module) {
    'use strict';

    module.factory('Pais', function(UbigeoRestangular) {
        var url = "paises";
        return {
            $search: function(queryParams){
                return UbigeoRestangular.all(url).getList(queryParams);
            }
        };
    });
});
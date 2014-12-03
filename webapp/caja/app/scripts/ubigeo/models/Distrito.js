define(['module'], function (module) {
    'use strict';

    module.factory('Distrito', function(UbigeoRestangular) {
        var url = "distritos";
        return {
            $search: function(queryParams){
                return UbigeoRestangular.all(url).getList(queryParams);
            }
        };
    });
});
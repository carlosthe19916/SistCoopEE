define(['./module'], function (module) {
    'use strict';

    module.factory('Currency', function(UbigeoRestangular) {
        var url = "currencies";
        return {
            $search: function(queryParams){
                return UbigeoRestangular.all(url).getList(queryParams);
            }
        };
    });
});
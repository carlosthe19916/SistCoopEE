define(['./module'], function (module) {
    'use strict';

    module.factory('TipoEmpresa', function(PersonaRestangular) {
        var url = "tiposEmpresa";
        return {
            $search: function(queryParams){
                return PersonaRestangular.all(url).getList(queryParams);
            }
        };
    });
});
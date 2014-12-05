define(['./module'], function (module) {
    'use strict';

    module.factory('TipoPersona', function(PersonaRestangular) {
        var url = "tiposPersona";
        return {
            $search: function(queryParams){
                return PersonaRestangular.all(url).getList(queryParams);
            }
        };
    });
});
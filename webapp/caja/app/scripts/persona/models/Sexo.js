define(['module'], function (module) {
    'use strict';

    module.factory('Sexo', function(PersonaRestangular) {
        var url = "sexos";
        return {
            $search: function(queryParams){
                return PersonaRestangular.all(url).getList(queryParams);
            }
        };
    });
});
define(['module'], function (module) {
    'use strict';

    module.factory('EstadoCivil', function(PersonaRestangular) {
        var url = "estadosCiviles";
        return {
            $search: function(queryParams){
                return PersonaRestangular.all(url).getList(queryParams);
            }
        };
    });
});
define(['./module'], function (module) {
    'use strict';

    module.factory('TipoDocumento', function(PersonaRestangular) {
        var url = "tiposDocumento";
        return {
            $search: function(queryParams){
                return PersonaRestangular.all(url).getList(queryParams);
            },
            $searchByPersonaNatural: function(){
                return PersonaRestangular.all(url).getList({tipoPersona: 'natural'});
            },
            $searchByPersonaJuridica: function(){
                return PersonaRestangular.all(url).getList({tipoPersona: 'juridica'});
            }
        };
    });
});
define(['module'], function (module) {
    'use strict';

    module.factory('Agencia', function(OrganizacionRestangular) {
        var url = "agencias";
        return {
            $getByCodigo: function(codigo){
                return OrganizacionRestangular.one(url+'/codigo/'+codigo).get();
            }
        };
    });
});
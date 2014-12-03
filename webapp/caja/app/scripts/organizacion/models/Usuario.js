define(['module'], function (module) {
    'use strict';

    module.factory('Usuario', function(OrganizacionRestangular) {
        var url = "usuarios";

        return {
            $getSucursal: function(username){
                return OrganizacionRestangular.one(url+'/'+username+'/sucursal').get();
            },
            $getAgencia: function(username){
                return OrganizacionRestangular.one(url+'/'+username+'/agencia').get();
            },
            $getCaja: function(username){
                return OrganizacionRestangular.one(url+'/'+username+'/caja').get();
            },
            $getTrabajador: function(username){
                return OrganizacionRestangular.one(url+'/'+username+'/trabajador').get();
            }
        };
    });
});
define(['./module'], function (module) {
    'use strict';

    module.factory('Agencia', function(OrganizacionRestangular) {
        var url = "agencias";

        return {
            $new: function(id){

            },
            $getByCodigo: function(codigo){
                return OrganizacionRestangular.one(url+'/codigo/'+codigo).get();
            },
            $getBovedas: function(sucursal){
                return OrganizacionRestangular.all('sucursales/'+sucursal.id+'/'+url+'/bovedas').getList();
            },
            $getCajas: function(sucursal){
                return OrganizacionRestangular.all('sucursales/'+sucursal.id+'/'+url+'/cajas').getList();
            }
        };
    });
});
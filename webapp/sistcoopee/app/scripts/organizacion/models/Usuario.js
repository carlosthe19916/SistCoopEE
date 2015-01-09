define(['./module'], function (module) {
    'use strict';

    module.factory('Usuario', function(OrganizacionRestangular) {
        var url = "usuarios";

        OrganizacionRestangular.extendModel(url, function(obj) {
            obj.$getAgencias = function(){
                return OrganizacionRestangular.all('sucursales/'+this.id+'/agencias').getList();
            };
            return obj;
        });

        return {
            $find: function(username){
                return OrganizacionRestangular.one(url, username).get();
            },
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
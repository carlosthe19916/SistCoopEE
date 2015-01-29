define(['./module'], function (module) {
    'use strict';

    module.factory('Sucursal', function(OrganizacionRestangular) {
        var url = "sucursales";

        OrganizacionRestangular.extendModel(url, function(obj) {
            obj.$save = function() {
                return OrganizacionRestangular.one(url, this.id).customPUT(OrganizacionRestangular.copy(this),'',{},{});
            };
            obj.$addAgencia = function(agencia){
                return OrganizacionRestangular.all(url+'/'+this.id+'/agencias').post(OrganizacionRestangular.copy(agencia));
            };
            obj.$getAgencias = function(){
                return OrganizacionRestangular.all(url+'/'+this.id+'/agencias').getList();
            };
            obj.$desactivar = function(){
                return OrganizacionRestangular.all(url+'/'+this.id+'/desactivar').post();
            };
            return obj;
        });

        return {
            $new: function(id){
                return {
                    id: id,
                    $getAgencias: function(){
                        return OrganizacionRestangular.all(url+'/'+this.id+'/agencias').getList();
                    }
                }
            },
            $build: function(){
                return {
                    id: undefined,
                    $save: function(){
                        return OrganizacionRestangular.all(url).post(angular.copy(this));
                    }
                }
            },
            $search: function(queryParams){
                return OrganizacionRestangular.all(url).getList(queryParams);
            },
            $find: function(id){
                return OrganizacionRestangular.one(url, id).get();
            }
        };
    });
});
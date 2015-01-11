define(['./module'], function (module) {
    'use strict';

    module.factory('Trabajador', function(OrganizacionRestangular) {
        var url = "trabajadores";

        OrganizacionRestangular.extendModel(url, function(obj) {
            obj.$save = function() {
                return OrganizacionRestangular.one(url, this.id).customPUT({'trabajador': OrganizacionRestangular.copy(this)},'',{},{});
            };
            obj.$addCaja = function(caja){
                return OrganizacionRestangular.all(url+'/'+this.id+'/cajas').post({'caja': OrganizacionRestangular.copy(caja)});
            };
            obj.$desactivar = function(){
                return OrganizacionRestangular.all(url+'/'+this.id+'/desactivar').post();
            };
            obj.$getCajas = function() {
                return OrganizacionRestangular.all(url+'/'+this.id+'/cajas').getList();
            };
            return obj;
        });

        return {
            $build: function(){
                return {
                    id: undefined,
                    $save: function(){
                        return OrganizacionRestangular.all(url).post({'trabajador': angular.copy(this)});
                    }
                }
            },
            $new: function(id){
                return {
                    id: id
                }
            },
            $find: function(id){
                return OrganizacionRestangular.one(url, id).get();
            }
        };
    });
});
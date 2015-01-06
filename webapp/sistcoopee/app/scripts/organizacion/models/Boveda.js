define(['./module'], function (module) {
    'use strict';

    module.factory('Boveda', function(OrganizacionRestangular) {
        var url = "bovedas";

        OrganizacionRestangular.extendModel(url, function(obj) {
            obj.$save = function() {
                return OrganizacionRestangular.one(url, this.id).customPUT({'boveda': OrganizacionRestangular.copy(this)},'',{},{});
            };
            obj.$abrir = function() {
                return OrganizacionRestangular.all(url+'/'+this.id+'/abrir').post();
            };
            obj.$cerrar = function() {
                return OrganizacionRestangular.all(url+'/'+this.id+'/cerrar').post();
            };
            obj.$getCajas = function() {
                return OrganizacionRestangular.all(url+'/'+this.id+'/cajas').getList();
            };
            obj.$getDetalle = function() {
                return OrganizacionRestangular.all(url+'/'+this.id+'/detalle').getList();
            };
            return obj;
        });

        return {
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
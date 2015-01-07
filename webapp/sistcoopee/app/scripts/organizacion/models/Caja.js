define(['./module'], function (module) {
    'use strict';

    module.factory('Caja', function(OrganizacionRestangular) {
        var url = "cajas";

        OrganizacionRestangular.extendModel(url, function(obj) {
            obj.$save = function() {
                return OrganizacionRestangular.one(url, this.id).customPUT({'caja': OrganizacionRestangular.copy(this)},'',{},{});
            };
            obj.$abrir = function() {
                return OrganizacionRestangular.all(url+'/'+this.id+'/abrir').post();
            };
            obj.$cerrar = function() {
                return OrganizacionRestangular.all(url+'/'+this.id+'/cerrar').post();
            };
            obj.$getBovedas = function() {
                return OrganizacionRestangular.all(url+'/'+this.id+'/bovedas').getList();
            };
            obj.$getDetalle = function() {
                return OrganizacionRestangular.all(url+'/'+this.id+'/detalle').getList();
            };
            obj.$desactivar = function(){
                return OrganizacionRestangular.all(url+'/'+this.id+'/desactivar').post();
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
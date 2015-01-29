define(['./module'], function (module) {
    'use strict';

    module.factory('Caja', function(OrganizacionRestangular) {
        var url = "cajas";

        OrganizacionRestangular.extendModel(url, function(obj) {
            obj.$save = function() {
                return OrganizacionRestangular.one(url, this.id).customPUT(OrganizacionRestangular.copy(this),'',{},{});
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
            obj.$getTrabajadores = function() {
                return OrganizacionRestangular.all(url+'/'+this.id+'/trabajadores').getList();
            };
            obj.$getDetalle = function() {
                return OrganizacionRestangular.all(url+'/'+this.id+'/detalle').getList();
            };
            obj.$addBoveda = function(boveda){
                return OrganizacionRestangular.all(url+'/'+this.id+'/bovedas').post(OrganizacionRestangular.copy(boveda));
            };
            obj.$desactivarBoveda = function(idBoveda){
                return OrganizacionRestangular.all(url+'/'+this.id+'/bovedas/'+idBoveda+'/desactivar').post();
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
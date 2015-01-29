define(['./module'], function (module) {
    'use strict';

    module.factory('Trabajador', function(OrganizacionRestangular) {
        var url = "trabajadores";

        OrganizacionRestangular.extendModel(url, function(obj) {
            obj.$save = function() {
                return OrganizacionRestangular.one(url, this.id).customPUT(OrganizacionRestangular.copy(this),'',{},{});
            };
            obj.$addCaja = function(caja){
                return OrganizacionRestangular.all(url+'/'+this.id+'/cajas').post(OrganizacionRestangular.copy(caja));
            };
            obj.$removeCaja = function(caja){
                return OrganizacionRestangular.one(url+'/'+this.id+'/cajas').remove();
            };
            obj.$desactivar = function(){
                return OrganizacionRestangular.all(url+'/'+this.id+'/desactivar').post();
            };
            return obj;
        });

        return {
            $build: function(){
                return {
                    id: undefined,
                    $save: function(){
                        return OrganizacionRestangular.all(url).post(angular.copy(this));
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
            },
            $findByTipoNumeroDocumento: function(tipoDocumento, numeroDocumento){
                return OrganizacionRestangular.one(url+'/buscar').get({tipoDocumento: tipoDocumento, numeroDocumento: numeroDocumento});
            }
        };
    });
});
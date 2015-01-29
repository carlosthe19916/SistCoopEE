define(['./module'], function (module) {
    'use strict';

    module.factory('Agencia', function(OrganizacionRestangular) {
        var url = "agencias";

        OrganizacionRestangular.extendModel(url, function(obj) {
            obj.$save = function() {
                return OrganizacionRestangular.one(url, this.id).customPUT(OrganizacionRestangular.copy(this),'',{},{});
            };
            obj.$getBovedas = function(){
                return OrganizacionRestangular.all(url+'/'+this.id+'/bovedas').getList();
            };
            obj.$getCajas = function(){
                return OrganizacionRestangular.all(url+'/'+this.id+'/cajas').getList();
            };
            obj.$getTrabajadores = function(queryParams){
                return OrganizacionRestangular.all(url+'/'+this.id+'/trabajadores').getList(queryParams);
            };
            obj.$addBoveda = function(boveda){
                return OrganizacionRestangular.all(url+'/'+this.id+'/bovedas').customPOST(OrganizacionRestangular.copy(boveda),'',{},{});
            };
            obj.$addCaja = function(caja){
                return OrganizacionRestangular.all(url+'/'+this.id+'/cajas').customPOST(OrganizacionRestangular.copy(caja),'',{},{});
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
                    $getBovedas: function(){
                        return OrganizacionRestangular.all(url + '/' + this.id + '/bovedas').getList();
                    },
                    $getCajas:function() {
                        return OrganizacionRestangular.all(url + '/' + this.id + '/cajas').getList();
                    },
                    $getTrabajadores:function() {
                        return OrganizacionRestangular.all(url + '/' + this.id + '/trabajadores').getList();
                    },
                    $addBoveda: function(boveda){
                        return OrganizacionRestangular.all(url+'/'+this.id+'/bovedas').customPOST(OrganizacionRestangular.copy(boveda),'',{},{});
                    },
                    $addCaja: function(caja){
                        return OrganizacionRestangular.all(url+'/'+this.id+'/cajas').customPOST(OrganizacionRestangular.copy(caja),'',{},{});
                    }
                }
            },
            $getByCodigo: function(codigo){
                return OrganizacionRestangular.one(url+'/codigo/'+codigo).get();
            },
            $find: function(id){
                return OrganizacionRestangular.one(url, id).get();
            }
        };
    });
});
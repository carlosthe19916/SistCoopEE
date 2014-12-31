define(['./module'], function (module) {
    'use strict';

    module.factory('Agencia', function(OrganizacionRestangular) {
        var url = "agencias";

        OrganizacionRestangular.extendModel(url, function(obj) {
            obj.$save = function() {
                return OrganizacionRestangular.one(url, this.id).customPUT({'agencia': OrganizacionRestangular.copy(this)},'',{},{});
            };
            obj.$getBovedas = function(){
                return OrganizacionRestangular.all(url+'/'+this.id+'/bovedas').getList();
            };
            obj.$getCajas = function(){
                return OrganizacionRestangular.all(url+'/'+this.id+'/cajas').getList();
            };
            obj.$addBoveda = function(boveda){
                return OrganizacionRestangular.all(url+'/'+this.id+'/bovedas').customPOST({'boveda': boveda},'',{},{});
            };
            obj.$addCaja = function(caja){
                return OrganizacionRestangular.all(url+'/'+this.id+'/cajas').post(caja);
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
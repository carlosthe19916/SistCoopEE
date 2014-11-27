(function(window, angular, undefined) {'use strict';

    angular.module('organizacion.models', [])
        .config(function() {

        }).factory('Sucursal', function(OrganizacionRestangular) {
            var url = "sucursales";

            OrganizacionRestangular.extendModel(url, function(obj) {
                obj.$save = function() {
                    return OrganizacionRestangular.one(url, this.id).customPUT({'sucursal': OrganizacionRestangular.copy(this)},'',{},{});
                };
                obj.$addAgencia = function(agencia){
                    return OrganizacionRestangular.all(url+'/'+this.id+'/agencias').post({'agencia': OrganizacionRestangular.copy(agencia)});
                };
                obj.$getAgencias = function(){
                    return OrganizacionRestangular.all(url+'/'+this.id+'/agencias').getList();
                };
                return obj;
            });

            return {
                $build: function(){
                    return {
                        id: undefined,
                        $save: function(){
                            return OrganizacionRestangular.all(url).post({'sucursal': angular.copy(this)});
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

})(window, window.angular);
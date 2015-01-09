define(['./module'], function (module) {
    'use strict';

    module.factory('Trabajador', function(OrganizacionRestangular) {
        var url = "trabajadores";

        OrganizacionRestangular.extendModel(url, function(obj) {
            obj.$save = function() {
                return OrganizacionRestangular.one(url, this.id).customPUT({'trabajadores': OrganizacionRestangular.copy(this)},'',{},{});
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
                        return OrganizacionRestangular.all(url).post({'trabajadores': angular.copy(this)});
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
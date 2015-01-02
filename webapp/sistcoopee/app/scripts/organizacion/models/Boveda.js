define(['./module'], function (module) {
    'use strict';

    module.factory('Boveda', function(OrganizacionRestangular) {
        var url = "bovedas";

        OrganizacionRestangular.extendModel(url, function(obj) {
            obj.$save = function() {
                return OrganizacionRestangular.one(url, this.id).customPUT({'boveda': OrganizacionRestangular.copy(this)},'',{},{});
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
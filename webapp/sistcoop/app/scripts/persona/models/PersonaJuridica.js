define(['./module'], function (module) {
    'use strict';

    module.factory('PersonaJuridica', function(PersonaRestangular) {
        var url = "personas/juridicas";

        PersonaRestangular.extendModel(url, function(obj) {
            obj.$save = function() {
                return PersonaRestangular.one(url, this.id).customPUT({'personaJuridica': PersonaRestangular.copy(this)},'',{},{});
            };
            obj.$addAccionista = function(accionista){
                return PersonaRestangular.all(url+'/'+this.id+'/accionistas').post({'accionista': PersonaRestangular.copy(accionista)});
            };
            obj.$getAccionistas = function(){
                return PersonaRestangular.all(url+'/'+this.id+'/accionistas').getList();
            };

            return obj;
        });

        return {
            $build: function(){
                return {
                    id: undefined,
                    tipoDocumento: undefined,
                    numeroDocumento: undefined,
                    razonSocial: undefined,
                    representanteLegal: {
                        tipoDocumento: undefined,
                        numeroDocumento: undefined
                    },
                    $save: function(){
                        var config = PersonaRestangular.withConfig(function(RestangularConfigurer) {
                            RestangularConfigurer.setFullResponse(true);
                        });
                        return config.all(url).post({'personaJuridica': angular.copy(this)});
                    }
                }
            },
            $url: function(urlResource){
                return PersonaRestangular.oneUrl(url, urlResource).get();
            },
            $search: function(queryParams){
                return PersonaRestangular.all(url).getList(queryParams);
            },
            $find: function(id){
                return PersonaRestangular.one(url, id).get();
            },
            $findByTipoNumeroDocumento: function(tipoDocumento, numeroDocumento){
                return PersonaRestangular.one(url+'/buscar').get({tipoDocumento: tipoDocumento, numeroDocumento: numeroDocumento});
            }
        };
    });
});
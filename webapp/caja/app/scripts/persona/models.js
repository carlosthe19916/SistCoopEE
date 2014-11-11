(function(window, angular, undefined) {'use strict';

    angular.module('persona.models', [])
        .config(function() {
        }).factory('EstadoCivil', function(PersonaRestangular) {
            var url = "estadosCiviles";
            return {
                $search: function(queryParams){
                    return PersonaRestangular.all(url).getList(queryParams);
                }
            };
        }).factory('Sexo', function(PersonaRestangular) {
            var url = "sexos";
            return {
                $search: function(queryParams){
                    return PersonaRestangular.all(url).getList(queryParams);
                }
            };
        }).factory('TipoPersona', function(PersonaRestangular) {
            var url = "tiposPersona";
            return {
                $search: function(queryParams){
                    return PersonaRestangular.all(url).getList(queryParams);
                }
            };
        }).factory('TipoEmpresa', function(PersonaRestangular) {
            var url = "tiposEmpresa";
            return {
                $search: function(queryParams){
                    return PersonaRestangular.all(url).getList(queryParams);
                }
            };
        }).factory('TipoDocumento', function(PersonaRestangular) {
            var url = "tiposDocumento";
            return {
                $search: function(queryParams){
                    return PersonaRestangular.all(url).getList(queryParams);
                },
                $searchByPersonaNatural: function(){
                    return PersonaRestangular.all(url).getList({tipoPersona: 'natural'});
                },
                $searchByPersonaJuridica: function(){
                    return PersonaRestangular.all(url).getList({tipoPersona: 'juridica'});
                }
            };
        }).factory('PersonaJuridica', function(PersonaRestangular) {
            var url = "personas/juridicas";
            return {
                $search: function(queryParams){
                    return PersonaRestangular.all(url).getList(queryParams);
                }
            };
        }).factory('PersonaNatural', function(PersonaRestangular) {

            var url = "personas/naturales";

            PersonaRestangular.extendModel(url, function(obj) {
                obj.$save = function() {
                    return PersonaRestangular.one(url, this.id).customPUT({'personaNatural': PersonaRestangular.copy(this)},'',{},{});
                };
                obj.fullName = function(){
                    return this.apellidoPaterno+' '+this.apellidoMaterno+', '+this.nombres;
                };
                return obj;
            });

            return {
                $build: function(){
                    return {
                        id: undefined,
                        tipoDocumento: undefined,
                        numeroDocumento: undefined,
                        apellidoPaterno: undefined,
                        apellidoMaterno: undefined,
                        nombres: undefined,
                        fechaNacimiento: undefined,
                        sexo: undefined,
                        estadoCivil: undefined,
                        $save: function(){
                            var config = PersonaRestangular.withConfig(function(RestangularConfigurer) {
                                RestangularConfigurer.setFullResponse(true);
                            });
                            return config.all(url).post({'personaNatural': angular.copy(this)});
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

})(window, window.angular);
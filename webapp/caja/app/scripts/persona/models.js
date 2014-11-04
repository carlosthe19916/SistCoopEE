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
                            return PersonaRestangular.all(url).post({'personaNatural': this});
                        }
                    }
                },
                $search: function(queryParams){
                    return PersonaRestangular.all(url).getList(queryParams);
                },
                $findByTipoNumeroDocumento: function(tipoDocumento, numeroDocumento){
                    return PersonaRestangular.one(url+'/buscar').get({tipoDocumento: tipoDocumento, numeroDocumento: numeroDocumento});
                }
            };
        });

})(window, window.angular);
(function(window, angular, undefined) {'use strict';

    angular.module('persona.models', ['restangular'])
        .config(function(RestangularProvider) {
            RestangularProvider.setBaseUrl('http://localhost:8080/restapi-persona/rest/v1');

           RestangularProvider.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
                var extractedData;
                if(data){
                    extractedData = data[Object.keys(data)[0]];
                    extractedData.meta = data.meta;
                } else {
                    extractedData = data;
                }
                return extractedData;
            });

        }).factory('EstadoCivil', function(Restangular) {
            var url = "estadosCiviles";
            return {
                $search: function(queryParams){
                    return Restangular.all(url).getList(queryParams).$object;
                }
            };
        }).factory('Sexo', function(Restangular) {
            var url = "sexos";
            return {
                $search: function(queryParams){
                    return Restangular.all(url).getList(queryParams).$object;
                }
            };
        }).factory('TipoPersona', function(Restangular) {
            var url = "tiposPersona";
            return {
                $search: function(queryParams){
                    return Restangular.all(url).getList(queryParams).$object;
                }
            };
        }).factory('TipoEmpresa', function(Restangular) {
            var url = "tiposEmpresa";
            return {
                $search: function(queryParams){
                    return Restangular.all(url).getList(queryParams).$object;
                }
            };
        }).factory('TipoDocumento', function(Restangular) {
            var url = "tiposDocumento";
            return {
                $search: function(queryParams){
                    return Restangular.all(url).getList(queryParams).$object;
                },
                $searchByPersonaNatural: function(){
                    return Restangular.all(url).getList({tipoPersona: 'natural'}).$object;
                },
                $searchByPersonaJuridica: function(){
                    return Restangular.all(url).getList({tipoPersona: 'juridica'}).$object;
                }
            };
        }).factory('PersonaJuridica', function(Restangular) {
            var url = "personas/juridicas";
            var _PersonaJuridica = Restangular.extendModel(url, function(model) {
                model.getFullName = function() {
                    return this.apellidoPaterno + this.apellidoMaterno + this.nombres;
                };
                return model;
            });

            return {
                $search: function(queryParams){
                    return Restangular.all(url).getList(queryParams).$object;
                }
            };
        }).factory('PersonaNatural', function(Restangular) {

            var url = "personas/naturales";
            var _PersonaNatural = Restangular.extendModel(url, function(model) {
                model.getFullName = function() {
                    return this.apellidoPaterno + this.apellidoMaterno + this.nombres;
                };
                return model;
            });

            return {
                $build: function(){
                    return {

                    }
                },
                $search: function(queryParams){
                    return Restangular.all(url).getList(queryParams).$object;
                },
                $findByTipoNumeroDocumento: function(tipoDocumento, numeroDocumento){
                    return Restangular.one(url+'/buscar').get({tipoDocumento: tipoDocumento, numeroDocumento: numeroDocumento}).$object;
                }
            };
        });

})(window, window.angular);
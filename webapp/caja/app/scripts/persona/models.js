(function(window, angular, undefined) {'use strict';

    angular.module('persona.models', ['restmod'])
        .config(function(restmodProvider) {

        }).factory('EstadoCivil', function(restmod) {
            return restmod.model('/estadosCiviles');
        }).factory('Sexo', function(restmod) {
            return restmod.model('/sexos');
        }).factory('TipoPersona', function(restmod) {
            return restmod.model('/tiposPersona');
        }).factory('TipoEmpresa', function(restmod) {
            return restmod.model('/tiposEmpresa');
        }).factory('TipoDocumento', function(restmod) {
            return restmod.model('/tiposDocumento').$mix({
                abreviatura: {init: undefined},
                denominacion: {init: undefined},
                cantidadCaracteres: {init: undefined},

                getId: function(){
                    return this.abreviatura;
                },
                getMaxLength: function(){
                    return this.cantidadCaracteres;
                }
            });
        }).factory('PersonaJuridica', function(restmod) {
            return restmod.model('/personas/juridicas');
        }).factory('PersonaNatural', function(restmod) {
            return restmod.model('http://localhost:8080/restapi-persona/rest/v1/personas/naturales').$mix({

                PRIMARY_KEY: 'id',

                id: {init: undefined},

                tipoDocumento: {init: undefined, map: 'tipoDocumento'},
                numeroDocumento: {init: undefined, map: 'numeroDocumento'},

                apellidoPaterno: {init: undefined, map: 'apellidoPaterno'},
                apellidoMaterno: {init: undefined, map: 'apellidoMaterno'},
                nombres: {init: undefined, map: 'nombres'},
                fechaNacimiento: {init: undefined, map: 'fechaNacimiento'},
                sexo: {init: undefined, map: 'sexo'},
                estadoCivil: {init: undefined, map: 'estadoCivil'},
                ocupacion: {init: undefined, map: 'ocupacion'},
                urlFoto: {init: undefined, map: 'urlFoto'},
                urlFirma: {init: undefined, map: 'urlFirma'},

                codigoPais: {init: undefined, map: 'codigoPais'},
                ubigeo: {init: undefined, map: 'ubigeo'},
                direccion: {init: undefined, map: 'direccion'},
                referencia: {init: undefined, map: 'referencia'},
                telefono: {init: undefined, map: 'telefono'},
                celular: {init: undefined, map: 'celular'},
                email: {init: undefined, map: 'email'},

                getNombreCompleto: function () {
                    var result = this.apellidoPaterno ? this.apellidoPaterno : '';
                    result += this.apellidoMaterno ? ' ' + this.apellidoMaterno : '';
                    result += result.length && this.nombres ? ', ' : '';
                    result += this.nombres ? this.nombres : '';
                    return result;
                },
                '@findByTipoNumeroDocumento': function (documento, numero) {
                    return this.$single(this.$url() + "/buscar").$fetch({tipoDocumento: documento, numeroDocumento: numero});
                }
            });
        });

})(window, window.angular);
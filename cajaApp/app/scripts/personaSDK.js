/**
 * Created by Huertas on 12/09/2014.
 */
var module = angular.module('personaSDK', ['restmod']);

module.config(function(restmodProvider) {
    restmodProvider.rebase('AMSApi');
    restmodProvider.rebase({
        PACKER: 'default',
        NAME: 'data',
        PLURAL: 'data'
    });
});

module.service('personaConfig', function(){
    this.urlPrefix = 'http://localhost:8080/persona-restapi/rest/v1';
});

module.factory('EstadoCivil', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/estadosCiviles').$mix({

    });
});

module.factory('Sexo', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/sexos').$mix({

    });
});

module.factory('TipoPersona', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/tiposPersona');
});

module.factory('TipoEmpresa', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/tiposEmpresa');
});

module.factory('PersonaJuridica', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/personas/juridicas');
});

module.factory('PersonaNatural', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/personas/naturales').$mix({
        PRIMARY_KEY: 'id',

        id: {init: undefined},

        tipoDocumento: {init: undefined, map: 'tipoDocumento.abreviatura'},
        numeroDocumento: {init: undefined, map: 'numeroDocumento'},

        apellidoPaterno: {init: undefined, map: 'apellidoPaterno'},
        apellidoMaterno: {init: undefined, map: 'apellidoMaterno'},
        nombres: {init: undefined, map: 'nombres'},
        fechaNacimiento: {init: undefined, map: 'fechaNacimiento'},
        sexo: {init: undefined},
        estadoCivil: {init: undefined, map: 'estadoCivil'},
        ocupacion: {init: undefined},
        urlFoto: {init: undefined, map: 'urlFoto'},
        urlFirma: {init: undefined, map: 'urlFirma'},

        codigoPais: {init: undefined, map: 'codigoPais'},
        ubigeo: {init: undefined},
        direccion: {init: undefined},
        referencia: {init: undefined},
        telefono: {init: undefined},
        celular: {init: undefined},
        email: {init: undefined}
    });
});

module.factory('TipoDocumento', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/tiposDocumento').$mix({
        NAME: 'data',
        PLURAL: 'data',

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
});
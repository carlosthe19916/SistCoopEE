/**
 * Created by Huertas on 12/09/2014.
 */
var modulePersonaSDK = angular.module('personaSDK', ['restmod']);

modulePersonaSDK.config(function(restmodProvider) {
    //restmodProvider.rebase('AMSApi');
    //restmodProvider.rebase('DefaultPacker');
});

modulePersonaSDK.service('personaConfig', function(){
    this.urlPrefix = 'http://localhost:8080/persona-restapi/rest/v1';
});

modulePersonaSDK.factory('EstadoCivil', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/estadosCiviles').$mix({

    });
});

modulePersonaSDK.factory('Sexo', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/sexos').$mix({

    });
});

modulePersonaSDK.factory('TipoPersona', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/tiposPersona');
});

modulePersonaSDK.factory('TipoEmpresa', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/tiposEmpresa');
});

modulePersonaSDK.factory('PersonaJuridica', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/personas/juridicas').$mix({
        $config: {
            name: 'mouse', // if you only set NAME, then plural is infered from it using the inflector.
                plural: 'personasJuridicas'
        }
    });
});

modulePersonaSDK.factory('PersonaNatural', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/personas/naturales').$mix({

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

        getNombreCompleto: function() {
            var result = this.apellidoPaterno ? this.apellidoPaterno : '';
            result += this.apellidoMaterno ? ' ' + this.apellidoMaterno : '';
            result += result.length && this.nombres ? ', ' : '';
            result += this.nombres ? this.nombres : '';
            return result;
        },
        '@findByTipoNumeroDocumento': function(documento, numero) {
            return this.$single(this.$url() + "/buscar").$fetch({tipoDocumento: documento, numeroDocumento: numero});
        }
});
});

modulePersonaSDK.factory('TipoDocumento', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/tiposDocumento').$mix({
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
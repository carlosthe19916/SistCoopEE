/**
 * Created by Huertas on 12/09/2014.
 */
var module = angular.module('personaSDK', ['restmod']);

module.config(function(restmodProvider) {

});

module.service('personaConfig', function(){
    this.urlPrefix = 'http://localhost:8080/persona-restapi/rest/v1';
});

module.factory('EstadoCivil', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/estadosCiviles');
});

module.factory('Sexo', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/sexos');
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
    return restmod.model(personaConfig.urlPrefix + '/personas/naturales');
});

module.factory('TipoDocumento', function(restmod, personaConfig) {
    return restmod.model(personaConfig.urlPrefix + '/tiposDocumento');
});
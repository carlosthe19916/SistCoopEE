/**
 * Created by Huertas on 12/09/2014.
 */
var module = angular.module('ubigeoSDK', ['restmod']);

module.config(function(restmodProvider) {
    restmodProvider.rebase('AMSApi');
   /* restmodProvider.rebase({
        // or use setProperty('urlPrefix', '/api/v1') in a definition function
        URL_PREFIX: 'http://localhost:8080/ubigeo-restapi/rest/v1'
    });*/
    restmodProvider.rebase({
        PACKER: 'default',
        NAME: 'data',
        PLURAL: 'data'
    });
});

module.service('ubigeoConfig', function(){
    this.urlPrefix = 'http://localhost:8080/ubigeo-restapi/rest/v1';
});

module.factory('Country', function(restmod, ubigeoConfig) {
    return restmod.model(ubigeoConfig.urlPrefix + '/countries').$mix({
        alpha2Code: {init: undefined},
        shortName: {init: undefined},
        shortNameLowerCase: {init: undefined},
        fullName: {init: undefined},
        alpha3Code: {init: undefined},
        numericCode: {init: undefined},
        remarks: {init: undefined},
        independent: {init: undefined},
        territoryName: {init: undefined},
        status: {init: undefined}
    });


});

module.factory('Currency', function(restmod, ubigeoConfig) {
    return restmod.model(ubigeoConfig.urlPrefix +'/currencies').$mix({
        code: undefined,
        denomination: undefined,
        simbol: undefined,

        denominations: { hasMany: 'Denomination'}
    });
});

module.factory('Denomination', function(restmod, ubigeoConfig) {
    return restmod.model(ubigeoConfig.urlPrefix +'/currencies').$mix({
        value: undefined
    });
});


module.factory('Departamento', function(restmod, ubigeoConfig) {
    return restmod.model(ubigeoConfig.urlPrefix +'/departamentos').$mix({
        PRIMARY_KEY: 'codigo',
        codigo: {init: undefined},
        denominacion: {init: undefined},
        provincias: { hasMany: 'Provincia'}
    })
});

module.factory('Provincia', function(restmod, ubigeoConfig) {
    return restmod.model(ubigeoConfig.urlPrefix +'/provincias').$mix({
        PRIMARY_KEY: 'codigo',
        codigo: {init: undefined},
        denominacion: {init: undefined},
        departamento: { hasOne: 'Departamento' },
        distritos: { hasMany: 'Distrito'}
    });
});

module.factory('Distrito', function(restmod, ubigeoConfig) {
    return restmod.model(ubigeoConfig.urlPrefix +'/distritos').$mix({
        PRIMARY_KEY: 'codigo',
        codigo: {init: undefined},
        denominacion: {init: undefined},
        provincia: { hasOne: 'Provincia' }
    });
});
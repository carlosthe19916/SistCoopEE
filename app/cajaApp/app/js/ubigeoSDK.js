/**
 * Created by Huertas on 12/09/2014.
 */
var moduleUbigeoSDK = angular.module('ubigeoSDK', ['restmod']);

moduleUbigeoSDK.config(function(restmodProvider) {
    restmodProvider.rebase('AMSApi');
});

moduleUbigeoSDK.service('ubigeoConfig', function(){
    this.urlPrefix = 'http://localhost:8080/ubigeo-restapi/rest/v1';
});

moduleUbigeoSDK.factory('Country', function(restmod, ubigeoConfig) {
    return restmod.model(ubigeoConfig.urlPrefix + '/countries').$mix({
        $config: {
            name: 'country',
            plural: 'countries'
        },
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

moduleUbigeoSDK.factory('Currency', function(restmod, ubigeoConfig) {
    return restmod.model(ubigeoConfig.urlPrefix +'/currencies').$mix({
        code: undefined,
        denomination: undefined,
        simbol: undefined,

        denominations: { hasMany: 'Denomination'}
    });
});

moduleUbigeoSDK.factory('Denomination', function(restmod, ubigeoConfig) {
    return restmod.model(ubigeoConfig.urlPrefix +'/currencies').$mix({
        value: undefined
    });
});

moduleUbigeoSDK.factory('Departamento', function(restmod, ubigeoConfig) {
    return restmod.model(ubigeoConfig.urlPrefix +'/departamentos').$mix({
        PRIMARY_KEY: 'codigo',
        codigo: {init: undefined},
        denominacion: {init: undefined},
        provincias: { hasMany: 'Provincia'}
    })
});

moduleUbigeoSDK.factory('Provincia', function(restmod, ubigeoConfig) {
    return restmod.model(ubigeoConfig.urlPrefix +'/provincias').$mix({
        PRIMARY_KEY: 'codigo',
        codigo: {init: undefined},
        denominacion: {init: undefined},
        departamento: { hasOne: 'Departamento' },
        distritos: { hasMany: 'Distrito'}
    });
});

moduleUbigeoSDK.factory('Distrito', function(restmod, ubigeoConfig) {
    return restmod.model(ubigeoConfig.urlPrefix +'/distritos').$mix({
        PRIMARY_KEY: 'codigo',
        codigo: {init: undefined},
        denominacion: {init: undefined},
        provincia: { hasOne: 'Provincia' }
    });
});
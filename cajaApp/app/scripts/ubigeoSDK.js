/**
 * Created by Huertas on 12/09/2014.
 */
var module = angular.module('ubigeoSDK', ['restmod']);

module.config(function(restmodProvider) {
    restmodProvider.rebase({
        // or use setProperty('urlPrefix', '/api/v1') in a definition function
        URL_PREFIX: 'http://localhost:8080/ubigeo-restapi/rest/v1'
    });
});

module.factory('Country', function(restmod) {
    return restmod.model('/countries').$mix({
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

module.factory('Currency', function(restmod) {
    return restmod.model('/currencies').$mix({
        code: undefined,
        denomination: undefined,
        simbol: undefined,

        denominations: { hasMany: 'Denomination'}
    });
});

module.factory('Denomination', function(restmod) {
    return restmod.model('/currencies').$mix({
        value: undefined
    });
});

module.factory('Departamento', function(restmod) {
    return restmod.model('/departamentos').$mix({
        codigo: undefined,
        denominacion: undefined,
        provincias: { hasMany: 'Provincia'}
    })
});

module.factory('Provincia', function(restmod) {
    return restmod.model('/provincias').$mix({
        codigo: undefined,
        denominacion: undefined,
        departamento: { hasOne: 'Departamento' },
        distritos: { hasMany: 'Distrito'}
    });
});

module.factory('Distrito', function(restmod) {
    return restmod.model('/distritos').$mix({
        codigo: undefined,
        denominacion: undefined,
        provincia: { hasOne: 'Provincia' }
    });
});
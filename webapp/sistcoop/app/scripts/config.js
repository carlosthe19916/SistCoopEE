define(['./app'], function(app) {
    'use strict';

    app.constant('CONFIGURATION', {
        'restUrl': {
            'persona': 'http://localhost:8080/restapi-persona/rest/v1',
            'ubigeo': 'http://localhost:8080/restapi-ubigeo/rest/v1',
            'organizacion': 'http://localhost:8080/restapi-organizacion/rest/v1',
            'keycloak': 'https://keycloak-softgreen.rhcloud.com/auth/admin/realms/SISTCOOP'
        }
    });

    app.config(function(uiSelectConfig) {
        uiSelectConfig.theme = 'bootstrap';
    });

    app.factory('PersonaRestangular', function(Restangular, CONFIGURATION) {
        return Restangular.withConfig(function(RestangularConfigurer) {
            RestangularConfigurer.setBaseUrl(CONFIGURATION.restUrl.persona);
        });
    });

    app.factory('UbigeoRestangular', function(Restangular, CONFIGURATION) {
        return Restangular.withConfig(function(RestangularConfigurer) {
            RestangularConfigurer.setBaseUrl(CONFIGURATION.restUrl.ubigeo);
        });
    });

    app.factory('OrganizacionRestangular', function(Restangular, CONFIGURATION) {
        return Restangular.withConfig(function(RestangularConfigurer) {
            RestangularConfigurer.setBaseUrl(CONFIGURATION.restUrl.organizacion);
        });
    });

    app.factory('KeycloakRestangular', function(Restangular, CONFIGURATION) {
        return Restangular.withConfig(function(RestangularConfigurer) {
            RestangularConfigurer.setBaseUrl(CONFIGURATION.restUrl.keycloak);
        });
    });

    app.config(['$provide', function($provide){

        var profile = angular.copy(window.auth.authz);

       var apiModules = [
            {
                module: 'PERSONA',
                roles: {
                    available: [
                        {rol: 'PUBLIC'},
                        {rol: 'USER'},
                        {rol: 'ADMIN'}
                    ],
                    assigned: profile.resourceAccess.PERSONA_RESTAPI === undefined ? [] : profile.resourceAccess.PERSONA_RESTAPI.roles
                }
            },
            {
                module: 'UBIGEO',
                roles: {
                    available: [
                        {rol: 'PUBLIC'},
                        {rol: 'USER'},
                        {rol: 'ADMIN'}
                    ],
                    assigned: profile.resourceAccess.UBIGEO_RESTAPI === undefined ? [] : profile.resourceAccess.UBIGEO_RESTAPI.roles
                }
            },
            {
                module: 'ORGANIZACION',
                roles: {
                    available: [
                        {rol: 'ADMIN'},
                        {rol: 'ADMINISTRADOR_GENERAL'},
                        {rol: 'ADMINISTRADOR'},
                        {rol: 'PLATAFORMA'},
                        {rol: 'JEFE_CAJA'},
                        {rol: 'CAJERO'}
                    ],
                    assigned: profile.resourceAccess.ORGANIZACION_RESTAPI === undefined ? [] : profile.resourceAccess.ORGANIZACION_RESTAPI.roles
                }
            }
        ];

        var getModule = function(moduleName){
            for(var i = 0; i< apiModules.length; i++){
                if(apiModules[i].module == moduleName.toUpperCase())
                    return apiModules[i];
            }
            return undefined;
        };

        var getRolesAssigned = function(moduleName){
            var module = getModule(moduleName);
            var result = [];
            for(var i=0;i<module.roles.available.length;i++){
                if(module.roles.assigned.indexOf(module.roles.available[i].rol) != -1)
                    result.push(module.roles.available[i]);
            }
            return result;
        };

        //mode puede ser AND o OR
        profile.hasRole = function(moduleName, roles, operator){
            var module = getModule(moduleName);
            if(Array.isArray(roles)){
                var result = true;
                if(operator){
                    if(operator.toUpperCase() == 'OR')
                        result = false;
                }
                for(var i = 0; i< roles.length; i++){
                    if(operator && operator.toUpperCase() == 'OR'){
                        if(module.roles.assigned.indexOf(roles[i]) >= 0){
                            result = true;
                            break;
                        }
                    } else {
                        if(module.roles.assigned.indexOf(roles[i]) < 0){
                            result = false;
                            break;
                        }
                    }
                }
                return result;
            } else {
                roles = roles.toUpperCase();
                return module.roles.assigned.indexOf(roles) >= 0;
            }
        };

        $provide.constant('activeProfile', profile);

    }]);

    return app;

});

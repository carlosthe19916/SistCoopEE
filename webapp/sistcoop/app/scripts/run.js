define(['./app'], function(app) {
    'use strict';

    app.run(function(Restangular, Notifications) {
        Restangular.setErrorInterceptor(function(response, deferred, responseHandler) {
            if(response.status === 0) {
                Notifications.error('Al parecer no se pudo realizar la conexion al sistema, actualice la pagina presionando F5.');
                return false; // error handled
            }
            if(response.status === 403) {
                return false; // error handled
            }
            if(response.status === 405) {
                alert("405");
                return false; // error handled
            }
            return true; // error not handled
        });
    });

    app.run(function($rootScope, $state, activeProfile) {
        $rootScope.$on('$stateChangeStart',
            function(event, toState, toParams, fromState, fromParams){
                if(toState.module && toState.roles){
                    if(!activeProfile.hasRole(toState.module, toState.roles, toState.operator)){
                        event.preventDefault();
                        alert('State unauthorized.');
                    }
                }
            })
    });

    return app;

});
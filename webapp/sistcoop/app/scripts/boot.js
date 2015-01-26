define([
    'require',
    'jquery',
    'angular',
    'app'
], function (require,jquery,angular,app) {
    'use strict';

    require(['requirejs-domready!'], function (document) {

        //keycloak
        var consoleBaseUrl = window.location.href;
        var consoleBaseUrl = consoleBaseUrl.substring(0, consoleBaseUrl.indexOf("/admin"));
        var consoleBaseUrl = consoleBaseUrl + "/admin";

        var baseAdminUrl = window.location.href;
        var baseAdminUrl = baseAdminUrl.substring(baseAdminUrl.indexOf("/admin"), baseAdminUrl.length);
        var baseAdminUrl = baseAdminUrl.substring(7, baseAdminUrl.length);
        var baseAdminUrl = baseAdminUrl.substring(0, baseAdminUrl.indexOf("/"));

        var consoleBaseUrl = consoleBaseUrl + "/" + baseAdminUrl;

        var configUrl = consoleBaseUrl + "/config";
        var logoutUrl = consoleBaseUrl + "/logout";
        window.auth = {};
        var logout = function(){
            console.log('*** LOGOUT');
            window.location = logoutUrl;
        };

        angular.element(document).ready(function ($http) {
            var keycloakAuth = new Keycloak(configUrl);
            keycloakAuth.onAuthLogout = function() {
                location.reload();
            };

            keycloakAuth.init({ onLoad: 'login-required' }).success(function () {
                window.auth.authz = keycloakAuth;
                if(keycloakAuth.realmAccess){
                    app.factory('Auth', function() {
                        return auth;
                    });
                    angular.bootstrap(document, ["sistcoop-app"]);
                } else {
                    keycloakAuth.logout();
                }
            }).error(function () {
                window.location.reload();
            });
        });

    });

});
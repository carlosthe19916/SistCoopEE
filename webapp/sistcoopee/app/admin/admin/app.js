'use strict';

consoleBaseUrl = consoleBaseUrl + "/admin";
var configUrl = consoleBaseUrl + "/config";
var logoutUrl = consoleBaseUrl + "/logout";
var authUrl = window.location.href;
authUrl = window.location.href.substring(0,  authUrl.indexOf('/admin/admin'));

angular.element(document).ready(function ($http) {

    var keycloakAuth = new Keycloak(configUrl);

    keycloakAuth.onAuthLogout = function() {
        location.reload();
    };

    keycloakAuth.init({ onLoad: 'login-required' }).success(function () {
        auth.authz = keycloakAuth;
        if(keycloakAuth.realmAccess){
            module.factory('Auth', function() {
                return auth;
            });
            angular.bootstrap(document, ["sistcoop"]);
        } else {
            keycloakAuth.logout();
        }
    }).error(function () {
        window.location.reload();
    });

});
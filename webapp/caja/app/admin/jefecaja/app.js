'use strict';

consoleBaseUrl = consoleBaseUrl + "/jefecaja";
var configUrl = consoleBaseUrl + "/config";
var logoutUrl = consoleBaseUrl + "/logout";
var authUrl = window.location.href;
authUrl = window.location.href.substring(0,  authUrl.indexOf('/admin/jefecaja'));

angular.element(document).ready(function ($http) {
    var keycloakAuth = new Keycloak(configUrl);

    keycloakAuth.onAuthLogout = function() {
        location.reload();
    };

    keycloakAuth.init({ onLoad: 'login-required' }).success(function () {
        auth.authz = keycloakAuth;
        module.factory('Auth', function() {
            return auth;
        });
        angular.bootstrap(document, ["sistcoop"]);
    }).error(function () {
        window.location.reload();
    });
});
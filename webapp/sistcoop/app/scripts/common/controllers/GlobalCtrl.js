define(['./module'], function (module) {
    'use strict';

    module.controller('GlobalCtrl', function($scope, $timeout, $http, $location, Auth, Usuario, Notifications, activeProfile, KeycloakRestangular, $window) {

        $scope.control = {
            block: false
        };
        $scope.blockControl = function(){
            $scope.control.block = true;
        };
        $scope.unblockControl = function(){
            $scope.control.block = false;
        };

        $scope.auth = Auth;
        $scope.$watch(function() {
            return $location.path();
        }, function() {
            $scope.fragment = $location.path();
            $scope.path = $location.path().substring(1).split("/");
        });

        $scope.activeProfile = activeProfile;
        $scope.auth.user = {};
        $scope.auth.user.username = activeProfile.idToken.preferred_username;

        $scope.accountManagement = function() {
            $scope.auth.authz.accountManagement();
        };
        $scope.getUsersManagementUrl = function() {
            //baseUrl = https://keycloak-softgreen.rhcloud.com/auth/admin/realms/SISTCOOP
            var baseUrl = KeycloakRestangular.configuration.baseUrl;
            var userUrl = baseUrl.replace('/realms/', "/");
            userUrl = userUrl + '/console/index.html';
            //userUrl = https://keycloak-softgreen.rhcloud.com/auth/admin/SISTCOOP/console/index.html
            $window.open(userUrl);
        };
        $scope.logout = function(){
            $scope.auth.authz.logout();
        };
        $scope.logoutTime = function(time){
            $timeout(function() {
                $scope.logout();
            }, (time ? time : 7000));
        };

        $scope.loadedObjectToCheck = {
            trabajador: false,
            caja: false,
            agencia: false,
            sucursal: false
        };
/*
        Usuario.$getTrabajador($scope.auth.user.username).then(function(response){
            $scope.auth.user.trabajador = response;
            $scope.loadedObjectToCheck.trabajador = true;
        });
        Usuario.$getCaja($scope.auth.user.username).then(function(response){
            $scope.auth.user.caja = response;
            $scope.loadedObjectToCheck.caja = true;
        });
        Usuario.$getSucursal($scope.auth.user.username).then(function(response){
            $scope.auth.user.sucursal  = response;
            $scope.loadedObjectToCheck.sucursal = true;
        });
        Usuario.$getAgencia($scope.auth.user.username).then(function(response){
            $scope.auth.user.agencia = response;
            $scope.loadedObjectToCheck.agencia = true;
        });

        $scope.goToTrabajadorSession = function(){
            if($scope.auth.user.trabajador.id){
                $state.go('app.organizacion.rrhh.editarTrabajador.resumen', {id: $scope.auth.user.trabajador.id});
            } else {
                alert('El usuario no tiene un trabajador asignado.');
            }
        };

        var checkRolesListener = $scope.$watch('loadedObjectToCheck', function(){
            if( $scope.loadedObjectToCheck.caja &&
                $scope.loadedObjectToCheck.trabajador &&
                $scope.loadedObjectToCheck.agencia &&
                $scope.loadedObjectToCheck.sucursal){
                $scope.checkRoles();
            }
        }, true);

        $scope.checkRoles = function(){
            checkRolesListener();

            if(activeProfile.realmAccess.roles.indexOf('ADMIN') != -1){

            } else if(activeProfile.realmAccess.roles.indexOf('GERENTE_GENERAL') != -1){
                if(angular.isUndefined($scope.auth.user.sucursal) ||
                    angular.isUndefined($scope.auth.user.trabajador)){
                    $scope.blockMessage = "El usuario no tiene un trabajador y/o sucursal asignada, no puede continuar. En 5 segundos se cerrará la session.";
                    $scope.logoutTime();
                }
            } else if(activeProfile.realmAccess.roles.indexOf('ADMINISTRADOR_GENERAL') != -1){
                if(angular.isUndefined($scope.auth.user.sucursal) ||
                    angular.isUndefined($scope.auth.user.trabajador)){
                    $scope.blockMessage = "El usuario no tiene un trabajador y/o sucursal asignada, no puede continuar. En 5 segundos se cerrará la session.";
                    $scope.logoutTime();
                }
            } else if(activeProfile.realmAccess.roles.indexOf('ADMINISTRADOR') != -1){
                if(angular.isUndefined($scope.auth.user.sucursal) ||
                    angular.isUndefined($scope.auth.user.agencia) ||
                    angular.isUndefined($scope.auth.user.trabajador)){
                    $scope.blockMessage = "El usuario no tiene un sucursal, agencia y/o trabajador asignado, no puede continuar. En 5 segundos se cerrará la session.";
                    $scope.logoutTime();
                }
            } else if(activeProfile.realmAccess.roles.indexOf('PLATAFORMA') != -1){
                if(angular.isUndefined($scope.auth.user.sucursal) ||
                    angular.isUndefined($scope.auth.user.agencia) ||
                    angular.isUndefined($scope.auth.user.trabajador)){
                    $scope.blockMessage = "El usuario no tiene un sucursal, agencia y/o trabajador asignado, no puede continuar. En 5 segundos se cerrará la session.";
                    $scope.logoutTime();
                }
            } else if(activeProfile.realmAccess.roles.indexOf('JEFE_CAJA') != -1){
                if(angular.isUndefined($scope.auth.user.sucursal) ||
                    angular.isUndefined($scope.auth.user.agencia) ||
                    angular.isUndefined($scope.auth.user.trabajador)) {
                    $scope.blockMessage = "El usuario no tiene una sucursal, agencia y/o trabajador asignado, no puede continuar. En 5 segundos se cerrará la session.";
                    $scope.logoutTime();
                }
            } else if(activeProfile.realmAccess.roles.indexOf('CAJERO') != -1){
                if(angular.isUndefined($scope.auth.user.sucursal) ||
                    angular.isUndefined($scope.auth.user.agencia) ||
                    angular.isUndefined($scope.auth.user.trabajador) ||
                    angular.isUndefined($scope.auth.user.caja)) {
                    $scope.blockMessage = "El usuario no tiene una sucursal, agencia, trabajador y/o caja asignada, no puede continuar. En 5 segundos se cerrará la session.";
                    $scope.logoutTime();
                }
            } else {
                $scope.logout();
            }
        };*/
    });
});
define(['./app'], function(app) {
    'use strict';
    return app.controller('GlobalCtrl', function($scope, $timeout, $http, $location, Auth, Usuario, Notifications, activeProfile) {

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

        $scope.logout = function(){
            $scope.auth.authz.logout();
        };
        /*$scope.auth.authz.loadUserProfile().success(function(profile) {
         $scope.auth.user = profile;
         }).error(function() {
         Notifications.error("Usuario no pudo ser cargado");
         });*/

        $scope.control = {
            block: false
        };
        $scope.blockControl = function(){
            $scope.control.block = true;
        };
        $scope.unblockControl = function(){
            $scope.control.block = false;
        };


        $scope.auth.user.trabajador = Usuario.$getTrabajador($scope.auth.user.username).$object;
        $scope.auth.user.caja = Usuario.$getCaja($scope.auth.user.username).$object;
        $scope.auth.user.sucursal = Usuario.$getSucursal($scope.auth.user.username).$object;
        $scope.auth.user.agencia = Usuario.$getAgencia($scope.auth.user.username).$object;

        //var myBlock = blockUI.instances.get('myBlock');
        //myBlock.start();

       /* if(activeProfile.realmAccess.roles.indexOf('ADMIN') != -1){

        } else if(activeProfile.realmAccess.roles.indexOf('GERENTE_GENERAL') != -1){

        } else if(activeProfile.realmAccess.roles.indexOf('ADMINISTRADOR_GENERAL') != -1){

        } else if(activeProfile.realmAccess.roles.indexOf('ADMINISTRADOR') != -1){

        } else if(activeProfile.realmAccess.roles.indexOf('PLATAFORMA') != -1){

        } else if(activeProfile.realmAccess.roles.indexOf('JEFE_CAJA') != -1){

        } else if(activeProfile.realmAccess.roles.indexOf('CAJERO') != -1){

        } else {

        }*/
    });

});
define([
    'angular'
], function(angular) {
    'use strict';

    angular.module('xenon.controllers', [])
        .controller('MainCtrl', function($scope, $rootScope, $location, $window, $state, $layout, $layoutToggles, $pageLoadingBar, $timeout, $http, blockUI, Auth, Usuario, Notifications, activeProfile, KeycloakRestangular) {
            $rootScope.isLoginPage = false;
            $rootScope.isLightLoginPage = false;
            $rootScope.isLockscreenPage = false;
            $rootScope.isMainPage = true;
            $rootScope.layoutOptions = {
                horizontalMenu: {
                    isVisible: true,
                    isFixed: true,
                    minimal: false,
                    clickToExpand: false,
                    isMenuOpenMobile: false
                },
                sidebar: {
                    isVisible: true,
                    isCollapsed: false,
                    toggleOthers: true,
                    isFixed: true,
                    isRight: false,
                    isMenuOpenMobile: false
                },
                chat: {
                    isOpen: false
                },
                settingsPane: {
                    isOpen: false,
                    useAnimation: true
                },
                container: {
                    isBoxed: false
                },
                skins: {
                    sidebarMenu: '',
                    horizontalMenu: '',
                    userInfoNavbar: ''
                },
                pageTitles: true,
                userInfoNavVisible: false
            };
            $layout.loadOptionsFromCookies();
            $layoutToggles.initToggles();

            $pageLoadingBar.init();


            /******************/
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

            Usuario.$getTrabajador($scope.auth.user.username).then(function(response){
                $scope.auth.user.trabajador = response;
            });
            Usuario.$getCaja($scope.auth.user.username).then(function(response){
                $scope.auth.user.caja = response;
            });
            Usuario.$getSucursal($scope.auth.user.username).then(function(response){
                $scope.auth.user.sucursal  = response;
            });
            Usuario.$getAgencia($scope.auth.user.username).then(function(response){
                $scope.auth.user.agencia = response;
            });

            $scope.goToTrabajadorSession = function(){
                if($scope.auth.user.trabajador.id){
                    $state.go('app.organizacion.rrhh.editarTrabajador.resumen', {id: $scope.auth.user.trabajador.id});
                } else {
                    alert('El usuario no tiene un trabajador asignado.');
                }
            };

            var checkRolesListener = $scope.$watchGroup([
                'auth.user.trabajador',
                'auth.user.caja',
                'auth.user.sucursal',
                'auth.user.agencia'
            ], function(newValue, oldValue){
                if( angular.isDefined(newValue[0]) &&
                    angular.isDefined(newValue[1]) &&
                    angular.isDefined(newValue[2]) &&
                    angular.isDefined(newValue[3])){
                    $scope.checkRoles();
                }
            });

             $scope.checkRoles = function(){
                 checkRolesListener();

                 if(activeProfile.realmAccess.roles.indexOf('ADMIN') != -1){

                 } else if(activeProfile.realmAccess.roles.indexOf('GERENTE_GENERAL') != -1){
                     if(angular.isUndefined($scope.auth.user.sucursal.id) ||
                         angular.isUndefined($scope.auth.user.trabajador.id)){
                         $scope.blockMessage = "El usuario no tiene un trabajador y/o sucursal asignada, no puede continuar. En 5 segundos se cerrará la session.";
                         $scope.logoutTime();
                     }
                 } else if(activeProfile.realmAccess.roles.indexOf('ADMINISTRADOR_GENERAL') != -1){
                     if(angular.isUndefined($scope.auth.user.sucursal.id) ||
                         angular.isUndefined($scope.auth.user.trabajador.id)){
                         $scope.blockMessage = "El usuario no tiene un trabajador y/o sucursal asignada, no puede continuar. En 5 segundos se cerrará la session.";
                         $scope.logoutTime();
                     }
                 } else if(activeProfile.realmAccess.roles.indexOf('ADMINISTRADOR') != -1){
                     if(angular.isUndefined($scope.auth.user.sucursal.id) ||
                         angular.isUndefined($scope.auth.user.agencia.id) ||
                         angular.isUndefined($scope.auth.user.trabajador.id)){
                         $scope.blockMessage = "El usuario no tiene un sucursal, agencia y/o trabajador asignado, no puede continuar. En 5 segundos se cerrará la session.";
                         $scope.logoutTime();
                     }
                 } else if(activeProfile.realmAccess.roles.indexOf('PLATAFORMA') != -1){
                     if(angular.isUndefined($scope.auth.user.sucursal.id) ||
                         angular.isUndefined($scope.auth.user.agencia.id) ||
                         angular.isUndefined($scope.auth.user.trabajador.id)){
                         $scope.blockMessage = "El usuario no tiene un sucursal, agencia y/o trabajador asignado, no puede continuar. En 5 segundos se cerrará la session.";
                         $scope.logoutTime();
                     }
                 } else if(activeProfile.realmAccess.roles.indexOf('JEFE_CAJA') != -1){
                     if(angular.isUndefined($scope.auth.user.sucursal.id) ||
                         angular.isUndefined($scope.auth.user.agencia.id) ||
                         angular.isUndefined($scope.auth.user.trabajador.id)){
                         $scope.blockMessage = "El usuario no tiene un sucursal, agencia y/o trabajador asignado, no puede continuar. En 5 segundos se cerrará la session.";
                         $scope.logoutTime();
                     }
                 } else if(activeProfile.realmAccess.roles.indexOf('CAJERO') != -1){
                     if(angular.isUndefined($scope.auth.user.sucursal.id) ||
                         angular.isUndefined($scope.auth.user.agencia.id) ||
                         angular.isUndefined($scope.auth.user.trabajador.id) ||
                         angular.isUndefined($scope.auth.user.caja.id)){
                         $scope.blockMessage = "El usuario no tiene un sucursal, agencia, trabajador y/o caja asignada, no puede continuar. En 5 segundos se cerrará la session.";
                         $scope.logoutTime();
                     }
                 } else {
                     $scope.logout();
                 }
             };
        })
        .controller('SidebarMenuCtrl', function($scope, $rootScope, $menuItems, $timeout, $location, $state, activeProfile) {
            var $sidebarMenuItems = $menuItems.instantiate();
            $scope.menuItems = $sidebarMenuItems.prepareSidebarMenu($state.current.name, activeProfile.realmAccess.roles).getAll();

            $scope.itemSelected = undefined;
            $scope.setMenuSelected = function($index){
                if($scope.itemSelected == $index){
                    $scope.itemSelected = undefined;
                } else {
                    $scope.itemSelected = $index;
                }
            };
        })
        .controller('HorizontalMenuCtrl', function($scope, $rootScope, $menuItems, activeProfile) {
            var $horizontalMenuItems = $menuItems.instantiate();
            $scope.menuItems = $horizontalMenuItems.prepareHorizontalMenu(activeProfile.realmAccess.roles).getAll();

            $scope.notificationsIsOpen = false;
            $scope.changeNotificationsIsOpen = function(){
                $scope.userProfileIsOpen = false;
                $scope.notificationsIsOpen = !$scope.notificationsIsOpen;
            };

            $scope.userProfileIsOpen = false;
            $scope.changeUserProfileIsOpen = function(){
                $scope.notificationsIsOpen = false;
                $scope.userProfileIsOpen = !$scope.userProfileIsOpen;
            };

            $scope.itemSelected = undefined;
            $scope.setMenuSelected = function($index){
                if($scope.itemSelected == $index){
                    $scope.itemSelected = undefined;
                } else {
                    $scope.itemSelected = $index;
                }
            };
        })
        .controller('SettingsPaneCtrl', function($rootScope) {
            $rootScope.changeSettingsPaneIsOpen = function(){
                $rootScope.layoutOptions.settingsPane.isOpen = !$rootScope.layoutOptions.settingsPane.isOpen;
            };
        });

});
define([
    'angular'
], function(angular) {
    'use strict';

    angular.module('xenon.controllers', [])
        .controller('MainCtrl', function($scope, $rootScope, $location, $layout, $layoutToggles, $pageLoadingBar, $timeout, $http, Auth, Usuario, Notifications, activeProfile) {
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
        .controller('HorizontalMenuCtrl', function($scope, $rootScope, $menuItems) {
            var $horizontalMenuItems = $menuItems.instantiate();
            $scope.menuItems = $horizontalMenuItems.prepareHorizontalMenu().getAll();

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
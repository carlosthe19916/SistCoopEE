define([
    'angular'
], function(app) {
    'use strict';

    angular.module('xenon.controllers', [])
        .controller('MainCtrl', function($scope, $rootScope, $location, $layout, $layoutToggles, $pageLoadingBar) {
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
        })
        .controller('SidebarMenuCtrl', function($scope, $rootScope, $menuItems, $timeout, $location, $state, activeProfile) {
            var $sidebarMenuItems = $menuItems.instantiate();
            $scope.menuItems = $sidebarMenuItems.prepareSidebarMenu($state.current.name, activeProfile.realmAccess.roles).getAll();

            $scope.itemSelected;
            $scope.setMenuSelected = function($index){
                if($scope.itemSelected == $index){
                    $scope.itemSelected = undefined;
                } else {
                    $scope.itemSelected = $index;
                }
            };
        })
        .controller('HorizontalMenuCtrl', function($scope, $rootScope, $menuItems, $timeout, $location, $state) {
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

            $scope.itemSelected;
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
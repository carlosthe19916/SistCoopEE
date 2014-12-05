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
            $scope.updatePsScrollbars = function() {
                var $scrollbars = jQuery(".ps-scrollbar:visible");
                $scrollbars.each(function(i, el) {
                    if (typeof jQuery(el).data('perfectScrollbar') == 'undefined') {
                        jQuery(el).perfectScrollbar();
                    } else {
                        jQuery(el).perfectScrollbar('update');
                    }
                })
            };
            $layoutToggles.initToggles();

            $pageLoadingBar.init();
            $scope.showLoadingBar = showLoadingBar;
            $scope.hideLoadingBar = hideLoadingBar;
            /*$rootScope.$on('$stateChangeStart', function() {
                var obj = {
                    pos: jQuery(window).scrollTop()
                };
                TweenLite.to(obj, .25, {
                    pos: 0,
                    ease: Power4.easeOut,
                    onUpdate: function() {
                        $(window).scrollTop(obj.pos);
                    }
                });
            });*/
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

            /*$sidebarMenuItems.setActive($location.path());
            $rootScope.$on('$stateChangeSuccess', function() {
                $sidebarMenuItems.setActive($state.current.name);
            });*/
            //public_vars.$sidebarMenu = public_vars.$body.find('.sidebar-menu');
            //$timeout(setup_sidebar_menu, 1);
            //ps_init();
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
            //public_vars.$settingsPane = public_vars.$body.find('.settings-pane');
            //public_vars.$settingsPaneIn = public_vars.$settingsPane.find('.settings-pane-inner');
            $rootScope.changeSettingsPaneIsOpen = function(){
                $rootScope.layoutOptions.settingsPane.isOpen = !$rootScope.layoutOptions.settingsPane.isOpen;
            };
        })
        .controller('ChatCtrl', function($scope, $element) {
            /*var $chat = jQuery($element),
                $chat_conv = $chat.find('.chat-conversation');
            $chat.find('.chat-inner').perfectScrollbar();
            $chat.on('click', '.chat-group a', function(ev) {
                ev.preventDefault();
                $chat_conv.toggleClass('is-open');
                if ($chat_conv.is(':visible')) {
                    $chat.find('.chat-inner').perfectScrollbar('update');
                    $chat_conv.find('textarea').autosize();
                }
            });
            $chat_conv.on('click', '.conversation-close', function(ev) {
                ev.preventDefault();
                $chat_conv.removeClass('is-open');
            });*/
        });

});
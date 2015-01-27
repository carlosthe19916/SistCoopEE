define(['./module'], function (module) {
    'use strict';

    module.
        controller('MainCtrl', function($scope, $rootScope, $location, $layout, $layoutToggles, $pageLoadingBar, Fullscreen)
        {
            $rootScope.isLoginPage        = false;
            $rootScope.isLightLoginPage   = false;
            $rootScope.isLockscreenPage   = false;
            $rootScope.isMainPage         = true;

            $rootScope.layoutOptions = {
                horizontalMenu: {
                    isVisible		: true,
                    isFixed			: true,
                    minimal			: true,
                    clickToExpand	: true,

                    isMenuOpenMobile: false
                },
                sidebar: {
                    isVisible		: true,
                    isCollapsed		: false,
                    toggleOthers	: true,
                    isFixed			: true,
                    isRight			: false,

                    isMenuOpenMobile: false,

                    // Added in v1.3
                    userProfile		: true
                },
                chat: {
                    isOpen			: false
                },
                settingsPane: {
                    isOpen			: false,
                    useAnimation	: true
                },
                container: {
                    isBoxed			: false
                },
                skins: {
                    sidebarMenu		: '',
                    horizontalMenu	: '',
                    userInfoNavbar	: ''
                },
                pageTitles: true,
                userInfoNavVisible	: false
            };

            $rootScope.settingsPaneToggle = function(toggle){
                $scope.layoutOptions.settingsPane.isOpen = toggle || !$scope.layoutOptions.settingsPane.isOpen;
            };
        }).
        controller('SidebarMenuCtrl', function($scope, $rootScope, $menuItems, $timeout, $location, $state, $layout)
        {

        }).
        controller('HorizontalMenuCtrl', function($scope, $rootScope, $menuItems, $timeout, $location, $state)
        {
        }).
        controller('ChatCtrl', function($scope, $element)
        {
        }).
        // Added in v1.3
        controller('FooterChatCtrl', function($scope, $element)
        {
        });

});
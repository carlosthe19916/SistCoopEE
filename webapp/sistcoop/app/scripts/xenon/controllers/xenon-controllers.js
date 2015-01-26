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
                    isVisible		: false,
                    isFixed			: true,
                    minimal			: false,
                    clickToExpand	: false,

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

            $layout.loadOptionsFromCookies(); // remove this line if you don't want to support cookies that remember layout changes

            // Init Layout Toggles
            $layoutToggles.initToggles();
        }).
        controller('SidebarMenuCtrl', function($scope, $rootScope, $menuItems, $timeout, $location, $state, $layout)
        {

        }).
        controller('HorizontalMenuCtrl', function($scope, $rootScope, $menuItems, $timeout, $location, $state)
        {
        }).
        controller('SettingsPaneCtrl', function($rootScope)
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
define(['./module'], function (module) {
    'use strict';

    module.
        directive('settingsPane', function(TweenMax){
            return {
                restrict: 'E',
                templateUrl: appHelper.templatePath('layout/settings-pane'),
                scope: {
                    isOpen: '= isOpen',
                    useAnimation: '= useAnimation'
                },
                controller: function($scope) {
                    $scope.settingsPaneToggle = function(toggle){
                        $scope.isOpen = toggle || !$scope.isOpen;
                    };
                },
                link: function($scope, elem, attrs, ngModel){
                }
            };
        }).
        directive('horizontalMenu', function($timeout, $menuItemsApp, KeycloakRestangular, Auth){
            return {
                restrict: 'E',
                replace: true,
                templateUrl: appHelper.templatePath('layout/horizontal-menu'),
                scope: {
                    isVisible: '=isVisible',
                    isFixed: '=isFixed',
                    minimal: '=minimal',
                    clickToExpand: '=clickToExpand',
                    isMenuOpenMobile: '=isMenuOpenMobile',
                    userInfoNavbar: '=userInfoNavbar',

                    settingsPaneIsOpen: '=settingsPaneIsOpen'
                },
                controller: function($scope) {
                    $scope.settingsPaneToggle = function(toggle){
                        $scope.settingsPaneIsOpen = toggle || !$scope.settingsPaneIsOpen;
                    };

                    $scope.username = Auth.authz.idToken.preferred_username;
                    $scope.profile = function(){
                        Auth.authz.accountManagement();
                    };
                    $scope.logout = function(){
                        Auth.authz.logout();
                    };

                    var $horizontalMenuItems = $menuItemsApp.instantiate();
                    $scope.menuItems = $horizontalMenuItems.prepareHorizontalMenu().getAll();

                    //notifications
                },
                link: function($scope, elem, attrs, ngModel){
                }
            }
        }).
        directive('sidebarMenu', function($timeout, $state,  $menuItemsApp, activeProfile){
            return {
                restrict: 'E',
                templateUrl: appHelper.templatePath('layout/sidebar-menu'),
                controller: function($scope) {
                    var $sidebarMenuItems = $menuItemsApp.instantiate();
                    $scope.menuItems = $sidebarMenuItems.prepareSidebarMenu($state.current.name, activeProfile.realmAccess.roles).getAll();
                }
            };
        }).
        directive('sidebarChat', function(){
            return {
                restrict: 'E',
                replace: true,
                templateUrl: appHelper.templatePath('layout/sidebar-chat')
            };
        }).
        directive('footerChat', function(){
            return {
                restrict: 'E',
                replace: true,
                controller: 'FooterChatCtrl',
                templateUrl: appHelper.templatePath('layout/footer-chat')
            };
        }).
        directive('sidebarLogo', function(){
            return {
                restrict: 'E',
                replace: true,
                templateUrl: appHelper.templatePath('layout/sidebar-logo')
            };
        }).
        directive('sidebarProfile', function(){
            return {
                restrict: 'E',
                replace: true,
                templateUrl: appHelper.templatePath('layout/sidebar-profile')
            };
        }).
        directive('userInfoNavbar', function(){
            return {
                restrict: 'E',
                replace: true,
                templateUrl: appHelper.templatePath('layout/user-info-navbar')
            };
        }).
        directive('pageTitle', function(){
            return {
                restrict: 'E',
                replace: true,
                templateUrl: appHelper.templatePath('layout/page-title'),
                link: function(scope, el, attr){
                    scope.title = attr.title;
                    scope.description = attr.description;
                }
            };
        }).
        directive('siteFooter', function(){
            return {
                restrict: 'E',
                templateUrl: appHelper.templatePath('layout/footer')
            };
        }).
        directive('xeBreadcrumb', function(){
            return {
                restrict: 'A',
                link: function(scope, el)
                {
                    var $bc = angular.element(el);

                    if($bc.hasClass('auto-hidden'))
                    {
                        var $as = $bc.find('li a'),
                            collapsed_width = $as.width(),
                            expanded_width = 0;

                        $as.each(function(i, el)
                        {
                            var $a = $(el);

                            expanded_width = $a.outerWidth(true);
                            $a.addClass('collapsed').width(expanded_width);

                            $a.hover(function()
                                {
                                    $a.removeClass('collapsed');
                                },
                                function()
                                {
                                    $a.addClass('collapsed');
                                });
                        });
                    }
                }
            }
        });
});
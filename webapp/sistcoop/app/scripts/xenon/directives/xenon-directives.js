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
                    var $settingsPane = elem.find('.settings-pane');
                    var $settingsPaneIn = $settingsPane.find('.settings-pane-inner');
                    var use_animation = $scope.useAnimation;

                    $scope.activeListener = function(){
                        $scope.$watch('isOpen', function(oldValue, newValue){
                            if(oldValue != newValue){
                                // Opening
                                if( !newValue) {
                                    var height = $settingsPane.outerHeight(true);
                                    $settingsPane.css({
                                        height: 0
                                    });
                                    TweenMax.to($settingsPane, .25, {css: {height: height}, ease: Circ.easeInOut, onComplete: function(){
                                        $settingsPane.css({height: ''});
                                    }});
                                    $settingsPaneIn.addClass('visible');
                                }
                                // Closing
                                else {
                                    $settingsPaneIn.addClass('closing');
                                    TweenMax.to($settingsPane, .25, {css: {height: 0}, delay: .15, ease: Power1.easeInOut, onComplete: function(){
                                        $settingsPane.css({height: ''});
                                        $settingsPaneIn.removeClass('closing visible');
                                    }});
                                }
                            }
                        }, true);
                    };

                    if(use_animation) {
                        // With Animation
                        $settingsPaneIn.addClass('with-animation');
                        $scope.activeListener();
                    } else {
                        // Without Animation
                        $settingsPaneIn.removeClass('visible');
                        $settingsPaneIn.removeClass('with-animation');
                    }
                }
            };
        }).
        directive('horizontalMenu', function(){
            return {
                restrict: 'E',
                replace: true,
                templateUrl: appHelper.templatePath('layout/horizontal-menu'),
                controller: function($scope) {
                },
                link: function($scope, elem, attrs, ngModel){
                }
            }
        }).
        directive('sidebarMenu', function(){
            return {
                restrict: 'E',
                templateUrl: appHelper.templatePath('layout/sidebar-menu'),
                controller: 'SidebarMenuCtrl'
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
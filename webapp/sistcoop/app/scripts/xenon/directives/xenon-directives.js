define(['./module'], function (module) {
    'use strict';

    module
        .directive('sgNavbar', function($menuItemsApp) {
            return {
                restrict: 'E',
                templateUrl: appHelper.templatePath('layout/sg-navbar'),
                controller: function($scope){
                    var $horizontalMenuItems = $menuItemsApp.instantiate();
                    $scope.menuItems = $horizontalMenuItems.prepareHorizontalMenu().getAll();
                }
            };
        })
        .directive('sgDropdown', function() {
            return {
                restrict: 'EA',
                scope: {
                    item: '=',
                    closeMode: '@'
                },
                replace: true,
                templateUrl: appHelper.templatePath('layout/sg-dropdown'),
                controller: function($scope, $attrs, $parse, dropdownService, $animate){

                    var self = this;
                    var scope = $scope.$new(); // create a child scope so we are not polluting original one
                    var openClass = $attrs.expandedClass;
                    var getIsOpen;
                    var setIsOpen = angular.noop;
                    var toggleInvoker = $attrs.onToggle ? $parse($attrs.onToggle) : angular.noop;

                    this.init = function( element ) {
                        self.$element = element;

                        if ( $attrs.isOpen ) {
                            getIsOpen = $parse($attrs.isOpen);
                            setIsOpen = getIsOpen.assign;

                            $scope.$watch(getIsOpen, function(value) {
                                scope.isOpen = !!value;
                            });
                        }
                    };

                    this.toggle = function( open ) {
                        return scope.isOpen = arguments.length ? !!open : !scope.isOpen;
                    };

                    // Allow other directives to watch status
                    this.isOpen = function() {
                        return scope.isOpen;
                    };

                    scope.getToggleElement = function() {
                        return self.toggleElement;
                    };

                    scope.focusToggleElement = function() {
                        if ( self.toggleElement ) {
                            self.toggleElement[0].focus();
                        }
                    };

                    scope.$watch('isOpen', function( isOpen, wasOpen ) {

                        $animate[isOpen ? 'addClass' : 'removeClass'](self.$element, openClass);
                        if(angular.isDefined(self.toggledElement)){
                            if(isOpen)
                                self.toggledElement.addClass($attrs.childExpandedClass);
                            else
                                self.toggledElement.removeClass($attrs.childExpandedClass);
                        }

                        if($scope.closeMode)
                        {
                            if ( isOpen ) {
                                scope.focusToggleElement();
                                dropdownService.open( scope );
                            } else {
                                dropdownService.close( scope );
                            }
                        }

                        setIsOpen($scope, isOpen);
                        if (angular.isDefined(isOpen) && isOpen !== wasOpen) {
                            toggleInvoker($scope, { open: !!isOpen });
                        }
                    });

                    $scope.$on('$locationChangeSuccess', function() {
                        if($scope.closeMode)
                        {
                            scope.isOpen = false;
                        }
                    });

                    $scope.$on('$destroy', function() {
                        scope.$destroy();
                    });

                },
                link: function(scope, element, attrs, sgDropdownCtrl) {
                    sgDropdownCtrl.init( element );
                }
            };
        })
        .directive('sgDropdownToggle', function() {
            return {
                require: '?^sgDropdown',
                link: function(scope, element, attrs, sgDropdownCtrl) {

                    if ( !sgDropdownCtrl ) {
                        return;
                    }

                    sgDropdownCtrl.toggleElement = element;

                    var toggleDropdown = function(event) {
                        event.preventDefault();

                        if ( !element.hasClass('disabled') && !attrs.disabled ) {
                            scope.$apply(function() {
                                sgDropdownCtrl.toggle();
                            });
                        }
                    };

                    element.bind('click', toggleDropdown);

                    // WAI-ARIA
                    element.attr({ 'aria-haspopup': true, 'aria-expanded': false });
                    scope.$watch(sgDropdownCtrl.isOpen, function( isOpen ) {
                        element.attr('aria-expanded', !!isOpen);
                    });

                    scope.$on('$destroy', function() {
                        element.unbind('click', toggleDropdown);
                    });
                }
            };
        })
        .directive('sgDropdownToggled', function() {
            return {
                require: '?^sgDropdown',
                link: function(scope, element, attrs, sgDropdownCtrl) {

                    if ( !sgDropdownCtrl ) {
                        return;
                    }

                    sgDropdownCtrl.toggledElement = element;

                }
            };
        })





        .directive('settingsPane', function(){
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
        directive('horizontalMenu', function($timeout, $menuItemsApp, Auth){
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
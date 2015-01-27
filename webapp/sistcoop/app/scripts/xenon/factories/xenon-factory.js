define(['./module'], function (module) {
    'use strict';

    module.
        factory('$layoutToggles', function($rootScope, $window, $layout){

            return {

                initToggles: function()
                {
                    // Sidebar Toggle
                    $rootScope.sidebarToggle = function()
                    {
                        $layout.setOptions('sidebar.isCollapsed', ! $rootScope.layoutOptions.sidebar.isCollapsed);
                    };


                    // Settings Pane
                    $rootScope.settingsPaneToggle = function()
                    {
                    };

                    // Chat Toggle
                    $rootScope.chatToggle = function()
                    {
                        $layout.setOptions('chat.isOpen', ! $rootScope.layoutOptions.chat.isOpen);
                    };


                    // Mobile Menu Toggle
                    $rootScope.mobileMenuToggle = function()
                    {
                        $layout.setOptions('sidebar.isMenuOpenMobile', ! $rootScope.layoutOptions.sidebar.isMenuOpenMobile);
                        $layout.setOptions('horizontalMenu.isMenuOpenMobile', ! $rootScope.layoutOptions.horizontalMenu.isMenuOpenMobile);
                    };


                    // Mobile User Info Navbar Toggle
                    $rootScope.mobileUserInfoToggle = function()
                    {
                        $layout.setOptions('userInfoNavVisible', ! $rootScope.layoutOptions.userInfoNavVisible);
                    }
                }
            };
        }).
        factory('$pageLoadingBar', function($rootScope, $window){

            return {

                init: function()
                {
                },

                showLoadingBar: function(options) {
                }
            };
        }).
        factory('$layout', function($rootScope, $cookies, $cookieStore){

            return {
                propsToCache: [
                    'horizontalMenu.isVisible',
                    'horizontalMenu.isFixed',
                    'horizontalMenu.minimal',
                    'horizontalMenu.clickToExpand',

                    'sidebar.isVisible',
                    'sidebar.isCollapsed',
                    'sidebar.toggleOthers',
                    'sidebar.isFixed',
                    'sidebar.isRight',
                    'sidebar.userProfile',

                    'chat.isOpen',

                    'container.isBoxed',

                    'skins.sidebarMenu',
                    'skins.horizontalMenu',
                    'skins.userInfoNavbar'
                ],

                setOptions: function(options, the_value)
                {
                    if(typeof options == 'string' && typeof the_value != 'undefined')
                    {
                        options = this.pathToObject(options, the_value);
                    }

                    angular.extend($rootScope.layoutOptions, options);

                    this.saveCookies();
                },

                saveCookies: function()
                {
                    var cookie_entries = this.iterateObject($rootScope.layoutOptions, '', {});

                    angular.forEach(cookie_entries, function(value, prop)
                    {
                        $cookies[prop] = value;
                    });
                },

                resetCookies: function()
                {
                    var cookie_entries = this.iterateObject($rootScope.layoutOptions, '', {});

                    angular.forEach(cookie_entries, function(value, prop)
                    {
                        $cookieStore.remove(prop);
                    });
                },

                loadOptionsFromCookies: function()
                {
                    var dis = this,
                        cookie_entries = dis.iterateObject($rootScope.layoutOptions, '', {}),
                        loaded_props = {};

                    angular.forEach(cookie_entries, function(value, prop)
                    {
                        var cookie_val = $cookies[prop];

                        if(typeof cookie_val != 'undefined')
                        {
                            angular.extend(loaded_props, dis.pathToObject(prop, cookie_val));
                        }
                    });

                    angular.extend($rootScope.layoutOptions, loaded_props);
                },

                is: function(prop, value)
                {
                    var cookieval = this.get(prop);

                    return cookieval == value;
                },

                get: function(prop)
                {
                    var cookieval = $cookies[prop];

                    if(cookieval && cookieval.match(/^true|false|[0-9.]+$/))
                    {
                        cookieval = eval(cookieval);
                    }

                    if( ! cookieval)
                    {
                        cookieval = this.getFromPath(prop, $rootScope.layoutOptions);
                    }

                    return cookieval;
                },

                getFromPath: function(path, lo)
                {
                    var val = '',
                        current_path,
                        paths = path.split('.');

                    angular.forEach(paths, function(path_id, i)
                    {
                        var is_last = paths.length - 1 == i;

                        if( ! current_path)
                            current_path = lo[path_id];
                        else
                            current_path = current_path[path_id];

                        if(is_last)
                        {
                            val = current_path;
                        }
                    });

                    return val;
                },

                pathToObject: function(obj_path, the_value)
                {
                    var new_obj = {},
                        curr_obj = null,
                        last_key;

                    if(obj_path)
                    {
                        var paths = obj_path.split('.'),
                            depth = paths.length - 1,
                            array_scls = '';

                        angular.forEach(paths, function(path_id, i)
                        {
                            var is_last = paths.length - 1 == i;

                            array_scls += '[\'' + path_id + '\']';

                            if(is_last)
                            {
                                if(typeof the_value == 'string' && ! the_value.toString().match(/^true|false|[0-9.]+$/))
                                {
                                    the_value = '"' + the_value + '"';
                                }

                                eval('new_obj' + array_scls + ' = ' + the_value + ';');
                            }
                            else
                                eval('new_obj' + array_scls + ' = {};');
                        });
                    }

                    return new_obj;
                },

                iterateObject: function(objects, append, arr)
                {
                    var dis = this;

                    angular.forEach(objects, function(obj, key)
                    {
                        if(typeof obj == 'object')
                        {
                            return dis.iterateObject(obj, append + key + '.', arr);
                        }
                        else
                        if(typeof obj != 'undefined')
                        {
                            arr[append + key] = obj;
                        }
                    });

                    // Filter Caching Objects
                    angular.forEach(arr, function(value, prop)
                    {
                        if( ! inArray(prop, dis.propsToCache))
                            delete arr[prop];
                    });

                    function inArray(needle, haystack) {
                        var length = haystack.length;
                        for(var i = 0; i < length; i++) {
                            if(haystack[i] == needle) return true;
                        }
                        return false;
                    }

                    return arr;
                }
            };
        });

});
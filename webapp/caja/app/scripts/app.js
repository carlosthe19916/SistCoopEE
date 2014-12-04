/*jshint unused: vars */
define([
        'angular',
        './common/main',
        './organizacion/main',
        './persona/main',
        './ubigeo/main'
    ]/*deps*/,
    function (angular){

        'use strict';

        var consoleBaseUrl = window.location.href;
        consoleBaseUrl = consoleBaseUrl.substring(0, consoleBaseUrl.indexOf("/app/admin"));
        consoleBaseUrl = consoleBaseUrl + "/app/admin/admin";
        var configUrl = consoleBaseUrl + "/config";
        var logoutUrl = consoleBaseUrl + "/logout";
        var auth = {};
        var logout = function(){
            console.log('*** LOGOUT');
            window.location = logoutUrl;
        };

        var authUrl = window.location.href;
        authUrl = window.location.href.substring(0,  authUrl.indexOf('/admin/admin'));

        angular.element(document).ready(function ($http) {
            var keycloakAuth = new Keycloak(configUrl);
            keycloakAuth.onAuthLogout = function() {
                location.reload();
            };

            keycloakAuth.init({ onLoad: 'login-required' }).success(function () {
                auth.authz = keycloakAuth;
                if(keycloakAuth.realmAccess){
                    app.factory('Auth', function() {
                        return auth;
                    });
                    angular.bootstrap(document, ["sistcoop-app"]);

                } else {
                    keycloakAuth.logout();
                }
            }).error(function () {
                window.location.reload();
            });
        });

        var app = angular.module('sistcoop-app',
            [
                /*xenon*/
                'ngCookies',
                'ui.router',
                'ui.bootstrap',
                'oc.lazyLoad',
                'xenon.controllers',
                'xenon.directives',
                'xenon.factory',
                'xenon.services',

                /*sistcoop*/
                'ngSanitize',
                'ngMessages',
                //'ngAnimate',
                'persona',
                'ubigeo',
                'organizacion',
                'common',
                'restangular',
                'ui.select',
                'ui.utils',
                'ui.grid',
                'ui.grid.edit',
                'ui.grid.selection',
                'blockUI',
                'angular-ladda'
            ]);

        var resourceRequests = 0;
        var loadingTimer = -1;

        app.run(function($rootScope, $timeout) {
            public_vars.$pageLoadingOverlay = jQuery('.page-loading-overlay');

            /*jQuery(window).load(function() {
                public_vars.$pageLoadingOverlay.addClass('loaded');
            });*/
            $rootScope.$on('$viewContentLoading', function(event, viewConfig){
                $timeout(function(){
                    public_vars.$pageLoadingOverlay.addClass('loaded');
                }, 1000);
            });
        });

        app.factory('authInterceptor', function($q, Auth) {
            return {
                request: function (config) {
                    var deferred = $q.defer();
                    if (Auth.authz.token) {
                        Auth.authz.updateToken(5).success(function() {
                            config.headers = config.headers || {};
                            config.headers.Authorization = 'Bearer ' + Auth.authz.token;

                            deferred.resolve(config);
                        }).error(function() {
                            location.reload();
                        });
                    }
                    return deferred.promise;
                }
            };
        });

        app.config(function(RestangularProvider) {
            RestangularProvider.setBaseUrl('http://localhost:8080');

            //añade @ a los atributos
            RestangularProvider.addFullRequestInterceptor(function(element, operation, route, url, headers, params, httpConfig) {
                if(operation == 'post' || operation == 'put'){
                    var newElement;
                    if(element){
                        newElement = element[Object.keys(element)[0]];
                        angular.forEach(newElement, function(value, key) {
                            if(angular.isObject(value) && !angular.isDate(value) && !angular.isArray(value)){
                                var obj = angular.copy(value);
                                angular.forEach(value, function(val, k) {
                                    if(angular.isObject(value)){
                                        this['@' + k.toString()] = val !== null ? val: undefined;
                                        delete this[k];
                                    }
                                }, obj);
                                this[key.toString()] = obj;
                            } else {
                                this['@' + key.toString()] = value !== null ? value: undefined;
                                delete this[key];
                            }
                        }, newElement);
                    }
                }
            });

            //saca el primer objeto
            RestangularProvider.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
                var extractedData;
                if(data){
                    extractedData = data[Object.keys(data)[0]];
                    extractedData.meta = data.meta;
                } else {
                    extractedData = data;
                }
                return extractedData;
            });
            //saca los @ de los atributos
            RestangularProvider.setResponseExtractor(function(response) {
                var newResponse = angular.copy(response);
                if (angular.isArray(response)) {
                    angular.forEach(newResponse, function(value, key) {
                        newResponse[key].originalElement = angular.copy(value);
                    });
                } else {
                    if(response){
                        //newResponse.originalElement = angular.copy(response);
                        angular.forEach(response, function(value, key) {
                            if(key.substring(0,1) == '@') {
                                var newKey = key.replace('@', '');
                                this[newKey.toString()] = value;
                                delete this[key];
                            } else {
                                var obj = angular.copy(value);
                                angular.forEach(value, function(val, k) {
                                    if(k.substring(0,1) == '@') {
                                        var newKey = k.replace('@', '');
                                        this[newKey.toString()] = val;
                                        delete this[k];
                                    }
                                }, obj);
                                this[key.toString()] = obj;
                            }
                        }, newResponse);
                    }
                }
                return newResponse;
            });
        });

        app.factory('PersonaRestangular', function(Restangular) {
            return Restangular.withConfig(function(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl('http://localhost:8080/restapi-persona/rest/v1');
            });
        });

        app.factory('UbigeoRestangular', function(Restangular) {
            return Restangular.withConfig(function(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl('http://localhost:8080/restapi-ubigeo/rest/v1');
            });
        });

        app.factory('OrganizacionRestangular', function(Restangular) {
            return Restangular.withConfig(function(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl('http://localhost:8080/restapi-organizacion/rest/v1');
            });
        });

        app.config(['$provide', function($provide){
            var profile = angular.copy(auth.authz);

            //modulesNames = ['PERSONA', 'UBIGEO', 'ORGANIZACION'];
            //operations = ['SELECT', 'CREATE', 'UPDATE', 'DELETE'];

            var apiModules = [
                {
                    module: 'PERSONA',
                    roles: {
                        available: [
                            {rol: 'PUBLIC', permissions: ['SELECT']},
                            {rol: 'USER', permissions: ['SELECT', 'CREATE']},
                            {rol: 'ADMIN', permissions: ['SELECT', 'CREATE', 'UPDATE', 'DELETE']}
                        ],
                        assigned: profile.resourceAccess.PERSONA_RESTAPI === undefined ? [] : profile.resourceAccess.PERSONA_RESTAPI.roles
                    }
                },
                {
                    module: 'UBIGEO',
                    roles: {
                        available: [
                            {rol: 'PUBLIC', permissions: ['SELECT']},
                            {rol: 'USER', permissions: ['SELECT', 'CREATE']},
                            {rol: 'ADMIN', permissions: ['SELECT', 'CREATE', 'UPDATE', 'DELETE']}
                        ],
                        assigned: profile.resourceAccess.UBIGEO_RESTAPI === undefined ? [] : profile.resourceAccess.UBIGEO_RESTAPI.roles
                    }
                },
                {
                    module: 'ORGANIZACION',
                    roles: {
                        available: [
                            {rol: 'ADMIN', permissions: ['SELECT', 'CREATE', 'UPDATE', 'DELETE']},
                            {rol: 'ADMINISTRADOR_GENERAL', permissions: ['SELECT', 'CREATE', 'UPDATE', 'DELETE']},
                            {rol: 'ADMINISTRADOR', permissions: ['SELECT', 'CREATE', 'UPDATE', 'DELETE']},
                            {rol: 'PLATAFORMA', permissions: ['SELECT', 'CREATE', 'UPDATE']},
                            {rol: 'JEFE_CAJA', permissions: ['SELECT', 'CREATE', 'UPDATE']},
                            {rol: 'CAJERO', permissions: ['SELECT', 'CREATE', 'UPDATE']}
                        ],
                        assigned: profile.resourceAccess.ORGANIZACION_RESTAPI === undefined ? [] : profile.resourceAccess.ORGANIZACION_RESTAPI.roles
                    }
                }
            ];

            var getModule = function(moduleName){
                for(var i = 0; i< apiModules.length; i++){
                    if(apiModules[i].module == moduleName.toUpperCase())
                        return apiModules[i];
                }
                return undefined;
            };

            var getRolesAssigned = function(moduleName){
                var module = getModule(moduleName);
                var result = [];
                for(var i=0;i<module.roles.available.length;i++){
                    if(module.roles.assigned.indexOf(module.roles.available[i].rol) != -1)
                        result.push(module.roles.available[i]);
                }
                return result;
            };

            //mode puede ser AND o OR
            profile.hasRole = function(moduleName, roles, operator){
                var module = getModule(moduleName);
                if(Array.isArray(roles)){
                    var result = true;
                    if(operator){
                        if(operator.toUpperCase() == 'OR')
                            result = false;
                    }
                    for(var i = 0; i< roles.length; i++){
                        if(operator && operator.toUpperCase() == 'OR'){
                            if(module.roles.assigned.indexOf(roles[i]) >= 0){
                                result = true;
                                break;
                            }
                        } else {
                            if(module.roles.assigned.indexOf(roles[i]) < 0){
                                result = false;
                                break;
                            }
                        }
                    }
                    return result;
                } else {
                    roles = roles.toUpperCase();
                    return module.roles.assigned.indexOf(roles) >= 0;
                }
            };

            profile.hasPermission = function(moduleName, operations){
                var rolesAssigned = getRolesAssigned(moduleName);
                if(Array.isArray(operations)){
                    var band = [];
                    for(var i = 0; i< rolesAssigned.length; i++){
                        for(var j = 0; j< operations.length; i++){
                            if(rolesAssigned[i].permissions.indexOf(operations[j]) > -1){
                                band.push(operations[j]);
                                break;
                            }
                        }
                    }
                    return band.length == operations.length;
                } else {
                    operations = operations.toUpperCase();
                    var result = false;
                    for(var i = 0; i< rolesAssigned.length; i++){
                        if(rolesAssigned[i].permissions.indexOf(operations) > -1){
                            result = true;
                            break;
                        }
                    }
                    return result;
                }
            };

            //carlos
            profile.getSubmenu = function(menuName){
                menuName = menuName.toLowerCase();
                if(menuName == 'administracion'){

                } else if(menuName == 'organizacion'){
                    if(profile.hasRole('ORGANIZACION', 'ADMIN')){
                        return [
                            {
                                name:'ESTRUCTURA',
                                state: 'app.organizacion',
                                header: true,
                                submenu: [
                                    {'name':'Sucursales', 'state': 'app.organizacion.buscarSucursal', header: false},
                                    {'name':'Agencias', 'state': 'app.organizacion.buscarAgencia', header: false},
                                    {'name':'Bovedas', 'state': 'app.organizacion.buscarBoveda', header: false},
                                    {'name':'Cajas', 'state': 'app.organizacion.buscarCaja', header: false}
                                ]
                            },
                            {
                                name:'RRHH',
                                state: 'app.organizacion',
                                header: true,
                                submenu: [
                                    {'name':'Trabajadores', 'state': 'app.trabajador.buscarTrabajador', header: false},
                                    {'name':'Usuarios', 'state': 'app.trabajador.buscarUsuarios', header: false}
                                ]
                            }
                        ];
                    } else if(profile.hasRole('ORGANIZACION', 'GERENTE_GENERAL')){
                        return [
                            {
                                name:'ESTRUCTURA',
                                state: 'app.organizacion',
                                header: true,
                                submenu: [
                                    {'name':'Sucursales', 'state': 'app.organizacion.buscarSucursal', header: false},
                                    {'name':'Agencias', 'state': 'app.organizacion.buscarAgencia', header: false},
                                    {'name':'Bovedas', 'state': 'app.organizacion.buscarBoveda', header: false},
                                    {'name':'Cajas', 'state': 'app.organizacion.buscarCaja', header: false}
                                ]
                            },

                            {
                                name:'RRHH',
                                state: 'app.organizacion',
                                header: true,
                                submenu: [
                                    {'name':'Trabajadores', 'state': 'app.trabajador.buscarTrabajador', header: false},
                                    {'name':'Usuarios', 'state': 'app.trabajador.buscarUsuarios', header: false}
                                ]
                            }
                        ];
                    } else if(profile.hasRole('ORGANIZACION', 'ADMINISTRADOR_GENERAL')){
                        return [
                            {
                                name:'ESTRUCTURA',
                                state: 'app.organizacion',
                                header: true,
                                submenu: [
                                    {'name':'Sucursales', 'state': 'app.organizacion.buscarSucursal', header: false},
                                    {'name':'Agencias', 'state': 'app.organizacion.buscarAgencia', header: false},
                                    {'name':'Bovedas', 'state': 'app.organizacion.buscarBoveda', header: false},
                                    {'name':'Cajas', 'state': 'app.organizacion.buscarCaja', header: false}
                                ]
                            },
                            {
                                name:'RRHH',
                                state: 'app.organizacion',
                                header: true,
                                submenu: [
                                    {'name':'Trabajadores', 'state': 'app.trabajador.buscarTrabajador', header: false},
                                    {'name':'Usuarios', 'state': 'app.trabajador.buscarUsuarios', header: false}
                                ]
                            }
                        ];
                    } else if(profile.hasRole('ORGANIZACION', 'ADMINISTRADOR')){
                        return [
                            {
                                name:'ESTRUCTURA',
                                state: 'app.organizacion',
                                header: true,
                                submenu: [
                                    {'name':'Sucursales', 'state': 'app.organizacion.buscarSucursal', header: false},
                                    {'name':'Agencias', 'state': 'app.organizacion.buscarAgencia', header: false},
                                    {'name':'Bovedas', 'state': 'app.organizacion.buscarBoveda', header: false},
                                    {'name':'Cajas', 'state': 'app.organizacion.buscarCaja', header: false}
                                ]
                            },
                            {
                                name:'RRHH',
                                state: 'app.organizacion',
                                header: true,
                                submenu: [
                                    {'name':'Trabajadores', 'state': 'app.trabajador.buscarTrabajador', header: false},
                                    {'name':'Usuarios', 'state': 'app.trabajador.buscarUsuarios', header: false}
                                ]
                            }
                        ];
                    } else if(profile.hasRole('ORGANIZACION', 'PLATAFORMA')){
                        return undefined;
                    } else if(profile.hasRole('ORGANIZACION', 'JEFE_CAJA')){
                        return [
                            {
                                name:'ESTRUCTURA',
                                state: 'app.organizacion',
                                header: true,
                                submenu: [
                                    {'name':'Bovedas', 'state': 'app.organizacion.buscarBoveda', header: false},
                                    {'name':'Cajas', 'state': 'app.organizacion.buscarCaja', header: false}
                                ]
                            }
                        ];
                    } else if(profile.hasRole('ORGANIZACION', 'CAJERO')){
                        return undefined;
                    } else {
                        return undefined;
                    }
                } else if(menuName == 'transaccion'){

                }  else if(menuName == 'socio'){

                } else {
                    return undefined;
                }
            };

            $provide.constant('activeProfile', profile);
        }]);

        app.config(function(uiSelectConfig) {
            uiSelectConfig.theme = 'bootstrap';
        });

        app.config(function(blockUIConfig) {
            blockUIConfig.message = 'Cargando...';
            blockUIConfig.template = '' +
                '<div class="row">' +
                '<div class="col-md-2 col-md-offset-6" style="top: 2.9em; position: fixed; z-index: 1500; text-align: center;">' +
                '<div class="alert alert-warning">' +
                '<span><strong>{{ state.message}}</strong></span>' +
                '</div>' +
                '</div>' +
                '</div>';
        });

        app.config(function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, ASSETS) {
            $urlRouterProvider.otherwise('/app/home');
            $stateProvider.state('app', {
                abstract: true,
                url: '/app',
                templateUrl: appHelper.templatePath('layout/app-body'),
                controller: function($rootScope) {
                    $rootScope.isLoginPage = false;
                    $rootScope.isLightLoginPage = false;
                    $rootScope.isLockscreenPage = false;
                    $rootScope.isMainPage = true;
                }
            }).state('app.home', {
                url: '/home',
                templateUrl: appHelper.templatePath('dashboards/home'),
                resolve: {
                    resources: function($ocLazyLoad) {
                        return $ocLazyLoad.load([ASSETS.charts.dxGlobalize, ASSETS.extra.toastr]);
                    },
                    dxCharts: function($ocLazyLoad) {
                        return $ocLazyLoad.load([ASSETS.charts.dxCharts]);
                    }
                }
            });
        });
        app.constant('ASSETS', {
            'core': {
                'bootstrap': appHelper.assetPath('js/bootstrap.min.js'),
                'jQueryUI': [appHelper.assetPath('js/jquery-ui/jquery-ui.min.js'), appHelper.assetPath('js/jquery-ui/jquery-ui.structure.min.css')],
                'moment': appHelper.assetPath('js/moment.min.js'),
                'googleMapsLoader': appHelper.assetPath('app/js/angular-google-maps/load-google-maps.js')
            },
            'charts': {
                'dxGlobalize': appHelper.assetPath('js/devexpress-web-14.1/js/globalize.min.js'),
                'dxCharts': appHelper.assetPath('js/devexpress-web-14.1/js/dx.chartjs.js'),
                'dxVMWorld': appHelper.assetPath('js/devexpress-web-14.1/js/vectormap-data/world.js')
            },
            'xenonLib': {
                notes: appHelper.assetPath('js/xenon-notes.js')
            },
            'maps': {
                'vectorMaps': [appHelper.assetPath('js/jvectormap/jquery-jvectormap-1.2.2.min.js'), appHelper.assetPath('js/jvectormap/regions/jquery-jvectormap-world-mill-en.js'), appHelper.assetPath('js/jvectormap/regions/jquery-jvectormap-it-mill-en.js')]
            },
            'icons': {
                'meteocons': appHelper.assetPath('css/fonts/meteocons/css/meteocons.css'),
                'elusive': appHelper.assetPath('css/fonts/elusive/css/elusive.css')
            },
            'tables': {
                'rwd': appHelper.assetPath('js/rwd-table/js/rwd-table.min.js'),
                'datatables': [appHelper.assetPath('js/datatables/dataTables.bootstrap.css'), appHelper.assetPath('js/datatables/datatables-angular.js')]
            },
            'forms': {
                'select2': [appHelper.assetPath('js/select2/select2.css'), appHelper.assetPath('js/select2/select2-bootstrap.css'), appHelper.assetPath('js/select2/select2.min.js')],
                'daterangepicker': [appHelper.assetPath('js/daterangepicker/daterangepicker-bs3.css'), appHelper.assetPath('js/daterangepicker/daterangepicker.js')],
                'colorpicker': appHelper.assetPath('js/colorpicker/bootstrap-colorpicker.min.js'),
                'selectboxit': appHelper.assetPath('js/selectboxit/jquery.selectBoxIt.js'),
                'tagsinput': appHelper.assetPath('js/tagsinput/bootstrap-tagsinput.min.js'),
                'datepicker': appHelper.assetPath('js/datepicker/bootstrap-datepicker.js'),
                'timepicker': appHelper.assetPath('js/timepicker/bootstrap-timepicker.min.js'),
                'inputmask': appHelper.assetPath('js/inputmask/jquery.inputmask.bundle.js'),
                'formWizard': appHelper.assetPath('js/formwizard/jquery.bootstrap.wizard.min.js'),
                'jQueryValidate': appHelper.assetPath('js/jquery-validate/jquery.validate.min.js'),
                'dropzone': [appHelper.assetPath('js/dropzone/css/dropzone.css'), appHelper.assetPath('js/dropzone/dropzone.min.js')],
                'typeahead': [appHelper.assetPath('js/typeahead.bundle.js'), appHelper.assetPath('js/handlebars.min.js')],
                'multiSelect': [appHelper.assetPath('js/multiselect/css/multi-select.css'), appHelper.assetPath('js/multiselect/js/jquery.multi-select.js')],
                'icheck': [appHelper.assetPath('js/icheck/skins/all.css'), appHelper.assetPath('js/icheck/icheck.min.js')],
                'bootstrapWysihtml5': [appHelper.assetPath('js/wysihtml5/src/bootstrap-wysihtml5.css'), appHelper.assetPath('js/wysihtml5/wysihtml5-angular.js')]
            },
            'uikit': {
                'base': [appHelper.assetPath('js/uikit/uikit.css'), appHelper.assetPath('js/uikit/css/addons/uikit.almost-flat.addons.min.css'), appHelper.assetPath('js/uikit/js/uikit.min.js')],
                'codemirror': [appHelper.assetPath('js/uikit/vendor/codemirror/codemirror.js'), appHelper.assetPath('js/uikit/vendor/codemirror/codemirror.css')],
                'marked': appHelper.assetPath('js/uikit/vendor/marked.js'),
                'htmleditor': appHelper.assetPath('js/uikit/js/addons/htmleditor.min.js'),
                'nestable': appHelper.assetPath('js/uikit/js/addons/nestable.min.js')
            },
            'extra': {
                'tocify': appHelper.assetPath('js/tocify/jquery.tocify.min.js'),
                'toastr': appHelper.assetPath('js/toastr/toastr.min.js'),
                'fullCalendar': [appHelper.assetPath('js/fullcalendar/fullcalendar.min.css'), appHelper.assetPath('js/fullcalendar/fullcalendar.min.js')],
                'cropper': [appHelper.assetPath('js/cropper/cropper.min.js'), appHelper.assetPath('js/cropper/cropper.min.css')]
            }
        });


        app.run(function(Restangular, Notifications) {
            Restangular.setErrorInterceptor(function(response, deferred, responseHandler) {
                if(response.status === 0) {
                    Notifications.error('Al parecer no se pudo realizar la conexion al sistema, actualice la pagina presionando F5.');
                    return false; // error handled
                }
                if(response.status === 403) {
                    return false; // error handled
                }
                if(response.status === 405) {
                    alert("405");
                    return false; // error handled
                }
                return true; // error not handled
            });
        });

        app.run(function($rootScope, $state, activeProfile) {
            $rootScope.$on('$stateChangeStart',
                function(event, toState, toParams, fromState, fromParams){
                    if(toState.module && toState.roles){
                        if(!activeProfile.hasRole(toState.module, toState.roles, toState.operator)){
                            event.preventDefault();
                            alert('State unauthorized.');
                        }
                    }
                })
        });

        app.config(function($httpProvider) {
            $httpProvider.interceptors.push('errorInterceptor');

            var spinnerFunction = function(data, headersGetter) {
                if (resourceRequests == 0) {
                    loadingTimer = window.setTimeout(function() {
                        //$('#loading').show();
                        loadingTimer = -1;
                    }, 500);
                }
                resourceRequests++;
                return data;
            };
            $httpProvider.defaults.transformRequest.push(spinnerFunction);

            $httpProvider.interceptors.push('spinnerInterceptor');
            $httpProvider.interceptors.push('authInterceptor');
        });

        app.factory('errorInterceptor', function($q, $window, $rootScope, $location,Notifications) {
            return function(promise) {
                return promise.then(function(response) {
                    return response;
                }, function(response) {
                    if (response.status == 401) {
                        console.log('session timeout?');
                        logout();
                    } else if (response.status == 403) {
                        Notifications.error("Forbidden");
                    } else if (response.status == 404) {
                        Notifications.error("Not found");
                    } else if (response.status) {
                        if (response.data && response.data.errorMessage) {
                            Notifications.error(response.data.errorMessage);
                        } else {
                            Notifications.error("An unexpected server error has occurred");
                        }
                    }
                    return $q.reject(response);
                });
            };
        });

        app.factory('spinnerInterceptor', function($q, $window, $rootScope, $location) {
            return function(promise) {
                return promise.then(function(response) {
                    resourceRequests--;
                    if (resourceRequests == 0) {
                        if(loadingTimer != -1) {
                            window.clearTimeout(loadingTimer);
                            loadingTimer = -1;
                        }
                        //$('#loading').hide();
                    }
                    return response;
                }, function(response) {
                    resourceRequests--;
                    if (resourceRequests == 0) {
                        if(loadingTimer != -1) {
                            window.clearTimeout(loadingTimer);
                            loadingTimer = -1;
                        }
                        //$('#loading').hide();
                    }

                    return $q.reject(response);
                });
            };
        });

        return app;
    }
);





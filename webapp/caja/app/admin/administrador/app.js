'use strict';

consoleBaseUrl = consoleBaseUrl + "/administrador";
var configUrl = consoleBaseUrl + "/config";
var logoutUrl = consoleBaseUrl + "/logout";
var authUrl = window.location.href;
authUrl = window.location.href.substring(0,  authUrl.indexOf('/admin/administrador'));

angular.element(document).ready(function ($http) {
    var keycloakAuth = new Keycloak(configUrl);

    keycloakAuth.onAuthLogout = function() {
        location.reload();
    };

    keycloakAuth.init({ onLoad: 'login-required' }).success(function () {
        auth.authz = keycloakAuth;
        module.factory('Auth', function() {
            return auth;
        });
        angular.bootstrap(document, ["sistcoop"]);
    }).error(function () {
        window.location.reload();
    });
});

module.config([ '$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: '../../views/themplate/themplate01.html'
        })
        .state('app', {
            abstract: true,
            url: '/app',
            templateUrl: '../../views/themplate/themplate02.html'
        })

        .state('app.administracion', {
            url: "/administracion",
            views: {
                "viewMenu":{
                    controller: function($scope){
                        $scope.menus = [
                            {'name':'Persona Natural', 'state': '', header: true},
                            {'name':'Nuevo', 'state': 'app.administracion.crearPersonaNatural', header: false},
                            {'name':'Buscar', 'state': 'app.administracion.buscarPersonaNatural', header: false},

                            {'name':'Persona Jurídica', 'state': '', header: true},
                            {'name':'Nuevo', 'state': 'app.administracion.crearPersonaJuridica', header: false},
                            {'name':'Buscar', 'state': 'app.administracion.buscarPersonaJuridica', header: false}
                        ];
                    }
                },
                "viewContent":{
                    templateUrl: '../../views/themplate/themplate02-content.html',
                    controller: function($scope){
                        $scope.themplate = {};
                        $scope.themplate.header = 'Administracion';
                    }
                }
            }
        })

        .state('app.administracion.buscarPersonaNatural', {
            url: '/persona/natural/buscar',
            templateUrl: "../../views/persona/natural/buscarPersonaNatural.html",
            controller: function($scope) {
                $scope.themplate.header = 'Buscar persona natural';
            },
            module: 'PERSONA',
            roles: ['PUBLIC']
        })
        .state('app.administracion.crearPersonaNatural', {
            url: "/persona/natural?documento&numero",
            templateUrl: "../../views/persona/natural/crearPersonaNatural.html",
            controller: function($scope, $stateParams) {
                $scope.themplate.header = 'Crear persona natural';

                $scope.params = {};
                $scope.params.tipoDocumento = $stateParams.documento;
                $scope.params.numeroDocumento = $stateParams.numero;
            },
            module: 'PERSONA',
            roles: ['USER']
        })
        .state('app.administracion.editarPersonaNatural', {
            url: "/persona/natural/:id",
            templateUrl: "../../views/persona/natural/editarPersonaNatural.html",
            controller: function($scope, $stateParams) {
                $scope.themplate.header = 'Editar persona natural';

                $scope.params = {};
                $scope.params.id = $stateParams.id;
            },
            module: 'PERSONA',
            roles: ['ADMIN']
        })

        .state('app.administracion.buscarPersonaJuridica', {
            url: '/persona/juridica/buscar',
            templateUrl: "../../views/persona/juridica/buscarPersonaJuridica.html",
            controller: function($scope, $stateParams) {
                $scope.themplate.header = 'Buscar persona juridica';
            },
            module: 'PERSONA',
            roles: ['PUBLIC']
        })
        .state('app.administracion.crearPersonaJuridica', {
            url: "/persona/juridica?documento&numero",
            templateUrl: "../../views/persona/juridica/crearPersonaJuridica.html",
            controller: function($scope, $stateParams) {
                $scope.themplate.header = 'Crear persona juridica';

                $scope.params = {};
                $scope.params.tipoDocumento = $stateParams.documento;
                $scope.params.numeroDocumento = $stateParams.numero;
            },
            module: 'PERSONA',
            roles: ['USER']
        })
        .state('app.administracion.editarPersonaJuridica', {
            url: "/persona/juridica/:id",
            templateUrl: "../../views/persona/juridica/editarPersonaJuridica.html",
            controller: function($scope, $stateParams) {
                $scope.themplate.header = 'Editar persona juridica';

                $scope.params = {};
                $scope.params.id = $stateParams.id;
            },
            module: 'PERSONA',
            roles: ['ADMIN']
        });
} ]);

module.run(function($rootScope, activeProfile) {
    $rootScope.$on('$stateChangeStart',
        function(event, toState, toParams, fromState, fromParams){
            if(!activeProfile.hasRole(toState.module, toState.roles)){
                event.preventDefault();
                alert('State unauthorized.');
            }
        })
});
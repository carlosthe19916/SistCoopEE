define(['./app'], function(app) {
    'use strict';
    return app.config(function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/home');

        $stateProvider
            .state('home', {
                url: '/home',
                templateUrl: 'views/themplate/themplate01.html'
            })
            .state('app', {
                abstract: true,
                url: '/app',
                templateUrl: 'views/themplate/themplate02.html'
            })
            .state('app.caja', {
                url: '/caja',
                views: {
                    "viewMenu":{
                        controller: function($scope){
                            $scope.menus = [
                                {'name':'Panel Control', 'state': '', header: true},
                                {'name':'Panel', 'state': 'app.caja.panelControl', header: false},

                                {'name':'Caja', 'state': '', header: true},
                                {'name':'Abrir', 'state': 'app.caja.abrirCaja', header: false},
                                {'name':'Cerrar', 'state': 'app.caja.cerrarCaja', header: false},
                                {'name':'Pendientes', 'state': 'app.caja.pendiente', header: false},
                                {'name':'Historial', 'state': 'app.caja.historial', header: false},

                                {'name':'Transacciones Internas', 'state': '', header: true},
                                {'name':'Transaccion con Boveda', 'state': 'app.caja.buscarTransaccionBovedaCaja', header: false},
                                {'name':'Transaccion con Caja', 'state': 'app.caja.buscarTransaccionCajaCaja', header: false}
                            ];
                        }
                    },
                    "viewContent":{
                        template: '<div ui-view style="min-height: 472px;">prueba</br></br></div>'
                    }
                }
            })
            .state('app.transaccion', {
                url: "/transaccion",
                views: {
                    "viewMenu":{
                        controller: function($scope){
                            $scope.menus = [
                                {'name':'Cuenta aporte', 'state': '', header: true},
                                {'name':'Aporte', 'state': 'app.transaccion.aporte', header: false},

                                {'name':'Cuenta Bancaria', 'state': '', header: true},
                                {'name':'Deposito/Retiro', 'state': 'app.transaccion.depositoRetiro', header: false},
                                {'name':'Transferencia', 'state': 'app.transaccion.transferencia', header: false},
                                {'name':'Compra/Venta', 'state': 'app.transaccion.compraVenta', header: false},

                                {'name':'Transacciones Internas', 'state': '', header: true},
                                {'name':'Transaccion con Boveda', 'state': 'app.caja.buscarTransaccionBovedaCaja', header: false},
                                {'name':'Transaccion con Caja', 'state': 'app.caja.buscarTransaccionCajaCaja', header: false},

                                {'name':'Historial', 'state': '', header: true},
                                {'name':'Buscar Transacción', 'state': 'app.transaccion.buscarTransaccion', header: false}
                            ];
                        }
                    },
                    "viewContent":{
                        template: '<div>sss</div>'
                    }
                }
            })
            .state('app.socio', {
                url: "/socio",
                views: {
                    "viewMenu":{
                        controller: function($scope){
                            $scope.menus = [
                                {'name':'Socio', 'state': '', header: true},
                                {'name':'Buscar', 'state': 'app.socio.buscarSocio', header: false},

                                {'name':'Cuenta Aporte', 'state': '', header: true},
                                {'name':'Nuevo', 'state': 'app.socio.crearSocio', header: false},

                                {'name':'Cuentas Bancarias', 'state': '', header: true},
                                {'name':'Nuevo', 'state': 'app.socio.crearCuentaBancaria', header: false},
                                {'name':'Buscar', 'state': 'app.socio.buscarCuentaBancaria', header: false}
                            ];
                        }
                    },
                    "viewContent":{
                        template: 'prueba'
                    }
                }
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
                        templateUrl: 'views/themplate/themplate02-content.html',
                        controller: function($scope){
                            $scope.themplate = {};
                            $scope.themplate.header = 'Administracion';
                        }
                    }
                }
            })

            .state('app.administracion.buscarPersonaNatural', {
                url: '/persona/natural/buscar',
                views: {
                    "viewContent":{
                        templateUrl: 'views/cajero/persona/natural/buscarPersonaNatural.html',
                        controller: 'BuscarPersonaNaturalController'
                    }
                }
            })
            .state('app.administracion.crearPersonaNatural', {
                url: "/persona/natural?tipoDocumento&numeroDocumento",
                templateUrl: "views/persona/natural/crearPersonaNatural.html",
                controller: function($scope, $stateParams) {
                    $scope.themplate.header = 'Crear persona natural';

                    $scope.params = {};
                    $scope.params.idTipoDocumento = $stateParams.tipoDocumento;
                    $scope.params.numeroDocumento = $stateParams.numeroDocumento;
                }
            })
            .state('app.administracion.editarPersonaNatural', {
                url: "/persona/natural/:id",
                views: {
                    "viewContent":{
                        templateUrl: "views/cajero/persona/natural/editarPersonaNatural.html",
                        controller: function($scope, $stateParams) {
                            $scope.id = $stateParams.id;
                        }
                    }
                }
            });

    });

});

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('MainController', function($scope){

        $scope.currentSubMenu = [];

        $scope.resetMenu = function(){
            $scope.currentSubMenu = [];
        };

        $scope.setMenuCaja = function(){
            $scope.currentSubMenu = $scope.caja;
        };
        $scope.setMenuTransaccion = function(){
            $scope.currentSubMenu = $scope.transaccion;
        };
        $scope.setMenuSocio = function(){
            $scope.currentSubMenu = $scope.socio;
        };
        $scope.setMenuAdministracion = function(){
            $scope.currentSubMenu = $scope.administracion;
        };

        $scope.caja = [
            { 'name':'Caja' , 'state':'app.caja.abrirCaja', header: true},
            { 'name':'Abrir Caja' , 'state':'app.caja.abrirCaja', header: false},
            { 'name':'Cerrar Caja' , 'state':'app.caja.cerrarCaja', header: false},
            { 'name':'Pendientes' , 'state':'app.caja.pendiente', header: false},
            { 'name':'Historial' , 'state':'app.caja.historial', header: false},

            { 'name':'Transaccion interna' , 'state':'app.caja.buscarTransaccionBovedaCaja', header: true},
            { 'name':'Transaccion con boveda' , 'state':'app.caja.buscarTransaccionBovedaCaja', header: false},
            { 'name':'Transaccion con caja' , 'state':'app.caja.buscarTransaccionCajaCaja',header: false}
        ];


        $scope.administracion = [
            { 'name':'Persona natural' , 'state':'app.caja.buscarTransaccionCajaCaja',header:  true},
            { 'name':'Nuevo' , 'state':'app.caja.buscarTransaccionCajaCaja',header:  false},
            { 'name':'Buscar' , 'state':'app.caja.buscarTransaccionCajaCaja',header:  false},

            { 'name':'Persona juridica' , 'state':'app.caja.buscarTransaccionCajaCaja',header:  true},
            { 'name':'Nuevo' , 'state':'app.caja.buscarTransaccionCajaCaja',header:  false},
            { 'name':'Buscar' , 'state':'app.caja.buscarTransaccionCajaCaja',header:  false}

        ];


    });
});
define([
    'angular'
], function(app) {
    'use strict';

    'use strict';
    angular.module('xenon.services', []).service('$menuItems', function() {
        this.menuItems = [];
        var $menuItemsRef = this;
        var menuItemObj = {
            parent: null,
            title: '',
            link: '',
            state: '',
            icon: '',
            isActive: false,
            label: null,
            menuItems: [],
            setLabel: function(label, color, hideWhenCollapsed) {
                if (typeof hideWhenCollapsed == 'undefined')
                    hideWhenCollapsed = true;
                this.label = {
                    text: label,
                    classname: color,
                    collapsedHide: hideWhenCollapsed
                };
                return this;
            },
            addItem: function(title, link, icon) {
                var parent = this,
                    item = angular.extend(angular.copy(menuItemObj), {
                        parent: parent,
                        title: title,
                        link: link,
                        icon: icon
                    });
                if (item.link) {
                    if (item.link.match(/^\./))
                        item.link = parent.link + item.link.substring(1, link.length);
                    if (item.link.match(/^-/))
                        item.link = parent.link + '-' + item.link.substring(2, link.length);
                    item.state = $menuItemsRef.toStatePath(item.link);
                }
                this.menuItems.push(item);
                return item;
            }
        };
        this.addItem = function(title, link, icon) {
            var item = angular.extend(angular.copy(menuItemObj), {
                title: title,
                link: link,
                state: this.toStatePath(link),
                icon: icon
            });
            this.menuItems.push(item);
            return item;
        };
        this.getAll = function() {
            return this.menuItems;
        };
        this.prepareSidebarMenu = function(stateName, roles) {
            if(roles.indexOf('ADMIN') != -1){
                if(stateName.indexOf('app.organizacion') > -1){
                    var estructura = this.addItem('Estructura', '', 'linecons-inbox');
                    var rrhh = this.addItem('RRHH', '', 'linecons-t-shirt');

                    estructura.addItem('Sucursales', 'app.organizacion.estructura.buscarSucursal');
                    estructura.addItem('Agencias', 'app.organizacion.estructura.buscarAgencia');
                    estructura.addItem('Bovedas', 'app.organizacion.estructura.buscarBoveda');
                    estructura.addItem('Cajas', 'app.organizacion.estructura.buscarCaja');
                    rrhh.addItem('Trabajadores', 'app.organizacion.rrhh.buscarTrabajador');
                    rrhh.addItem('Usuarios', 'app.organizacion.rrhh.buscarUsuario');

                } else if(stateName.indexOf('app.administracion') > -1){
                    var administracion = this.addItem('Personas', '', 'linecons-user');

                    administracion.addItem('Naturales', 'app.administracion.personas.buscarPersonaNatural');
                    administracion.addItem('Juridicas', 'app.administracion.personas.buscarPersonaJuridica');
                } else {
                    return [];
                }
            } else if(roles.indexOf('GERENTE_GENERAL') != -1){
                if(stateName.indexOf('app.organizacion') > -1){
                    var estructura = this.addItem('Estructura', '', 'linecons-inbox');

                    estructura.addItem('Sucursales', 'app.organizacion.estructura.buscarSucursal');
                    estructura.addItem('Agencias', 'app.organizacion.estructura.buscarAgencia');
                    estructura.addItem('Bovedas', 'app.organizacion.estructura.buscarBoveda');
                    estructura.addItem('Cajas', 'app.organizacion.estructura.buscarCaja');

                } else if(stateName.indexOf('app.administracion') > -1){
                    var administracion = this.addItem('Personas', '', 'linecons-user');

                    administracion.addItem('Naturales', 'app.administracion.personas.buscarPersonaNatural');
                    administracion.addItem('Juridicas', 'app.administracion.personas.buscarPersonaJuridica');
                } else {
                    return [];
                }
            } else if(roles.indexOf('ADMINISTRADOR_GENERAL') != -1){
                if(stateName.indexOf('app.organizacion') > -1){
                    var estructura = this.addItem('Estructura', '', 'linecons-inbox');

                    estructura.addItem('Agencias', 'app.organizacion.estructura.buscarAgencia');
                    estructura.addItem('Bovedas', 'app.organizacion.estructura.buscarBoveda');
                    estructura.addItem('Cajas', 'app.organizacion.estructura.buscarCaja');
                } else if(stateName.indexOf('app.administracion') > -1){
                    var administracion = this.addItem('Personas', '', 'linecons-user');

                    administracion.addItem('Naturales', 'app.administracion.personas.buscarPersonaNatural');
                    administracion.addItem('Juridicas', 'app.administracion.personas.buscarPersonaJuridica');
                } else {
                    return [];
                }
            } else if(roles.indexOf('ADMINISTRADOR') != -1){
                if(stateName.indexOf('app.organizacion') > -1){
                    var estructura = this.addItem('Estructura', '', 'linecons-inbox');

                    estructura.addItem('Bovedas', 'app.organizacion.estructura.buscarBoveda');
                    estructura.addItem('Cajas', 'app.organizacion.estructura.buscarCaja');
                } else if(stateName.indexOf('app.administracion') > -1){
                    var administracion = this.addItem('Personas', '', 'linecons-user');

                    administracion.addItem('Naturales', 'app.administracion.personas.buscarPersonaNatural');
                    administracion.addItem('Juridicas', 'app.administracion.personas.buscarPersonaJuridica');
                } else {
                    return [];
                }
            } else if(roles.indexOf('PLATAFORMA') != -1){
                if(stateName.indexOf('app.organizacion') > -1){

                } else if(stateName.indexOf('app.administracion') > -1){
                    var administracion = this.addItem('Personas', '', 'linecons-user');

                    administracion.addItem('Naturales', 'app.administracion.personas.buscarPersonaNatural');
                    administracion.addItem('Juridicas', 'app.administracion.personas.buscarPersonaJuridica');
                } else {
                    return [];
                }
            } else if(roles.indexOf('JEFE_CAJA') != -1){
                if(stateName.indexOf('app.organizacion') > -1){
                    var estructura = this.addItem('Estructura', '', 'linecons-inbox');

                    estructura.addItem('Bovedas', 'app.organizacion.estructura.buscarBoveda');
                    estructura.addItem('Cajas', 'app.organizacion.estructura.buscarCaja');
                } else if(stateName.indexOf('app.administracion') > -1){
                    var administracion = this.addItem('Personas', '', 'linecons-user');

                    administracion.addItem('Naturales', 'app.administracion.personas.buscarPersonaNatural');
                    administracion.addItem('Juridicas', 'app.administracion.personas.buscarPersonaJuridica');
                } else {
                    return [];
                }
            } else if(roles.indexOf('CAJERO') != -1){
                if(stateName.indexOf('app.organizacion') > -1){

                } else {
                    return [];
                }
            } else if(stateName.indexOf('app.administracion') > -1){
                var administracion = this.addItem('Personas', '', 'linecons-user');

                administracion.addItem('Naturales', 'app.administracion.personas.buscarPersonaNatural');
                administracion.addItem('Juridicas', 'app.administracion.personas.buscarPersonaJuridica');
            } else {
                return undefined;
            }

            return this;
        };
        this.prepareHorizontalMenu = function(roles) {
            if(roles.indexOf('ADMIN') != -1){
                var organizacion = this.addItem('Organizacion', 'app.organizacion', 'linecons-desktop');
                var administracion = this.addItem('Administracion', 'app.administracion', 'linecons-params');
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');
            } else if(roles.indexOf('GERENTE_GENERAL') != -1){
                var organizacion = this.addItem('Organizacion', 'app.organizacion', 'linecons-desktop');
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');
            } else if(roles.indexOf('ADMINISTRADOR_GENERAL') != -1){
                var organizacion = this.addItem('Organizacion', 'app.organizacion', 'linecons-desktop');
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');
            } else if(roles.indexOf('ADMINISTRADOR') != -1){
                var organizacion = this.addItem('Organizacion', 'app.organizacion', 'linecons-desktop');
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');
            } else if(roles.indexOf('PLATAFORMA') != -1){
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');
            } else if(roles.indexOf('JEFE_CAJA') != -1){
                var organizacion = this.addItem('Organizacion', 'app.organizacion', 'linecons-desktop');
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');
            } else if(roles.indexOf('CAJERO') != -1){
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');
            } else {
                return undefined;
            }

            //var organizacion = this.addItem('Organizacion', 'app.organizacion', 'linecons-cog');
            //var transacciones = this.addItem('Transacciones', '', 'linecons-note');
           // var socio = this.addItem('Socio', '', 'linecons-params');
            //var administracion = this.addItem('Administracion', '', 'linecons-desktop');

            //administracion.addItem('Personas', 'app.administracion');

            return this;
        };
        this.instantiate = function() {
            return angular.copy(this);
        };
        this.toStatePath = function(path) {
            return path.replace(/\//g, '.').replace(/^\./, '');
        };
        this.setActive = function(path) {
            this.iterateCheck(this.menuItems, this.toStatePath(path));
        };
        this.setActiveParent = function(item) {
            item.isActive = true;
            item.isOpen = true;
            if (item.parent)
                this.setActiveParent(item.parent);
        };
        this.iterateCheck = function(menuItems, currentState) {
            angular.forEach(menuItems, function(item) {
                if (item.state == currentState) {
                    item.isActive = true;
                    if (item.parent != null)
                        $menuItemsRef.setActiveParent(item.parent);
                } else {
                    item.isActive = false;
                    item.isOpen = false;
                    if (item.menuItems.length) {
                        $menuItemsRef.iterateCheck(item.menuItems, currentState);
                    }
                }
            });
        }
    });

});


define(['./module'], function (module) {
    'use strict';

    module.service('$menuItemsApp', function(activeProfile)
    {
        this.menuItems = [];

        var $menuItemsRef = this;

        var menuItemObj = {
            parent: null,

            title: '',
            link: '', // starting with "./" will refer to parent link concatenation
            state: '', // will be generated from link automatically where "/" (forward slashes) are replaced with "."
            icon: '',

            isActive: false,
            label: null,

            menuItems: [],

            setLabel: function(label, color, hideWhenCollapsed)
            {
                if(typeof hideWhenCollapsed == 'undefined')
                    hideWhenCollapsed = true;

                this.label = {
                    text: label,
                    classname: color,
                    collapsedHide: hideWhenCollapsed
                };

                return this;
            },

            addItem: function(title, link, icon)
            {
                var parent = this,
                    item = angular.extend(angular.copy(menuItemObj), {
                        parent: parent,

                        title: title,
                        link: link,
                        icon: icon
                    });

                if(item.link)
                {
                    if(item.link.match(/^\./))
                        item.link = parent.link + item.link.substring(1, link.length);

                    if(item.link.match(/^-/))
                        item.link = parent.link + '-' + item.link.substring(2, link.length);

                    item.state = $menuItemsRef.toStatePath(item.link);
                }

                this.menuItems.push(item);

                return item;
            }
        };

        this.addItem = function(title, link, icon)
        {
            var item = angular.extend(angular.copy(menuItemObj), {
                title: title,
                link: link,
                state: this.toStatePath(link),
                icon: icon
            });

            this.menuItems.push(item);

            return item;
        };

        this.getAll = function()
        {
            return this.menuItems;
        };

        this.prepareSidebarMenu = function(stateName, roles)
        {
            if(roles.indexOf('ADMIN') != -1){
                if(stateName.indexOf('app.admin.organizacion') > -1){
                    var estructura = this.addItem('Estructura', '', 'linecons-inbox');
                    var rrhh = this.addItem('RRHH', '', 'linecons-t-shirt');

                    estructura.addItem('Sucursales', 'app.admin.organizacion.estructura.buscarSucursal');
                    estructura.addItem('Agencias', 'app.admin.organizacion.estructura.buscarAgencia');
                    estructura.addItem('Bovedas', 'app.organizacion.estructura.buscarBoveda');
                    estructura.addItem('Cajas', 'app.organizacion.estructura.buscarCaja');
                    rrhh.addItem('Trabajadores', 'app.organizacion.rrhh.buscarTrabajador');
                    rrhh.addItem('Usuarios', 'app.organizacion.rrhh.buscarUsuario');

                } else if(stateName.indexOf('app.cliente') > -1){
                    var cuentaAporte = this.addItem('Cuentas aporte', '', 'linecons-eye');
                    var cuentaBancaria = this.addItem('Cuentas bancarias', '', 'linecons-globe');

                    cuentaAporte.addItem('Buscar cuentas', 'app.cliente.socio.buscarCuentaAporte');
                    cuentaBancaria.addItem('Crear cuenta ahorro', 'app.cliente.cuentaBancaria.crearCuentaAhorro');
                    cuentaBancaria.addItem('Crear cuenta plazo fijo', 'app.cliente.cuentaBancaria.crearCuentaPlazoFijo');
                    cuentaBancaria.addItem('Crear cuenta corriente', 'app.cliente.cuentaBancaria.crearCuentaCorriente');
                    cuentaBancaria.addItem('Buscar cuentas', 'app.cliente.cuentaBancaria.buscarCuentaBancaria');
                } else if(stateName.indexOf('app.transaccion') > -1){
                    var transaccionInterna = this.addItem('Interno', '', 'linecons-beaker');
                    var transaccionExterna = this.addItem('Externa', '', 'linecons-money');
                    var monitoreo = this.addItem('Monitoreo', '', 'linecons-mobile');

                    transaccionInterna.addItem('Transaccion cta. aporte', 'app.administracion.personas.buscarPersonaNatural');
                    transaccionInterna.addItem('Transaccion cta. bancaria', 'app.administracion.personas.buscarPersonaNatural');
                    transaccionInterna.addItem('Transaccion cta. cheque', 'app.administracion.personas.buscarPersonaNatural');
                    transaccionInterna.addItem('Transaccion cta. compra/venta', 'app.administracion.personas.buscarPersonaNatural');

                    transaccionExterna.addItem('Trans. Boveda/boveda', 'app.administracion.personas.buscarPersonaJuridica');
                    transaccionExterna.addItem('Trans. Boveda/caja', 'app.administracion.personas.buscarPersonaJuridica');
                    transaccionExterna.addItem('Trans. caja/caja', 'app.administracion.personas.buscarPersonaJuridica');
                } else if(stateName.indexOf('app.administracion') > -1){
                    var administracion = this.addItem('Personas', '', 'linecons-user');

                    administracion.addItem('Naturales', 'app.administracion.personas.buscarPersonaNatural');
                    administracion.addItem('Juridicas', 'app.administracion.personas.buscarPersonaJuridica');
                } else {
                    return [];
                }
            } else if(roles.indexOf('GERENTE_GENERAL') != -1){
                if(stateName.indexOf('app.gerentegeneral.organizacion') > -1){
                    var estructura = this.addItem('Estructura', '', 'linecons-inbox');
                    var rrhh = this.addItem('RRHH', '', 'linecons-t-shirt');

                    estructura.addItem('Sucursales', 'app.gerentegeneral.organizacion.estructura.buscarSucursal');
                    estructura.addItem('Agencias', 'app.gerentegeneral.organizacion.estructura.buscarAgencia');
                    estructura.addItem('Bovedas', 'app.organizacion.estructura.buscarBoveda');
                    estructura.addItem('Cajas', 'app.organizacion.estructura.buscarCaja');
                    rrhh.addItem('Trabajadores', 'app.organizacion.rrhh.buscarTrabajador');
                } else if(stateName.indexOf('app.administracion') > -1){
                    var administracion = this.addItem('Personas', '', 'linecons-user');

                    administracion.addItem('Naturales', 'app.administracion.personas.buscarPersonaNatural');
                    administracion.addItem('Juridicas', 'app.administracion.personas.buscarPersonaJuridica');
                } else {
                    return [];
                }
            } else if(roles.indexOf('ADMINISTRADOR_GENERAL') != -1){
                if(stateName.indexOf('app.administradorgeneral.organizacion') > -1){
                    var estructura = this.addItem('Estructura', '', 'linecons-inbox');
                    var rrhh = this.addItem('RRHH', '', 'linecons-t-shirt');

                    estructura.addItem('Agencias', 'app.administradorgeneral.organizacion.estructura.buscarAgencia');
                    estructura.addItem('Bovedas', 'app.organizacion.estructura.buscarBoveda');
                    estructura.addItem('Cajas', 'app.organizacion.estructura.buscarCaja');
                    rrhh.addItem('Trabajadores', 'app.organizacion.rrhh.buscarTrabajador');
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
                    var rrhh = this.addItem('RRHH', '', 'linecons-t-shirt');

                    estructura.addItem('Bovedas', 'app.organizacion.estructura.buscarBoveda');
                    estructura.addItem('Cajas', 'app.organizacion.estructura.buscarCaja');
                    rrhh.addItem('Trabajadores', 'app.organizacion.rrhh.buscarTrabajador');
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
                    var rrhh = this.addItem('RRHH', '', 'linecons-t-shirt');

                    estructura.addItem('Bovedas', 'app.organizacion.estructura.buscarBoveda');
                    estructura.addItem('Cajas', 'app.organizacion.estructura.buscarCaja');
                    rrhh.addItem('Trabajadores', 'app.organizacion.rrhh.buscarTrabajador');
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

        this.prepareHorizontalMenu = function()
        {
            var roles = activeProfile.realmAccess.roles;

            if(roles.indexOf('ADMIN') != -1){
                var organizacion = this.addItem('Organizacion', 'app.admin.organizacion', 'linecons-desktop');
                var cliente = this.addItem('Clientes', 'app.cliente', 'linecons-database');
                var transaccion = this.addItem('Transacciones', 'app.transaccion', 'linecons-doc');
                var administracion = this.addItem('Administracion', 'app.administracion', 'linecons-params');
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');


                var other  	 	 = this.addItem('Other', 			'', 				'linecons-beaker');

                // Subitems of Others
                var widgets     = other.addItem('Widgets', 			'/app/widgets', 			'linecons-star');
                var mailbox     = other.addItem('Mailbox', 			'', 			'linecons-mail').setLabel('5', 'secondary', false);
                var tables      = other.addItem('Tables', 			'/app/tables', 				'linecons-database');
                var extra       = other.addItem('Extra', 			'/app/extra', 				'linecons-beaker').setLabel('New Items', 'purple');
                var charts      = other.addItem('Charts', 			'/app/charts', 				'linecons-globe');
                var menu_lvls   = other.addItem('Menu Levels', 		'', 						'linecons-cloud');

            } else if(roles.indexOf('GERENTE_GENERAL') != -1){
                var organizacion = this.addItem('Organizacion', 'app.gerentegeneral.organizacion', 'linecons-desktop');
                var cliente = this.addItem('Clientes', 'app.cliente', 'linecons-database');
                var transaccion = this.addItem('Transacciones', 'app.transaccion', 'linecons-doc');
                var administracion = this.addItem('Administracion', 'app.administracion', 'linecons-params');
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');
            } else if(roles.indexOf('ADMINISTRADOR_GENERAL') != -1){
                var organizacion = this.addItem('Organizacion', 'app.administradorgeneral.organizacion', 'linecons-desktop');
                var cliente = this.addItem('Clientes', 'app.cliente', 'linecons-database');
                var transaccion = this.addItem('Transacciones', 'app.transaccion', 'linecons-doc');
                var administracion = this.addItem('Administracion', 'app.administracion', 'linecons-params');
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');
            } else if(roles.indexOf('ADMINISTRADOR') != -1){
                var organizacion = this.addItem('Organizacion', 'app.organizacion', 'linecons-desktop');
                var cliente = this.addItem('Clientes', 'app.cliente', 'linecons-database');
                var transaccion = this.addItem('Transacciones', 'app.transaccion', 'linecons-doc');
                var administracion = this.addItem('Administracion', 'app.administracion', 'linecons-params');
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');
            } else if(roles.indexOf('PLATAFORMA') != -1){
                var cliente = this.addItem('Clientes', 'app.cliente', 'linecons-database');
                var administracion = this.addItem('Administracion', 'app.administracion', 'linecons-params');
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');
            } else if(roles.indexOf('JEFE_CAJA') != -1){
                var organizacion = this.addItem('Organizacion', 'app.organizacion', 'linecons-desktop');
                var cliente = this.addItem('Clientes', 'app.cliente', 'linecons-database');
                var transaccion = this.addItem('Transacciones', 'app.transaccion', 'linecons-doc');
                var administracion = this.addItem('Administracion', 'app.administracion', 'linecons-params');
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');
            } else if(roles.indexOf('CAJERO') != -1){
                var cliente = this.addItem('Clientes', 'app.cliente', 'linecons-database');
                var transaccion = this.addItem('Transacciones', 'app.transaccion', 'linecons-doc');
                var administracion = this.addItem('Administracion', 'app.administracion', 'linecons-params');
                var configuracion = this.addItem('Configuracion', 'app.configuracion', 'linecons-cog');
            } else {
                return undefined;
            }

            return this;
        };

        this.instantiate = function()
        {
            return angular.copy( this );
        };

        this.toStatePath = function(path)
        {
            return path.replace(/\//g, '.').replace(/^\./, '');
        };

        this.setActive = function(path)
        {
            this.iterateCheck(this.menuItems, this.toStatePath(path));
        };

        this.setActiveParent = function(item)
        {
            item.isActive = true;
            item.isOpen = true;

            if(item.parent)
                this.setActiveParent(item.parent);
        };

        this.iterateCheck = function(menuItems, currentState)
        {
            angular.forEach(menuItems, function(item)
            {
                if(item.state == currentState)
                {
                    item.isActive = true;

                    if(item.parent != null)
                        $menuItemsRef.setActiveParent(item.parent);
                }
                else
                {
                    item.isActive = false;
                    item.isOpen = false;

                    if(item.menuItems.length)
                    {
                        $menuItemsRef.iterateCheck(item.menuItems, currentState);
                    }
                }
            });
        }
    });
});
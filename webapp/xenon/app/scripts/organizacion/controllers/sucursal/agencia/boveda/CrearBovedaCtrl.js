define(['../../../module'], function (module) {
    'use strict';

    module.controller('CrearBovedaCtrl', function($scope, $state, Sucursal, Agencia, Currency, activeProfile, Notifications){

        $scope.view = {
            boveda: undefined
        };

        $scope.combo = {
            sucursal: undefined,
            agencia: undefined,
            moneda: undefined
        };
        $scope.combo.selected = {
            sucursal: undefined,
            agencia: undefined,
            moneda: undefined
        };
        var comboSucursalListener = $scope.$watch('combo.selected.sucursal', function(){
            if(angular.isDefined($scope.combo.selected.sucursal)){
                $scope.combo.agencia = $scope.combo.selected.sucursal.$getAgencias().$object;
            }
        }, true);
        $scope.$watch('combo.selected.agencia', function(){
            if(angular.isDefined($scope.combo.selected.agencia)){
                $scope.combo.boveda = Agencia.$new($scope.combo.selected.agencia.id).$getBovedas().$object;
            }
        }, true);
        $scope.loadCombo = function(){
            if(activeProfile.hasRole('ORGANIZACION', ['ADMIN', 'GERENTE_GENERAL'], 'OR')){
                $scope.combo.sucursal = Sucursal.$search().$object;
            } else if(activeProfile.hasRole('ORGANIZACION', ['ADMINISTRADOR_GENERAL'], 'OR')){
                $scope.combo.sucursal = [];
                $scope.combo.sucursal[0] = $scope.auth.user.sucursal;
                $scope.combo.selected.sucursal = $scope.combo.sucursal[0];
            } else if(activeProfile.hasRole('ORGANIZACION', ['ADMINISTRADOR', 'JEFE_CAJA'], 'OR')){
                comboSucursalListener();
                $scope.combo.sucursal = [];
                $scope.combo.sucursal[0] = $scope.auth.user.sucursal;
                $scope.combo.agencia = [];
                $scope.combo.agencia[0] = $scope.auth.user.agencia;
                $scope.combo.selected.sucursal = $scope.combo.sucursal[0];
                $scope.combo.selected.agencia = $scope.combo.agencia[0];
            }

            $scope.combo.moneda = Currency.$search().$object;
        };
        $scope.loadCombo();

        $scope.addBoveda = function(){
            if($scope.form.$valid){
                $scope.view.boveda.moneda = $scope.combo.selected.moneda.code;
                Agencia.$new($scope.combo.selected.agencia.id).$addBoveda($scope.view.boveda).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Boveda creada");
                        $state.go('app.organizacion.estructura.editarBoveda.resumen', {id: response.id});
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            } else {
                $scope.form.$setSubmitted();
            }
        };

    }).controller('CrearBovedaFromAgenciaCtrl', function($scope, $state, Currency, Sucursal, Notifications){

        $scope.view = {
            agencia: $scope.$parent.view.agenciaDB,
            boveda: undefined
        };

        $scope.combo = {
            moneda: undefined
        };
        $scope.combo.selected = {
            moneda: undefined
        };
        $scope.loadCombo = function(){
            $scope.combo.moneda = Currency.$search().$object;
        };
        $scope.loadCombo();

        $scope.addBoveda = function(){
            if($scope.form.$valid){
                $scope.view.boveda.moneda = $scope.combo.selected.moneda.code;
                $scope.blockControl();
                $scope.view.agencia.$addBoveda($scope.view.boveda).then(
                    function(response){
                        $scope.unblockControl();
                        Notifications.success("Boveda creada");
                        $state.go('^.^.resumen');
                    },
                    function error(error){
                        $scope.unblockControl();
                        Notifications.error(error.data+".");
                    }
                );
            }
        };

    });
});

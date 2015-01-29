define(['./module'], function (module) {
    'use strict';

    module.directive('sgUbigeo',function(Departamento, Provincia, Distrito){
        return {
            restrict:'E',
            replace: false,
            require: ['^form','ngModel'],
            link: function($scope, elem, attrs, ngModel){

                ngModel[1].$validators.sgubigeo = function(modelValue,viewValue){
                    var value = modelValue || viewValue;
                    value = value ? value : '';
                    //false representa error y true represeta exito
                    if($scope.requerido)
                        return (value.length == 6);
                    else
                        return (value.length == 6  || value.length == 0);
                };

                Departamento.$search().then(function(data){
                    $scope.departamentos = data;
                    $scope.activeListener();
                });
                $scope.provincias = undefined;
                $scope.distritos = undefined;

                $scope.ubigeo = {
                    departamento: undefined,
                    provincia: undefined,
                    distrito: undefined
                };

                $scope.$watch('ubigeo.departamento', function(){
                    if(!angular.isUndefined($scope.ubigeo.departamento) && $scope.ubigeo.departamento){
                        $scope.provincias = $scope.ubigeo.departamento.provincias.$fetch().$object;
                        ngModel[1].$setDirty();
                    } else {
                        $scope.ubigeo.provincia = undefined;
                        $scope.ubigeo.distrito = undefined;

                        $scope.provincias = undefined;
                        $scope.distritos = undefined;
                    }
                });
                $scope.$watch('ubigeo.provincia', function(){
                    if(!angular.isUndefined($scope.ubigeo.provincia) && $scope.ubigeo.provincia){
                        $scope.distritos = Provincia.distritos($scope.ubigeo.departamento.codigo, $scope.ubigeo.provincia.codigo).$object;
                    } else {
                        $scope.ubigeo.distrito = undefined;

                        $scope.distritos = undefined;
                    }
                });
                $scope.$watch('ubigeo.distrito', function(){
                    if(!angular.isUndefined($scope.ubigeo.distrito) && $scope.ubigeo.distrito){
                        var ubigeo = $scope.ubigeo.departamento.codigo + $scope.ubigeo.provincia.codigo + $scope.ubigeo.distrito.codigo;
                        ngModel[1].$setViewValue(ubigeo);
                    }
                });

                $scope.activeListener = function(){
                    var listener = $scope.$watch(function(){return ngModel[1].$modelValue}, function(){
                        if( ngModel[1].$modelValue
                            && ngModel[1].$modelValue.length == 6
                            //&& angular.isUndefined($scope.departamentos)
                            && angular.isUndefined($scope.provincias)
                            && angular.isUndefined($scope.distritos)){

                            for(var i=0;i<$scope.departamentos.length;i++){
                                if($scope.departamentos[i].codigo == ngModel[1].$modelValue.substring(0, 2)){
                                    $scope.ubigeo.departamento = $scope.departamentos[i];
                                    break;
                                }
                            }

                            $scope.ubigeo.departamento.provincias.$fetch().then(function(data){
                                $scope.provincias = data;
                                for(var i=0;i<$scope.provincias.length;i++){
                                    if($scope.provincias[i].codigo == ngModel[1].$modelValue.substring(2, 4)){
                                        $scope.ubigeo.provincia = $scope.provincias[i];
                                        break;
                                    }
                                }

                                Provincia.distritos($scope.ubigeo.departamento.codigo, $scope.ubigeo.provincia.codigo).then(function(data){
                                    $scope.distritos = data;

                                    for(var i=0;i<$scope.distritos.length;i++){
                                        if($scope.distritos[i].codigo == ngModel[1].$modelValue.substring(4, 6)){
                                            $scope.ubigeo.distrito = $scope.distritos[i];
                                            break;
                                        }
                                    }
                                });

                            });
                            listener();
                        } else {
                            listener();
                        }
                    });
                };
            },
            scope: {
                requerido: '@'
            },
            template: ''
                +'<div class="row">'
                +'<div class="col-sm-4">'
                +'<div class="form-group" ng-class="{ \'has-error\' : formCrearPersonanatural.departamento.$invalid && (formCrearPersonanatural.departamento.$touched || formCrearPersonanatural.$submitted)}">'
                +'<label>Departamento</label>'
                +'<ui-select name="departamento" ng-model="ubigeo.departamento">'
                +'<ui-select-match placeholder="Seleccione">{{$select.selected.denominacion}}</ui-select-match>'
                +'<ui-select-choices repeat="item in departamentos | filter: $select.search">'
                +'<div ng-bind-html="item.denominacion | highlight: $select.search"></div>'
                +'<small ng-bind-html="item.codigo | highlight: $select.search"></small>'
                +'</ui-select-choices>'
                +'</ui-select>'
                +'<div ng-messages="formCrearPersonanatural.departamento.$error" ng-if="formCrearPersonanatural.departamento.$touched || formCrearPersonanatural.$submitted">'
                +'<div class="help-block" ng-message="required">Ingrese departamento.</div>'
                +'</div>'
                +'</div>'
                +'</div>'
                +'<div class="col-sm-4">'
                +'<div class="form-group" ng-class="{ \'has-error\' : formCrearPersonanatural.provincia.$invalid && (formCrearPersonanatural.provincia.$touched || formCrearPersonanatural.$submitted)}">'
                +'<label>Provincia</label>'
                +'<ui-select name="provincia" ng-model="ubigeo.provincia">'
                +'<ui-select-match placeholder="Seleccione">{{$select.selected.denominacion}}</ui-select-match>'
                +'<ui-select-choices repeat="item in provincias | filter: $select.search">'
                +'<div ng-bind-html="item.denominacion | highlight: $select.search"></div>'
                +'<small ng-bind-html="item.codigo | highlight: $select.search"></small>'
                +'</ui-select-choices>'
                +'</ui-select>'
                +'<div ng-messages="formCrearPersonanatural.provincia.$error" ng-if="formCrearPersonanatural.provincia.$touched || formCrearPersonanatural.$submitted">'
                +'<div class="help-block" ng-message="required">Ingrese provincia.</div>'
                +'</div>'
                +'</div>'
                +'</div>'
                +'<div class="col-sm-4">'
                +'<div class="form-group" ng-class="{ \'has-error\' : formCrearPersonanatural.provincia.$invalid && (formCrearPersonanatural.provincia.$touched || formCrearPersonanatural.$submitted)}">'
                +'<label>Distrito</label>'
                +'<ui-select name="distrito" ng-model="ubigeo.distrito">'
                +'<ui-select-match placeholder="Seleccione">{{$select.selected.denominacion}}</ui-select-match>'
                +'<ui-select-choices repeat="item in distritos | filter: $select.search">'
                +'<div ng-bind-html="item.denominacion | highlight: $select.search"></div>'
                +'<small ng-bind-html="item.codigo | highlight: $select.search"></small>'
                +'</ui-select-choices>'
                +'</ui-select>'
                +'<div ng-messages="formCrearPersonanatural.distrito.$error" ng-if="formCrearPersonanatural.distrito.$touched || formCrearPersonanatural.$submitted">'
                +'<div class="help-block" ng-message="required">Ingrese provincia.</div>'
                +'</div>'
                +'</div>'
                +'</div>'
                +'</div>'
        }
    });
});
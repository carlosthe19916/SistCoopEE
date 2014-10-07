angular.module('persona.directives', [])
    .directive('sgDocumentoIdentidad',function(TipoDocumento){
        return {
            restrict:'E',
            replace: false,
            require: '^form',
            link: function($scope, elem, attrs, ngModel){
                $scope.tiposDocumento = TipoDocumento.$search({tipoPersona: $scope.tipoPersona.toUpperCase()});
                $scope.sgForm = ngModel;

                $scope.view = {
                    sgTipoDocumento: undefined,
                    sgNumeroDocumento: undefined
                };

                $scope.$watch('view.sgTipoDocumento', function (value) {
                    $scope.numeroDocumento = '';
                    if(!angular.isUndefined($scope.view.sgTipoDocumento)){
                        $scope.tipoDocumento = $scope.view.sgTipoDocumento.abreviatura;
                    }
                    else {
                        $scope.tipoDocumento = undefined;
                    }
                });

            },
            scope: {
                nombre: '@',
                tipoPersona: '@',
                tipoDocumento: '=',
                numeroDocumento: '=',
                requerido: '@'
            },
            template: ''
                +'<div class="col-sm-4">'
                    +'<div class="form-group" ng-class="{ \'has-error\' : sgForm.sgTipoDocumento.$invalid && (sgForm.sgTipoDocumento.$touched || sgForm.$submitted)}">'
                        +'<label>Tipo documento</label>'
                        +'<ui-select name="sgTipoDocumento" ng-model="view.sgTipoDocumento" theme="bootstrap">'
                            +'<ui-select-match placeholder="Seleccione">{{$select.selected.abreviatura}}</ui-select-match>'
                            +'<ui-select-choices repeat="item in tiposDocumento | filter: $select.search">'
                            +'<div ng-bind-html="item.abreviatura | highlight: $select.search"></div>'
                            +'</ui-select-choices>'
                        +'</ui-select>'
                        +'<div ng-messages="sgForm.sgTipoDocumento.$error" ng-if="sgForm.sgTipoDocumento.$touched || sgForm.$submitted">'
                            +'<div class="help-block" ng-message="required">Ingrese documento.</div>'
                        +'</div>'
                    +'</div>'
                +'</div>'

                +'<div class="col-sm-4" ng-class="{ \'has-error\' : sgForm.sgNumeroDocumento.$invalid && (sgForm.sgNumeroDocumento.$touched || sgForm.$submitted)}">'
                    +'<div class="form-group">'
                        +'<label>N&uacute;mero documento</label>'
                        +'<input type="text" name="sgNumeroDocumento" ng-model="numeroDocumento" ng-pattern="/^[0-9]+$/" ng-minlength="view.sgTipoDocumento.cantidadCaracteres" ng-maxlength="view.sgTipoDocumento.cantidadCaracteres" class="form-control" ng-required="{{requerido}}"/>'
                        +'<div ng-messages="sgForm.sgNumeroDocumento.$error" ng-if="sgForm.sgNumeroDocumento.$touched || sgForm.$submitted">'
                            +'<div class="help-block" ng-message="required">Ingrese numero documento.</div>'
                            +'<div class="help-block" ng-message="minlength">Debe tener <span ng-bind="view.sgTipoDocumento.cantidadCaracteres"></span> digitos.</div>'
                            +'<div class="help-block" ng-message="maxlength">Debe tener <span ng-bind="view.sgTipoDocumento.cantidadCaracteres"></span> digitos.</div>'
                            +'<div class="help-block" ng-message="pattern">Numero invalido.</div>'
                        +'</div>'
                    +'</div>'
                +'</div>'
        }
    }).directive('sgUbigeo',function(Departamento, Provincia, Distrito){
        return {
            restrict:'E',
            replace: false,
            require: ['^form','ngModel'],
            link: function($scope, elem, attrs, ngModel){

                ngModel[1].$validators.sgubigeo = function(modelValue,viewValue){
                    var value = modelValue || viewValue;
                    return value.length == 6;
                };

                $scope.departamentos = Departamento.$search();
                $scope.provincias = undefined;
                $scope.distritos = undefined;

                $scope.ubigeo = {
                    departamento: undefined,
                    provincia: undefined,
                    distrito: undefined
                };

                $scope.changeDepartamento = function(){
                    if(!angular.isUndefined($scope.ubigeo.departamento)){
                        console.log($scope.ubigeo.departamento);
                        $scope.provincias = $scope.ubigeo.departamento.provincias.$fetch();
                    } else {
                        $scope.provincias = undefined;
                        $scope.distritos = undefined;
                    }
                };
                $scope.changeProvincia = function(){
                    if(!angular.isUndefined($scope.ubigeo.provincia)){
                        $scope.distritos = $scope.ubigeo.provincia.distritos.$fetch();
                    } else {
                        $scope.distritos = undefined;
                    }
                };
                $scope.changeDistrito = function(){
                    console.log("ubigeo:"+$scope.ubigeo.departamento.codigo+$scope.ubigeo.provincia.codigo+$scope.ubigeo.distrito.codigo);
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
                +'<select name="departamento" ng-options="dep.denominacion for dep in departamentos" ng-model="ubigeo.departamento" ng-change="changeDepartamento()" class="form-control" ng-required="{{requerido}}">'
                +'<option value="">--Seleccione--</option>'
                +'</select>'
                +'<div ng-messages="formCrearPersonanatural.departamento.$error" ng-if="formCrearPersonanatural.departamento.$touched || formCrearPersonanatural.$submitted">'
                +'<div class="help-block" ng-message="required">Ingrese departamento.</div>'
                +'</div>'
                +'</div>'
                +'</div>'
                +'<div class="col-sm-4">'
                +'<div class="form-group" ng-class="{ \'has-error\' : formCrearPersonanatural.provincia.$invalid && (formCrearPersonanatural.provincia.$touched || formCrearPersonanatural.$submitted)}">'
                +'<label>Provincia</label>'
                +'<select name="provincia" ng-options="provincia.codigo as provincia.denominacion for provincia in combos.provincias" ng-model="ubigeo.codigoProvincia" ng-change="changeProvincia()" class="form-control">'
                +'<option value="">--Seleccione--</option>'
                +'</select>'
                +'<div ng-messages="formCrearPersonanatural.provincia.$error" ng-if="formCrearPersonanatural.provincia.$touched || formCrearPersonanatural.$submitted">'
                +'<div class="help-block" ng-message="required">Ingrese provincia.</div>'
                +'</div>'
                +'</div>'
                +'</div>'
                +'<div class="col-sm-4">'
                +'<div class="form-group" ng-class="{ \'has-error\' : formCrearPersonanatural.provincia.$invalid && (formCrearPersonanatural.provincia.$touched || formCrearPersonanatural.$submitted)}">'
                +'<label>Distrito</label>'
                +'<select name="distrito" ng-options="distrito.codigo as distrito.denominacion for distrito in combos.distritos" ng-model="ubigeo.codigoDistrito" ng-change="changeDistrito()" class="form-control">'
                +'<option value="">--Seleccione--</option>'
                +'</select>'
                +'<div ng-messages="formCrearPersonanatural.distrito.$error" ng-if="formCrearPersonanatural.distrito.$touched || formCrearPersonanatural.$submitted">'
                +'<div class="help-block" ng-message="required">Ingrese provincia.</div>'
                +'</div>'
                +'</div>'
                +'</div>'
                +'</div>'
        }
    });

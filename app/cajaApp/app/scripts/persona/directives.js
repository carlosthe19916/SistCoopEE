angular.module('persona.directives', [])
    .directive('sgDocumentoIdentidad',function(TipoDocumento){
        return {
            restrict:'E',
            replace: false,
            require: '^form',
            link: function($scope, elem, attrs, ngModel){
                $scope.tiposDocumento = TipoDocumento.$search({tipoPersona: $scope.tipoPersona.toUpperCase()});
                $scope.sgForm = ngModel;

                $scope.$watch('sgTipoDocumento', function (value) {
                    $scope.numeroDocumento = '';
                    if(!angular.isUndefined($scope.sgTipoDocumento))
                        $scope.tipoDocumento = $scope.sgTipoDocumento.abreviatura;
                    else
                        $scope.tipoDocumento = undefined;
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
                +'<select name="sgTipoDocumento" ng-model="sgTipoDocumento" ng-options="doc.abreviatura for doc in tiposDocumento" class="form-control" ng-required="{{requerido}}">'
                +'<option value="">--Seleccione--</option>'
                +'</select>'
                +'<div ng-messages="sgForm.sgTipoDocumento.$error" ng-if="sgForm.sgTipoDocumento.$touched || sgForm.$submitted">'
                +'<div class="help-block" ng-message="required">Ingrese documento.</div>'
                +'</div>'
                +'</div>'
                +'</div>'
                +'<div class="col-sm-4" ng-class="{ \'has-error\' : sgForm.sgNumeroDocumento.$invalid && (sgForm.sgNumeroDocumento.$touched || sgForm.$submitted)}">'
                +'<div class="form-group">'
                +'<label>N&uacute;mero documento</label>'
                +'<input type="text" name="sgNumeroDocumento" ng-model="numeroDocumento" ng-pattern="/^[0-9]+$/" ng-minlength="sgTipoDocumento.cantidadCaracteres" ng-maxlength="sgTipoDocumento.cantidadCaracteres" class="form-control" placeholder="N&uacute;mero documento" ng-required="{{requerido}}"/>'
                +'<div ng-messages="sgForm.sgNumeroDocumento.$error" ng-if="sgForm.sgNumeroDocumento.$touched || sgForm.$submitted">'
                +'<div class="help-block" ng-message="required">Ingrese numero documento.</div>'
                +'<div class="help-block" ng-message="minlength">Debe tener <span ng-bind="sgTipoDocumento.cantidadCaracteres"></span> digitos.</div>'
                +'<div class="help-block" ng-message="maxlength">Debe tener <span ng-bind="sgTipoDocumento.cantidadCaracteres"></span> digitos.</div>'
                +'<div class="help-block" ng-message="pattern">Numero invalido.</div>'
                +'</div>'
                +'</div>'
                +'</div>'
        }
    });

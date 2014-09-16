define(['./module'], function (directives) {
    'use strict';
    directives.directive('sgDocumentoIdentidad',function(TipoDocumento){
        return {
            restrict:'EA',
            link: function($scope, elem, attrs, ngModel){
                $scope.$watch(attrs.tipoDocumento, function(value){
                    console.log(value);
                });
            },
            template: ''
                +'<div class="col-sm-4">'
                    +'<div class="form-group">'
                        +'<label>Tipo documento</label>'
                        +'<select name="tipoDocumento" ng-model="tipoDocumento" class="form-control" required>'
                            +'<option value="">--Seleccione--</option>'
                        +'</select>'
                    +'</div>'
                +'</div>'
                +'<div class="col-sm-4">'
                    +'<div class="form-group">'
                        +'<label>N&uacute;mero documento</label>'
                        +'<input name="numeroDocumento" type="text" ng-pattern="/^[0-9]+$/" ng-minlength="1" ng-maxlength="20" class="form-control" placeholder="N&uacute;mero Documento" required/>'
                    +'</div>'
                +'</div>'
        }
    });
});
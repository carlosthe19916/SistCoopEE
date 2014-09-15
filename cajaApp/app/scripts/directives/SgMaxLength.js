define(['./module'], function (directives) {
    'use strict';
    directives.directive('sgMaxlength',function(){
        return {
            restrict:'AE',
            require:'ngModel',
            link: function($scope, elem, attrs, ngModel){
                $scope.$watch(attrs.sgMaxlength, function (value){
                    if(!value)
                        value = 0;
                    ngModel.$validators.sgmaxlength = function(modelValue, viewValue){
                        var valueModel = modelValue || viewValue;
                        if(!valueModel)
                            valueModel = '';
                        return valueModel.length <= value ? true : false;
                    };
                });
            }
        }
    });
});
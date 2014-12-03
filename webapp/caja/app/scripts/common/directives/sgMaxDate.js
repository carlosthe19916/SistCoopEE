define(['module'], function (module) {
    'use strict';

    module.directive('sgMaxDate', function() {
        return {
            require: 'ngModel',
            link: function($scope, elem, attrs, ngModel) {
                ngModel.$validators.sgmaxdate = function(modelValue,viewValue){
                    var value = modelValue || viewValue;
                    return $scope.maxDate >= value;
                }
            }
        };
    });
});
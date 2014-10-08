
angular.module('common.directives', [])
    .directive('capitalize', function() {
        return {
            require: 'ngModel',
            link: function(scope, element, attrs, modelCtrl) {
                var capitalize = function(inputValue) {
                    if(typeof inputValue === 'undefined')
                        return undefined;
                    var capitalized = inputValue.toUpperCase();
                    if(capitalized !== inputValue) {
                        modelCtrl.$setViewValue(capitalized);
                        modelCtrl.$render();
                    }
                    return capitalized;
                };
                modelCtrl.$parsers.push(capitalize);
                capitalize(scope[attrs.ngModel]);  // capitalize initial value
            }
        };
    })
    .directive('sgMaxDate', function() {
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

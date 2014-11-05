
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
    }).directive('sgMaxDate', function() {
        return {
            require: 'ngModel',
            link: function($scope, elem, attrs, ngModel) {
                ngModel.$validators.sgmaxdate = function(modelValue,viewValue){
                    var value = modelValue || viewValue;
                    return $scope.maxDate >= value;
                }
            }
        };
    })
    .directive('autofocus', function($timeout) {
        return {
            restrict: 'A',
            link : function($scope, $element) {
                $timeout(function() {
                    $element[0].focus();
                });
            }
        };
    })
    .directive('uiAutofocus', function($timeout) {
        return {
            restrict: 'A',
            require: 'uiSelect',
            link: function(scope, elem, attr) {
                if(elem.hasClass('ui-select-bootstrap')){
                    if(elem[0].children.length == 4){
                        $timeout(function() {
                            if(attr.uiAutofocus == 'open')
                                elem.find("button.ui-select-match").click();
                            elem.find("input.ui-select-focusser").focus();
                        }, 0);
                    }
                }
            }
        };
    });



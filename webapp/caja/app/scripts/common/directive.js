
(function(window, angular, undefined) {'use strict';

    angular.module('common.directives', [])
        .directive('capitalize', function() {
            return {
                require: 'ngModel',
                link: function(scope, element, attrs, modelCtrl) {
                    var capitalize = function(inputValue) {
                        if(typeof inputValue === 'undefined')
                            return undefined;
                        if(inputValue === null)
                            return null;
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
        })
        .directive('uiSelectAutoload', function(Util) {
            return {
                restrict: 'E',
                require: '^uiSelect',
                scope: {
                    //@ one way binding; = two way binding
                    pkName: '@',
                    comparator: '@',
                    ignoreCase: '@'
                },
                controller: function($scope) {
                    if(angular.isDefined($scope.comparator) && $scope.comparator){
                        var listener = $scope.$parent.$watch('$select.items',function(newValue, oldValue){
                            if(angular.isDefined(newValue) && newValue.length){
                                var items = $scope.$parent.$select.items;
                                var selected = Util.getElementOfArray(items, $scope.pkName, $scope.comparator, $scope.ignoreCase);
                                $scope.$parent.$select.ngModel.$setViewValue(selected);
                                $scope.$parent.$select.ngModel.$render();
                                //unwatch listener
                                listener();
                            }
                        },true);
                    }
                }
            };
        })
        ;

})(window, window.angular);



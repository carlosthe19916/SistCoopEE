define(['./module'], function (module) {
    'use strict';

    module.directive('uiSelectAutoload', function(Util) {
        return {
            restrict: 'E',
            require: '^uiSelect',
            scope: {
                //@ one way binding; = two way binding
                pkName: '@',
                comparator: '=',
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
                    }, true);
                }

                $scope.$parent.$watch('$select.ngModel.$modelValue', function(newValue, oldValue){
                    if(angular.isDefined(newValue)){
                        $scope.comparator = $scope.$parent.$select.ngModel.$modelValue[$scope.pkName];
                    }
                }, true);
            }
        };
    });
});
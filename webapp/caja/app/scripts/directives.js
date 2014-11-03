module.directive('sgDataKeep', function(ngIfDirective, activeProfile) {
    var ngIf = ngIfDirective[0];
    return {
        transclude: ngIf.transclude,
        priority: ngIf.priority,
        terminal: ngIf.terminal,
        restrict: ngIf.restrict,
        link: function($scope, $element, $attr) {
            var value = $attr['sgDataKeep'];
            var yourCustomValue = $scope.$eval(value);
            $attr.ngIf = function() {
                return activeProfile.hasPermission(yourCustomValue.module, yourCustomValue.permission);
            };
            ngIf.link.apply(ngIf, arguments);
        }
    };
});
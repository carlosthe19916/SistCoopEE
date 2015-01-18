define(['./module'], function (module) {
    'use strict';

    //{module: 'PERSONA', permission: 'UPDATE'}
    //{module: 'PERSONA', rol: 'ADMIN'}
    //{module: 'PERSONA', rol: ['ADMIN', 'USER']}
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
                    if(angular.isDefined(yourCustomValue.rol)){
                        return activeProfile.hasRole(yourCustomValue.module, yourCustomValue.rol, 'OR');
                    }
                    else if (angular.isDefined(yourCustomValue.permission)){
                        return activeProfile.hasPermission(yourCustomValue.module, yourCustomValue.permission);
                    }
                    else {
                        return false;
                    }
                };
                ngIf.link.apply(ngIf, arguments);
            }
        };
    });
});
define(['./module'], function (module) {
    'use strict';

    module.directive('autofocus', function($timeout) {
        return {
            restrict: 'A',
            link : function($scope, $element) {
                $timeout(function() {
                    $element[0].focus();
                });
            }
        };
    });
});
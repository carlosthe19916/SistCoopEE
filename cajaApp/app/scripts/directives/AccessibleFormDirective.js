define(['./module'], function (directives) {
    'use strict';
    directives.directive('accessibleForm', function () {
        return {
            retrict: 'E',
            link: function (scope, elem) {

                // set up event handler on the form element
                elem.on('submit', function () {

                    // find the first invalid element
                    var firstInvalid = angular.element(
                        elem[0].querySelector('.ng-invalid'))[0];

                    // if we find one, set focus
                    if (firstInvalid) {
                        firstInvalid.focus();
                    }
                });
            }
        };
    });
});
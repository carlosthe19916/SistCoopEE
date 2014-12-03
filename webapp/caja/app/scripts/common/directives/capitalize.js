define(['module'], function (module) {
    'use strict';

    module.directive('capitalize', function() {
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
    });
});
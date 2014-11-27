
(function(window, angular, undefined) {'use strict';

    angular.module('common.filters', [])
        .filter('si_no', function() {
            return function(input, mode) {
                var defaultResult = ['Si', 'No'];
                var modeOneResult = ['Activo', 'Inactivo'];

                var result = defaultResult;
                if(mode){
                    if(mode.toLowerCase() == 'activo')
                        result = modeOneResult;
                }

                if (input) {
                    return result[0];
                }
                return [1];
            }
        });

})(window, window.angular);



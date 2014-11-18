
(function(window, angular, undefined) {'use strict';

    angular.module('common.filters', [])
        .filter('si_no', function() {
            return function(text, length, end) {
                if (text) {
                    return 'Si';
                }
                return 'No';
            }
        });

})(window, window.angular);




(function(window, angular, undefined) {'use strict';

    angular.module('common.services', [])
        .factory('Storage', function(){
            var object = undefined;
            return {
                setObject: function(elem){
                    object = elem;
                },
                getObject: function(){
                    var obj = angular.copy(object);
                    object = undefined;
                    return obj;
                }
            }
        });

})(window, window.angular);


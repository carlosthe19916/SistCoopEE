define(['./module'], function (module) {
    'use strict';

    module.factory('Storage', function(){
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
});
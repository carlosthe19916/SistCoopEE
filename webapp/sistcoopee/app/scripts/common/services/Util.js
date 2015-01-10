define(['./module'], function (module) {
    'use strict';

    module.factory('Util', function(){
        return {
            getElementOfArray: function(object, attributeName, toCompare, ignoreCase){
                if(object.length){
                    for(var i = 0; i<object.length; i++){
                        var attribute1 = angular.isDefined(ignoreCase) ? object[i][attributeName.toString()].toLowerCase() : object[i][attributeName.toString()];
                        var attribute2 = angular.isDefined(ignoreCase) ? toCompare.toLowerCase() : toCompare;
                        if(attribute1 == attribute2)
                            return object[i];
                    }
                }
                return undefined;
            }
        }
    });
});
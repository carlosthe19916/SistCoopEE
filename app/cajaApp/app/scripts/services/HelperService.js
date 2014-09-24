define(['./module'], function (services) {
    'use strict';
    services.factory('HelperService',function(){

        return {
            getTipoDocumentoLength: function(combo, id){
                if(!angular.isUndefined(combo) && !angular.isUndefined(id)){
                    var result = undefined;
                    for(var i = 0; i < combo.length; i++)
                        if(combo[i].getId() == id)
                            result = combo[i];
                    if(!angular.isUndefined(result)){
                        return result.getMaxLength();
                    }
                }
                return undefined;
            }
        }
    })
});
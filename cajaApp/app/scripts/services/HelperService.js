define(['./module'], function (services) {
    'use strict';
    services.factory('HelperService',function(){

        return {
            getTipoDocumentoLength: function(combo, id){
                if(!combo && !id){
                    var result = undefined;
                    for(var i = 0; i < combo.length; i++)
                        if(combo[i].getId() == id)
                            result = combo[i];
                    if(!result)
                        return result.getMaxLength();
                }
                return undefined;
            }
        }
    })
});
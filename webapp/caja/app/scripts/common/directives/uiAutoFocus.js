define(['module'], function (module) {
    'use strict';

    module.directive('uiAutofocus', function($timeout) {
        return {
            restrict: 'A',
            require: 'uiSelect',
            link: function(scope, elem, attr) {
                if(elem.hasClass('ui-select-bootstrap')){
                    if(elem[0].children.length == 4){
                        $timeout(function() {
                            if(attr.uiAutofocus == 'open')
                                elem.find("button.ui-select-match").click();
                            elem.find("input.ui-select-focusser").focus();
                        }, 0);
                    }
                }
            }
        };
    });
});
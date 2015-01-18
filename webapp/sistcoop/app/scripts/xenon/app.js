define(['./module'], function (app) {
    'use strict';

    app.run(function()
    {
        // Page Loading Overlay
        public_vars.$pageLoadingOverlay = jQuery('.page-loading-overlay');

        jQuery(window).load(function()
        {
            public_vars.$pageLoadingOverlay.addClass('loaded');
        });

        setTimeout(function(){ public_vars.$pageLoadingOverlay.addClass('loaded'); }, 200);

    });

});
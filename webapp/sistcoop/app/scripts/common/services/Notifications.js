define(['./module'], function (module) {
    'use strict';

    module.factory('Notifications', function($rootScope, $timeout) {
        // time (in ms) the notifications are shown
        var delay = 5000;

        var notifications = {};

        var scheduled = null;
        var schedulePop = function() {
            if (scheduled) {
                $timeout.cancel(scheduled);
            }

            scheduled = $timeout(function() {
                $rootScope.notification = null;
                scheduled = null;
            }, delay);
        };

        if (!$rootScope.notifications) {
            $rootScope.notifications = [];
        }

        notifications.message = function(type, header, message) {
            $rootScope.notification = {
                type : type,
                header: header,
                message : message
            };

            schedulePop();
        };

        notifications.info = function(message) {
            notifications.message("info", "Info!", message);
        };

        notifications.success = function(message) {
            notifications.message("success", "Success!", message);
        };

        notifications.error = function(message) {
            notifications.message("danger", "Error!", message);
        };

        notifications.warn = function(message) {
            notifications.message("warning", "Warning!", message);
        };

        return notifications;
    });
});
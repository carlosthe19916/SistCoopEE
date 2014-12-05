define(['./app'], function(app) {
    'use strict';

    app.service('Dialog', function($modal) {
        var dialog = {};

        var openDialog = function(title, message, btns) {
            var controller = function($scope, $modalInstance, title, message, btns) {
                $scope.title = title;
                $scope.message = message;
                $scope.btns = btns;

                $scope.ok = function () {
                    $modalInstance.close();
                };
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            };

            return $modal.open({
                templateUrl: 'templates/kc-modal.html',
                controller: controller,
                resolve: {
                    title: function() {
                        return title;
                    },
                    message: function() {
                        return message;
                    },
                    btns: function() {
                        return btns;
                    }
                }
            }).result;
        };

        var escapeHtml = function(str) {
            var div = document.createElement('div');
            div.appendChild(document.createTextNode(str));
            return div.innerHTML;
        };

        dialog.confirmDelete = function(name, type, success) {
            var title = 'Delete ' + escapeHtml(type.charAt(0).toUpperCase() + type.slice(1));
            var msg = 'Are you sure you want to permanently delete the ' + type + ' ' + name + '?';
            var btns = {
                ok: {
                    label: 'Delete',
                    cssClass: 'btn btn-danger'
                },
                cancel: {
                    label: 'Cancel',
                    cssClass: 'btn btn-default'
                }
            };

            openDialog(title, msg, btns).then(success);
        };

        dialog.confirmGenerateKeys = function(name, type, success) {
            var title = 'Generate new keys for realm';
            var msg = 'Are you sure you want to permanently generate new keys for ' + name + '?';
            var btns = {
                ok: {
                    label: 'Generate Keys',
                    cssClass: 'btn btn-danger'
                },
                cancel: {
                    label: 'Cancel',
                    cssClass: 'btn btn-default'
                }
            };

            openDialog(title, msg, btns).then(success);
        };

        dialog.confirm = function(title, message, success, cancel) {
            var btns = {
                ok: {
                    label: title,
                    cssClass: 'btn btn-danger'
                },
                cancel: {
                    label: 'Cancel',
                    cssClass: 'btn btn-default'
                }
            };

            openDialog(title, message, btns).then(success, cancel);
        };

        return dialog
    });

    app.factory('Notifications', function($rootScope, $timeout) {
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

    app.factory('TimeUnit', function() {
        var t = {};

        t.autoUnit = function(time) {
            if (!time) {
                return 'Hours';
            }

            var unit = 'Seconds';
            if (time % 60 == 0) {
                unit = 'Minutes';
                time  = time / 60;
            }
            if (time % 60 == 0) {
                unit = 'Hours';
                time = time / 60;
            }
            if (time % 24 == 0) {
                unit = 'Days';
                time = time / 24;
            }
            return unit;
        };

        t.toSeconds = function(time, unit) {
            switch (unit) {
                case 'Seconds': return time;
                case 'Minutes': return time * 60;
                case 'Hours': return time * 3600;
                case 'Days': return time * 86400;
                default: throw 'invalid unit ' + unit;
            }
        };

        t.toUnit = function(time, unit) {
            switch (unit) {
                case 'Seconds': return time;
                case 'Minutes': return Math.ceil(time / 60);
                case 'Hours': return Math.ceil(time / 3600);
                case 'Days': return Math.ceil(time / 86400);
                default: throw 'invalid unit ' + unit;
            }
        };

        t.convert = function(time, from, to) {
            var seconds = t.toSeconds(time, from);
            return t.toUnit(seconds, to);
        };

        return t;
    });

});


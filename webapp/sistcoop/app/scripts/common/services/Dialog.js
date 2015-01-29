define(['./module'], function (module) {
    'use strict';

    module.service('Dialog', function($modal) {
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
                templateUrl: appHelper.viewPath("tpls/sg-modal"),
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
            var title = 'Eliminar ' + escapeHtml(type.charAt(0).toUpperCase() + type.slice(1));
            var msg = '¿Estas seguro de querer eliminar permanentemente el/la ' + type + ' ' + name + '?';
            var btns = {
                ok: {
                    label: 'Eliminar',
                    cssClass: 'btn btn-danger'
                },
                cancel: {
                    label: 'Cancelar',
                    cssClass: 'btn btn-white'
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
                    cssClass: 'btn btn-white'
                }
            };

            openDialog(title, message, btns).then(success, cancel);
        };

        return dialog
    });

});
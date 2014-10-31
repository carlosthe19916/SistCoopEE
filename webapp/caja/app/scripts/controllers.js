module.controller('GlobalCtrl', function($scope, $timeout, $http, Auth, $location, Notifications) {

    $scope.authUrl = authUrl;
    $scope.auth = Auth;

    $scope.$watch(function() {
        return $location.path();
    }, function() {
        $scope.fragment = $location.path();
        $scope.path = $location.path().substring(1).split("/");
    });

   /* $scope.auth.authz.loadUserProfile().success(function(profile) {
        $scope.auth.user = profile;
    }).error(function() {
        Notifications.error("Usuario no pudo ser cargado");
    });*/

});
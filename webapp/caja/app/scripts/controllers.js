module.controller('GlobalCtrl', function($scope, $timeout, $http, Auth, $location, Notifications, activeProfile) {

    $scope.authUrl = authUrl;
    $scope.auth = Auth;

    $scope.$watch(function() {
        return $location.path();
    }, function() {
        $scope.fragment = $location.path();
        $scope.path = $location.path().substring(1).split("/");
    });

    $scope.activeProfile = activeProfile;
    $scope.auth.user = {};
    $scope.auth.user.username = activeProfile.idToken.preferred_username;

    $scope.logout = function(){
        $scope.auth.authz.logout();
    };
    /*$scope.auth.authz.loadUserProfile().success(function(profile) {
        $scope.auth.user = profile;
    }).error(function() {
        Notifications.error("Usuario no pudo ser cargado");
    });*/

});
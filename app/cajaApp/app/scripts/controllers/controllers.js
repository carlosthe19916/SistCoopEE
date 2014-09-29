module.controller('GlobalCtrl', function($scope, $http, Auth, $location, Notifications) {
    $scope.addMessage = function() {
        Notifications.success("test");
    };

    $scope.authUrl = authUrl;
    $scope.auth = Auth;

    $scope.$watch(function() {
        return $location.path();
    }, function() {
        $scope.fragment = $location.path();
        $scope.path = $location.path().substring(1).split("/");
    });
});
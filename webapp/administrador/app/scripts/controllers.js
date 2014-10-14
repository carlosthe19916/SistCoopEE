module.controller('GlobalCtrl', function($scope, $timeout, $http, Auth, $location, Notifications) {
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



    $scope.idletimeout = false;
   /* $scope.clock = 60;

    $scope.checkToken = function(){
        $timeout(function() {
            var checked = $scope.auth.authz.isTokenExpired(60);
            if(checked){
                $scope.idletimeout = true;
                if($scope.clock == 60)
                    $timeout(tick, 1000);
            }
            $scope.checkToken();
        }, 1000 * 5)
    };
    $scope.checkToken();

    $scope.updateToken = function(){
        $scope.auth.authz.updateToken().success(function(refreshed) {
            $scope.idletimeout = false;
            $scope.clock = 60;
        }).error(function() {
            alert('failed to refresh the token, or the session has expired');
        });
    };

    var tick = function() {
        $scope.clock = $scope.clock - 1; // get the current time
        if($scope.clock != 0){
            $timeout(tick, 1000); // reset the timer
        }
        else {
            $scope.clock = 60;
            $scope.auth.authz.logout();
        }
    };*/




});
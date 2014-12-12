var convertGPlaceToThumbPlace = function(gplace) {
	with (gplace) {
		return {
				id:place_id, 
				provider:scope, 
				name:name, 
				longitude:geometry.location.k, 
				latitude:geometry.location.B
		};
	};	
};

var app = angular.module('thumb', ['ngRoute', 'ngGPlaceAutocomplete', 'ui.bootstrap']);

app.factory('UserService', function($http, $rootScope) {
	var user = principal;
	var userProfile;
	var promise = $http.get('/TheThumb/rest/userprofile/' + user).success(function(data) {
        userProfile = data;
    }). 
    error(function(data) {
    	$rootScope.$broadcast('error', {message: "Could not load user from server: " + data});
    });	  
	  return {
	    promise:promise,		  
	    getUser: function() { 
	    	return user; 
	    }, 	
	    getUserProfile: function() {
	    	return userProfile; 
	    }
	  };
	});

app.controller('AlertController', function ($scope, $timeout) {
	$scope.alerts = [];
    // Picks up the event to display a saved message.
    $scope.$on('saved', function (event, data) {
        $scope.alerts.unshift({ type: 'success', msg: data});
        $timeout(function() {
            $scope.alerts.pop();
          }, 5000);         
    });     
 
    // Picks up the event to display a server error message.
    $scope.$on('error', function (event, data) {
        $scope.alerts.unshift({ type: 'danger', msg: data});
        $timeout(function() {
            $scope.alerts.pop();
          }, 5000);        
    });        
 
    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});

app.controller('MainController', function($scope, $rootScope, $http, $location, UserService) {
	    /* load the profile of the logged in user into the root scope, as we'll need it in most controllers */
	    $scope.userProfile = UserService.getUserProfile();
	    $http.get('/TheThumb/rest/userprofile/' + UserService.getUser() + '/requests').success(function(data) {
		    $scope.rideRequests = data;
	    }). 
	    error(function(data) {
	    	$rootScope.$broadcast('error', {message: "Could not load ride requests from server: " + data});
	    });	

        $scope.accept = function(idx) {
        	var rq = $scope.rideRequests[idx];     	
            $http.post('/TheThumb/rest/ride/' + rq.ride.id + '/passengers', rq.requester).success(function(data){
            	$rootScope.$broadcast('saved', {message: "Passenger succesfully added to ride"});
                rq.status = "APPROVED";
                $http.post('/TheThumb/rest/request/', rq).success(function(data) {
        	    	$rootScope.$broadcast('saved', {message: "Ride request approved"});
        	    }).error(function(data) {
        	    	$rootScope.$broadcast('error', {message: "Ride request could not be approved: " + data});
        	    });        		
            }). 
    	    error(function(data) {
    	    	$rootScope.$broadcast('error', {message: "Passenger could not be added to the ride: " + data});
    	    });            
        };

        $scope.reject = function(idx) {
        	var rq = $scope.rideRequests[idx];
            $http.delete('/TheThumb/rest/ride/' + rq.ride.id + '/passengers/' + rq.requester.id).success(function(data){
    	    	$rootScope.$broadcast('saved', {message: "Passenger was removed from the ride"});            	
                rq.status = "REJECTED";
                $http.post('/TheThumb/rest/request/', rq).success(function(data) {
        	    	$rootScope.$broadcast('saved', {message: "Ride request rejected"});
        	    }).error(function(data) {
        	    	$rootScope.$broadcast('error', {message: "Failed to reject ride request: " + data});
        	    });        		        		
            }). 
    	    error(function(data) {
    	    	$rootScope.$broadcast('error', {message: "Passenger could not be removed from the ride: " + data});
    	    });                  
        };	    
    });
	
app.controller('AllRidesController', function($scope, $rootScope, $http, $location, UserService) {
		$http.get('/TheThumb/rest/ride').success(function(data) {
			$scope.rides = data;
		}). 
	    error(function(data) {
	    	$rootScope.$broadcast('error', {message: "Failed to load rides from server: " + data});
	    });
		$scope.requestRide = function(idx) {
			var request = {requester:UserService.getUserProfile(), ride:$scope.rides[idx], status:"INITIAL"};
			$http.post('/TheThumb/rest/request/', request).success(function(data) {
    	    	$rootScope.$broadcast('saved', {message: "Ride request sent"});
    	    }).error(function(data) {
		    	$rootScope.$broadcast('error', {message: "Failed to send ride request: " + data});
		    });
		};		
	});
	
app.controller('AllThumbsController', function($scope, $rootScope, $http, $location) {
		$http.get('/TheThumb/rest/thumb').success(function(data) {
			$scope.thumbs = data;
		}). 
	    error(function(data) {
	    	$rootScope.$broadcast('error', {message: "Failed to load thumbs from server: " + data});
	    });
	});    	
	
app.controller('ThumbController', function($scope, $rootScope, $http, $location, UserService) {
	    $scope.user = UserService.getUser();	
	    $scope.minDate = new Date();	    
	    $scope.open = function($event) {
	        $event.preventDefault();
	        $event.stopPropagation();

	        $scope.opened = true;
	    };	    
		$scope.raiseThumb = function() {		
			$scope.newThumb.thumb.hitchiker = UserService.getUserProfile();
			$scope.newThumb.thumb.origin = convertGPlaceToThumbPlace($scope.newThumb.originFull);						
			$scope.newThumb.thumb.destination = convertGPlaceToThumbPlace($scope.newThumb.destinationFull);						
			$http.post('/TheThumb/rest/thumb', $scope.newThumb.thumb).success(function(data) {
    	    	$rootScope.$broadcast('saved', {message: "Succesfully raised a thumb"});				
			    $location.path('/');
			}). 
		    error(function(data) {
		    	$rootScope.$broadcast('error', {message: "Could not raise a thumb: " + data});
		    });
		};
	});

app.controller('RidesController', function($scope, $rootScope, $http, $location, UserService) {
	    $scope.user = UserService.getUser();		
	    $scope.minDate = new Date();	    
	    $scope.open = function($event) {
	        $event.preventDefault();
	        $event.stopPropagation();

	        $scope.opened = true;
	    };	    	    
		$scope.createRide = function() {	
			/* adapt properties of the sent ride object */
			$scope.newRide.ride.driver = UserService.getUserProfile();
			$scope.newRide.ride.origin = convertGPlaceToThumbPlace($scope.newRide.originFull);						
			$scope.newRide.ride.destination = convertGPlaceToThumbPlace($scope.newRide.destinationFull);			
			$http.post('/TheThumb/rest/ride', $scope.newRide.ride).success(function(data) {
    	        $rootScope.$broadcast('saved', {message: "Ride created"});				
				$location.path('/');
			}). 
		    error(function(data) {
		    	$rootScope.$broadcast('error', {message: "Failed to create a ride: " + data});
		    });
		};
	})
	.controller('UserProfilesController', function($scope, $rootScope, $http, $routeParams, UserService) {
		$http.get('/TheThumb/rest/userprofile/' + $routeParams.userName).success(function(data) {
			$scope.userProfile = data;
		}). 
	    error(function(data) {
	    	$rootScope.$broadcast('error', {message: "Failed to load user profile data: " + data});
	    });
	})
	.config(['$routeProvider', function ($routeProvider) {
		$routeProvider.
		    when('/', {
		    	templateUrl: "views/main.html",
	    		controller: "MainController", 
	    		   resolve:{'UserServiceData':function(UserService){ return UserService.promise;}}
            }).		
			when('/allrides', {
				templateUrl: "views/allrides.html",
				controller: "AllRidesController", 
      		    resolve:{'UserServiceData':function(UserService){ return UserService.promise;}}				
			}).
			when('/allthumbs', {
				templateUrl: "views/allthumbs.html",
				controller: "AllThumbsController", 
      		    resolve:{'UserServiceData':function(UserService){ return UserService.promise;}}								
			}).			
			when('/rides/create', {
				templateUrl: "views/createride.html",
				controller: "RidesController", 
      		    resolve:{'UserServiceData':function(UserService){ return UserService.promise;}}								
			}).
			when('/thumbs/raise', {
				templateUrl: "views/raisethumb.html",
				controller: "ThumbController",
      		    resolve:{'UserServiceData':function(UserService){ return UserService.promise;}}									
			}).			
			when('/users/:userName', {
				templateUrl: "views/userprofile.html",
				controller: "UserProfilesController", 
      		    resolve:{'UserServiceData':function(UserService){ return UserService.promise;}}								
			}).
			otherwise({
				redirectTo: "/"
			});
	}]);
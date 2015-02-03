<!DOCTYPE html>
<html ng-app="thumb">
  <head>
        <meta charset="UTF-8">
        <title>The Thumb</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">        
        
        <script src="scripts/angular.min.js"></script>
        <script src="scripts/angular-route.min.js"></script>                                
        <script type="text/javascript">
            var principal = '${pageContext.request.userPrincipal.name}';
        </script>
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=false"></script>
        <script src="controllers/ngGPlaceAutocomplete.js"></script>                
        <script src="controllers/rides.js"></script>       
        <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
        <script src="scripts/ui-bootstrap-tpls-0.12.0.js"></script>                
    </head>
    <body>
      <div class="container">
        <nav class="navbar navbar-default" role="navigation">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">The Thumb</a>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#" title="Home">Home</a></li>
                    <li><a href="#rides/create" title="Drive">Drive</a></li>
                    <li><a href="#thumbs/raise" title="Raise a Thumb">Raise a Thumb</a></li>
                    <li><a href="#allrides" title="All rides">All rides</a></li> 
                    <li><a href="#allthumbs" title="All thumbs">All thumbs</a></li>
                    <li><a href="#notifications" title="Notifications">Notifications</a></li>
                    <li><a href="logout.jsp" title="Logout">Logout</a></li>                                                            
                </ul>
            </div>
        </nav>
                                                            
        <div ng-view></div>
                            
        <div class="message" ng-controller="AlertController">
            <alert ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">{{alert.msg}}</alert>
       </div>
       
      </div>                                            
    </body>
</html>
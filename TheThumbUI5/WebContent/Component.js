jQuery.sap.declare("org.thumb.Component");

sap.ui.core.UIComponent.extend("org.thumb.Component",{
	metadata : {
				
		config : {			
			serviceConfig : {
				name : "OwnRides",
				ownRideRequestsServiceUrl : "/TheThumb/rest/userprofile/" + principal + "/requests"
			}
		},		
		
		routing : {
			config : { 
				viewType:"HTML",
				viewPath:"thethumbui5",
				targetControl:"navContainer",
				targetAggregation:"pages",
				clearTarget : false
			},
			routes : [
			          {
			        	  pattern : "",
			        	  name:"Main",
			        	  view:"Main"
			          },
			          {
			        	  pattern : "UserProfile/{id}",
			        	  name : "UserProfile",
			        	  view : "UserProfile"
			          }]
		}
	}
});

org.thumb.Component.prototype.init = function(){
	jQuery.sap.require("sap.ui.core.routing.History");
	jQuery.sap.require("sap.m.routing.RouteMatchedHandler");
	
	sap.ui.core.UIComponent.prototype.init.apply(this);
	
	var mConfig = this.getMetadata().getConfig();
	var ownRideRequestsServiceUrl = mConfig.serviceConfig.ownRideRequestsServiceUrl;
	
	/* load the rides model */
    var oModel = new sap.ui.model.json.JSONModel();    
    $.ajax({  
              url : ownRideRequestsServiceUrl,  
              contentType : "application/json",  
              dataType : 'json',  
              success : function(data, textStatus, jqXHR) {  
                        oModel.setData(data);  
                        sap.ui.getCore().setModel(oModel);
              }, 
              error : function (data) {
            	  alert("Failed to load data from backend", data);
              }
    });
	
	var router = this.getRouter();
	this.routeHandler = new sap.m.routing.RouteMatchedHandler(router);
	router.initialize();
};

org.thumb.Component.prototype.destroy = function(){
	if(this.routeHandler){
		this.routeHandler.destroy();
	}
	sap.ui.core.UIComponent.destroy.apply(this,arguments);
};


org.thumb.Component.prototype.createContent = function(){
	this.view = sap.ui.view({id:"main",viewName:"thethumbui5.App",type:sap.ui.core.mvc.ViewType.JS});
	return this.view;
};

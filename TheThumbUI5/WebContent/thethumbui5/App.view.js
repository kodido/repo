sap.ui.jsview("thethumbui5.App", {

	/** Specifies the Controller belonging to this View. 
	* In the case that it is not implemented, or that "null" is returned, this View does not have a Controller.
	* @memberOf thethumbui5.Main
	*/ 
	getControllerName : function() {
		return "thethumbui5.App";
	},

	/** Is initially called once after the Controller has been instantiated. It is the place where the UI is constructed. 
	* Since the Controller is given to this method, its event handlers can be attached right away. 
	* @memberOf thethumbui5.Main
	*/ 
	createContent : function(oController) {
		this.setDisplayBlock(true);
 		return new sap.m.App("navContainer");
	}

});

sap.ui.jsview("hearit.main", {

	/** Specifies the Controller belonging to this View. 
	 * In the case that it is not implemented, or that "null" is returned, this View does not have a Controller.
	 * @memberOf hearit.main
	 */
	getControllerName : function() {
		return "hearit.main";
	},

	/** Is initially called once after the Controller has been instantiated. It is the place where the UI is constructed. 
	 * Since the Controller is given to this method, its event handlers can be attached right away. 
	 * @memberOf hearit.main
	 */
	createContent : function(oController) {
		var aControls = [];
		var oButton = new sap.m.Button({
			id : this.createId("btn"),
			text : "Melodic Intervals"
		});
		oButton.attachPress(oController.doIt)
		aControls.push(oButton);
		
		
		return new sap.m.Page({
		title: "Hear It",
		content: [
		    oButton
		]
		});		
	}

});
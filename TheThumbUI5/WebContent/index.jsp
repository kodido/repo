<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv='Content-Type' content='text/html;charset=UTF-8'/>
		
		<!-- src="https://openui5.hana.ondemand.com/resources/sap-ui-core.js" -->
		<script id="sap-ui-bootstrap" 		         	
		        src="resources/sap-ui-core.js"		
				data-sap-ui-libs="sap.m"
				data-sap-ui-theme="sap_bluecrystal"
				data-sap-ui-resourceroots='{
					"org.thumb":"./",
					"thethumbui5":"./thethumbui5"}'>
		</script>
		
        <script type="text/javascript">
            var principal = '${pageContext.request.userPrincipal.name}';
        </script>
        		
		<!-- only load the mobile lib "sap.m" and the "sap_bluecrystal" theme -->

		<script>
				new sap.m.Shell("Shell",{
					title:"The Thumb",
					app: new sap.ui.core.ComponentContainer({
						name : 'org.thumb'
					})
				}).placeAt("content");
		</script>

	</head>
	<body class="sapUiBody" role="application">
		<div id="content"></div>
	</body>
</html>
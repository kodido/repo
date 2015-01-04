<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv='Content-Type' content='text/html;charset=UTF-8'/>

		<script src="resources/sap-ui-core.js"
				id="sap-ui-bootstrap"
				data-sap-ui-libs="sap.m"
				data-sap-ui-theme="sap_bluecrystal">
		</script>
		
        <script type="text/javascript">
            var principal = '${pageContext.request.userPrincipal.name}';
        </script>
        		
		<!-- only load the mobile lib "sap.m" and the "sap_bluecrystal" theme -->

		<script>
				sap.ui.localResources("thethumbui5");
				var app = new sap.m.App({initialPage:"idApp1"});
				var page = sap.ui.view({id:"idApp1", viewName:"thethumbui5.App", type:sap.ui.core.mvc.ViewType.XML});
				app.addPage(page);
				app.placeAt("content");
		</script>

	</head>
	<body class="sapUiBody" role="application">
		<div id="content"></div>
	</body>
</html>
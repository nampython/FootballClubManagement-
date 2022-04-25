<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!DOCTYPE HTML>
	<html>

	<head>
		<meta charset="UTF-8" />
		<title>Welcome</title>
		<link rel="stylesheet" href="//code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.15.5/css/ui.jqgrid.min.css">
		<link href="css/player.css" rel="stylesheet">
		<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.15.5/jquery.jqgrid.min.js"></script>
		<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
		<script	src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
	</head>

	<body>

		<video playsinline autoplay muted loop>
			<source
				src="./images/cristiano.mp4">
		</video>
		
		<header class="noi_dung">
			<h1 align="center"  style="background-color:tomato">
				Clubs Management
			</h1>
			<div align="center"> 
				<table id="clubTable" class="center"></table>
			</div>
			<div align="center">
				<div class="jqgrid-button-div">
					<button id="edit-button" class="jqgrid-button">Edit</button>
					<button id="add-button" class="jqgrid-button">Add</button>
					<button id="delete-button" class="jqgrid-button">Delete</button>
					<button id="refresh-button" class="jqgrid-button">Refresh</button>
					<button id="home-button" class="jqgrid-button">Home</button>
				</div>
			</div>
		</header>

		<script src="${request.getContextPath()}/js/club.js"></script>
		<script src="${request.getContextPath()}/js/funcs.js"></script>
		<script src="${request.getContextPath()}/js/properties.js"></script>
	</body>

	</html>
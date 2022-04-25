<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<meta http-equiv="X-UA-Compatible" content="IE=edge">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>Home page</title>
			<link rel="stylesheet" href="<c:url value = "
				https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" />">
			<link href="css/home.css" rel="stylesheet">
			<script src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
			<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
			<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

		</head>


		<body>
			
			<video playsinline autoplay muted loop>
   				<source src="https://media.istockphoto.com/videos/football-player-people-silhouette-redloop-video-id965011602">
 			</video>
 			<header class="noi_dung">
			 	<section class="">
					<h1 class="display-1 text-center text-primary">Welcome To Our Football Management System</h1>
					<div align="center">
						<button id="player-manage" class="btn-dark">Player Manage</button>
						<button id="club-manage" class="btn-dark">Club Manage</button>
					</div>
				</section>
 			</header>




			
			<script type="text/javascript" src="js/home.js"></script>
			<script type="text/javascript" src="js/properties.js"></script>

		</body>

		</html>
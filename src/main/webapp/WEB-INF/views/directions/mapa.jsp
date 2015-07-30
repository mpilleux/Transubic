<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
<title>Dirección</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet"
	href="<c:url value='/webjars/bootstrap/3.3.2-1/css/bootstrap.min.css'/>">
<script
	src="https://maps.googleapis.com/maps/api/js?sensor=true&libraries=places,geometry&language=es&region=CL"></script>
<style>
html, body, #map-canvas {
	margin: 0;
	padding: 0;
	height: 100%;
	width: 100%;
}
</style>
<script>
	var directionsDisplay = new google.maps.DirectionsRenderer();
	var directionsService = new google.maps.DirectionsService();
	var map;

	var region = "${region}";
	var language = "${language}";
	var origin = new google.maps.LatLng(${origin});
	var destination = "${destination}";
	var arrivalTime = new Date(${arrivalTime});
	var travelMode = ${travelMode};
	arrivalTime.setHours(arrivalTime.getHours() - 1);//por alguna razón las rutas vienen con 1 hora de atraso
	arrivalTime.setMinutes(arrivalTime.getMinutes() - 5);//para que llegue 5 min antes
	
	function initialize() {
		var santiago = new google.maps.LatLng(-33.43783, -70.65045);
		var mapOptions = {
			zoom : 12,
			center : santiago
		}
		map = new google.maps.Map(document.getElementById("map-canvas"),
				mapOptions);
		directionsDisplay.setMap(map);
		calcRoute();
	}

	var request = {
		origin: origin,
		destination: destination,
		travelMode: travelMode,
		provideRouteAlternatives: false,
		region: region,
		transitOptions: {
			 arrivalTime: arrivalTime
		}
		
	};

	function calcRoute() {

		directionsService.route(request, function(response, status) {
		    if (status == google.maps.DirectionsStatus.OK) {
		      directionsDisplay.setDirections(response);
		    }
		  });
		
	}
	google.maps.event.addDomListener(window, "load", initialize);
	
</script>
</head>
<body>

	<div id="map-canvas"></div>

	<script src="<c:url value='/webjars/jquery/2.1.3/jquery.min.js'/>"></script>
	<script
		src="<c:url value='/webjars/bootstrap/3.3.2-1/js/bootstrap.min.js'/>"></script>
</body>
</html>
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
	var map;
	var response = ${response};
	var origin = new google.maps.LatLng(${origin});
	var destination = "${destination}";
	var travelMode = google.maps.DirectionsTravelMode.TRANSIT;
	
	function initialize() {
		var santiago = new google.maps.LatLng(-33.43783, -70.65045);
		var mapOptions = {
			zoom : 12,
			center : santiago
		}
		map = new google.maps.Map(document.getElementById("map-canvas"),
				mapOptions);
		directionsDisplay.setMap(map);
		renderDirections(map, response, request)
	}

	var request = {
		origin: origin,
		destination: destination,
		travelMode: google.maps.DirectionsTravelMode.TRANSIT,
		provideRouteAlternatives: false,
		unitSystem: 0
	};

	function typecastRoutes(routes){
	    routes.forEach(function(route){
	        route.bounds = asBounds(route.bounds);
	        // I don't think `overview_path` is used but it exists on the
	        // response of DirectionsService.route()
	        route.overview_polyline = route.overviewPolyline;
	        route.waypoint_order = route.waypointOrder;
	        route.overview_path = asPath(route.overviewPolyline);

	        route.legs.forEach(function(leg){
	        	leg.arrival_time = leg.arrivalTime;
	        	leg.departure_time = leg.departureTime;
	        	leg.duration_in_traffic = leg.durationInTraffic;
	        	leg.end_address = leg.endAddress;
	        	leg.start_address = leg.startAddress;	        	
	        	
	            leg.start_location = asLatLng(leg.startLocation);
	            leg.end_location   = asLatLng(leg.endLocation);
	            

	            leg.steps.forEach(function(step){
	                step.start_location = asLatLng(step.startLocation);
	                step.end_location   = asLatLng(step.endLocation);
	                step.path = asPath(step.polyline);
	                step.travel_mode = step.travelMode;
	            });

	        });
	    });
	    return routes;
	}

	function asBounds(boundsObject){
	    return new google.maps.LatLngBounds(asLatLng(boundsObject.southwest),
	                                    asLatLng(boundsObject.northeast));
	}

	function asLatLng(latLngObject){
	    return new google.maps.LatLng(latLngObject.lat, latLngObject.lng);
	}

	function asPath(encodedPolyObject){
	    return google.maps.geometry.encoding.decodePath( encodedPolyObject.encodedPath );
	}

	function renderDirections(map, response, request){
		directionsDisplay.setOptions({
	        directions : {
	            routes : response,
	            // "ub" is important and not returned by web service it's an
	            // object containing "origin", "destination" and "travelMode"
	            request : request,
	            status : "OK"
	        },
	        draggable : true,
	        map : map
	    });
		
	}
	response = typecastRoutes(response);
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
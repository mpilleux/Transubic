<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
	<title>Error Page</title>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="<c:url value='/webjars/bootstrap/3.3.2-1/css/bootstrap.min.css'/>">
	<link rel="stylesheet" href="<c:url value='/resources/css/403.css'/>">
	<style>
	body {
		background: url( '<c:url value="/resources/img/galaxy.png"/>' ) no-repeat center center fixed;
	}
	</style>
</head>
<body>
	<div class="site-wrapper">
		<div class="site-wrapper-inner">
			<div class="cover-container">
				<h1 class="cover-heading">Whoops!</h1>
				<h2>Maybe, we went too too far... &nbsp;&nbsp;<a class="btn btn-bonus btn-lg" href="<c:url value="/"/>">back to home</a></h2>				
			</div>
		</div>
	</div>
	<script src="<c:url value='/webjars/jquery/1.11.2/jquery.min.js'/>"></script>
	<script src="<c:url value='/webjars/bootstrap/3.3.2-1/js/bootstrap.min.js'/>"></script>
</body>
</html>
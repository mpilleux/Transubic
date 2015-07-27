<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
	<title>Login Page</title>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="<c:url value='/webjars/bootstrap/3.3.2-1/css/bootstrap.min.css'/>">
	<link rel="stylesheet" href="<c:url value='/resources/css/login.css'/>">
	
	<style>
	body {
		background: url( '<c:url value="/resources/img/cosmos.jpg"/>' ) no-repeat center center fixed;
	}
	</style>
</head>
<body>
	<div class="site-wrapper">
		<div class="site-wrapper-inner">
			<div class="cover-container">
				<div class="inner cover">
					<h1 class="cover-heading">Transubic</h1>
					<form class="form-signin" name='loginForm' action="<c:url value='/login' />" method='POST'>
						<input class="form-control" type='text' name='rut' placeholder="RUT or ID" onChange="this.value=$.Rut.formatear(this.value.substring(0, this.value.length - 1))+'-'+this.value[this.value.length-1]; "/>
						<input class="form-control" type='password' name='password' placeholder="Password"/> 
						<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button> 
						<a href="<c:url value='user/formCrearUsuario' />">Crear usuario</a>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
					<c:if test="${not empty error}">
						<div class="alert alert-danger alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<strong>Error! </strong>${error}
						</div>
					</c:if>
					<c:if test="${not empty formSuccess}">
						<div class="alert alert-success alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							${formSuccess}
						</div>
					</c:if>
					<c:if test="${not empty msg}">
						<div class="alert alert-info alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<strong>Heads up! </strong>${msg}
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<script src="<c:url value='/webjars/jquery/2.1.3/jquery.min.js'/>"></script>
	<script src="<c:url value='/resources/js/jquery.Rut.min.js'/>"></script>
	<script src="<c:url value='/webjars/bootstrap/3.3.2-1/js/bootstrap.min.js'/>"></script>
</body>
</html>
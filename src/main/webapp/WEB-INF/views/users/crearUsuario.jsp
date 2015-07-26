<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<style>
a {
	color: #354052;
}

a:hover {
	color: #15a4fa;
	text-decoration: none;
}

h4 {
	font-weight: 600;
}
</style>
		<script src="<c:url value='/resources/js/jquery.Rut.min.js'/>"></script>

		<h3>Crear usuario</h3>

		<c:url var="addAction" value="/user/crearUsuario"></c:url>
		<form:form class="col-xs-3" action="${addAction}" method='POST'
			commandName="user">

			<div class="form-group">
				<label>Rut</label>
				<form:input path="rut" class="form-control"
					onChange="this.value=$.Rut.formatear(this.value.substring(0, this.value.length - 1))+'-'+this.value[this.value.length-1]; " />
				<form:errors path="rut" />
			</div>
			<div class="form-group">
				<label>Nombre</label>
				<form:input path="name" class="form-control"/>
				<form:errors path="name" />
			</div>
			<div class="form-group">
				<label>Password</label>
				<form:password path="password" class="form-control" />
				<form:errors path="password" />
			</div>
			<div class="form-group">
				<label>Email</label>
				<form:input path="email" class="form-control" />
				<form:errors path="email" />
			</div>

			<input class="btn btn-primary" type="submit" value="Crear" />

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form:form>
		
		<c:url var="getKey" value="/getKey"></c:url>
		<form:form class="col-xs-3" action="${getKey}" method='POST'>
			<input class="form-control" type='text' name='rut' placeholder="RUT or ID" onChange="this.value=$.Rut.formatear(this.value.substring(0, this.value.length - 1))+'-'+this.value[this.value.length-1]; "/>
			<input class="form-control" type='password' name='password' placeholder="Password"/> 
			<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button> 
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form:form>

	</tiles:putAttribute>
</tiles:insertDefinition>

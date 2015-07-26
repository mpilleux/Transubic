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

		<h3>Eliminar evento de calendario</h3>

		<c:url var="deleteAction"
			value="/calendar/eliminarEvento"></c:url>
		<form:form action="${deleteAction}" method='POST' commandName="modificarEventoForm">
			<div class="form-group">
				<label>¿Estás seguro que deseas eliminar este evento del calendario?</label>
				<br /> 
				<label class="radio-inline"> 
					<input type="radio" name="confirm" value="0" checked>No
				</label> 
				<label class="radio-inline"> 
					<input type="radio" name="confirm" value="1">Si
				</label>
				<br /> 
				<form:errors path="*" />
			</div>
			
			<br />
			<br />
			<form:hidden path="id"/>
			<input class="btn btn-danger" type="submit" value="Eliminar" />
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form:form>

	</tiles:putAttribute>
</tiles:insertDefinition>

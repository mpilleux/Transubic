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

		<h3>Evento de calendario</h3>

		<c:url var="addAction" value="/calendar/${action}"></c:url>
		<form:form class="col-xs-3" action="${addAction}" method='POST'
			commandName="calendarEvent">

			<label>Fecha: ${calendarEvent.eventDateFormatted}</label>

			<div class="form-group">
				<label>Título</label>
				<form:input path="title" class="form-control"/>
				<form:errors path="title" />
			</div>
			<div class="form-group">
				<label>Hora de inicio (24h)</label>
				<form:input path="eventTimeString" type="time" class="form-control" />
				<form:errors path="eventTimeString" />
			</div>
			<div class="form-group">
				<label>Hubicación</label>
				<form:input path="location" class="form-control"/>
				<form:errors path="location" />
			</div>
			<input class="btn btn-primary" type="submit" value="Guardar" />
			
			<form:hidden path="eventId"/>
			<form:hidden path="eventDateString"/>
			<form:hidden path="userId"/>
			
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form:form>

	</tiles:putAttribute>
</tiles:insertDefinition>

<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page trimDirectiveWhitespaces="true"%>

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

img.profile {
	float: left;
	display: none;
	width: 100px;
	height: 100px;
	margin: 18px 18px 40px 18px;
}

h4 {
	font-weight: 600;
}

#calendar {
	max-width: 900px;
	margin: 0 auto;
}
</style>
		<script src="<c:url value='/resources/js/calendario.js'/>"></script>
		<c:url var="addEventoAction" value="/calendar/formAgregarEvento"></c:url>
		<c:url var="editEventoAction" value="/calendar/formEditarEvento"></c:url>
		<c:url var="deleteEventoAction" value="/calendar/formEliminarEvento"></c:url>
		<form:form id="form-addEvento" action="${addEventoAction}"
			method='POST'>
			<input type="hidden" name="date" value="" />
		</form:form>
		<form:form id="form-editEvento" action="${editEventoAction}"
			method='POST'>
			<input type="hidden" name="id" value="" />
		</form:form>
		<form:form id="form-deleteEvento" action="${deleteEventoAction}"
			method='POST'>
			<input type="hidden" name="id" value="" />
		</form:form>

		<div id="popover-content-add" class="hide">
			<form>
				<div class='btn-group'>
					<input class='btn btn-xs btn-default' form='form-addEvento'
						value='Agregar evento' type='submit' onClick="setAddEventoForm(this);"/>
				</div>
			</form>
		</div>
		<div id="popover-content-edit" class="hide">
			<form>
				<div class='btn-group'>
					<input class='btn btn-xs btn-default' form='form-editEvento'
						value='Editar evento' type='submit' onClick="setEditEventoForm(this);"/>
					<input class='btn btn-xs btn-default' form='form-deleteEvento'
						value='Eliminar evento' type='submit' onClick="setDeleteEventoForm(this);"/>
				</div>
			</form>
		</div>
		
		
		<script>
			$(document).ready(function() {

				$('#calendar').fullCalendar({
					header : {
						left : 'prev,next today',
						center : 'title',
						right : 'month,agendaWeek,agendaDay'
					},
					defaultDate : '${defaultDate}',
					selectable : true,
					selectHelper : true,
					dayClick : function(start, end, allDay, jsEvent, view) {
						$(this).popover({

							html : true,
							placement : 'bottom',
							content : function() {
								$("#popover-content-add input").attr("date", "'"+start.format()+"'");
								return $("#popover-content-add").html();
							},
							html: true,
			                container: 'body'
						});
						$('.popover').hide();
						$(this).popover('toggle');

					},
					eventClick: function(calEvent, jsEvent, view) {
						$(this).popover({

							html : true,
							placement : 'bottom',
							content : function() {
								$("#popover-content-edit input").attr("id", calEvent.id);
								return $("#popover-content-edit").html();
							},
							html: true,
			                container: 'body'
						});
						$('.popover').hide();
						$(this).popover('toggle');
						
				    },
				    viewRender: function(view, element) { $('.popover').hide(); },
					editable : true,
					eventLimit : true, // allow "more" link when too many events
					events : ${events}
				});

			});
		</script>
		<div id='calendar'></div>

	</tiles:putAttribute>
</tiles:insertDefinition>

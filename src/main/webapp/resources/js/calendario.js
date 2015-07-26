function setDateForm(formName, date) {
	$('#form-' + formName + ' input[name="date"]')[0].value = date;
}
function setAddEventoForm(field) {
	var date = $(field).attr('date');
	setDateForm('addEvento', date);
}

function setIdForm(formName, id) {
	$('#form-' + formName + ' input[name="id"]')[0].value = id;
}
function setEditEventoForm(field) {
	var id = $(field).attr('id');
	setIdForm('editEvento', id);
}
function setDeleteEventoForm(field) {
	var id = $(field).attr('id');
	setIdForm('deleteEvento', id);
}
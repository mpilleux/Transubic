package cl.uchile.transubic.calendarEvent.form;

import javax.validation.constraints.AssertTrue;

public class ModificarEventoForm {

	private Integer id;
	private Integer confirm;

	public ModificarEventoForm() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getConfirm() {
		return confirm;
	}

	public void setConfirm(Integer confirm) {
		this.confirm = confirm;
	}
	
	@AssertTrue(message = "Debe seleccionar 'Si' para eliminar este evento del calendario.")
	public boolean isValidLeaveDates() {
		return this.getConfirm() == 1;
	}

}

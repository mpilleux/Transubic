package cl.uchile.transubic.calendarEvent.form;

public class AgregarEventoForm {

	private String date;

	public AgregarEventoForm() {
		super();
	}

	public String getDate() {
		if (date == null)
			return null;

		return date.replace("'", "");
	}

	public void setDate(String date) {
		this.date = date;
	}

}

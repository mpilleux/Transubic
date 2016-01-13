package cl.uchile.transubic.claseEjemplo.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tabla_ejemplo")
public class ClaseEjemplo {

	
	private Integer id;
	private String nombre;
	private String apellido;
	private Date fecha;

	
	public ClaseEjemplo() {
		
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "T_EJ_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "T_EJE_NOMBRE", nullable = false, length = 128)
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "T_EJE_APELLIDO", nullable = false, length = 128)
	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "T_EJE_FECHA", nullable = false)
	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	
	
}

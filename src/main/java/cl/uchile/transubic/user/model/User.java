package cl.uchile.transubic.user.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "user")
public class User {

	private Integer userId;
	private Date creationDate;
	private String rut;
	private String name;
	private String password;
	private String email;
	private boolean enabled;

	public User() {
	}

	public User(Integer userId, Date creationDate, String rut,
			String name, String password, String email, boolean enabled) {
		super();
		this.userId = userId;
		this.creationDate = creationDate;
		this.rut = rut;
		this.name = name;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USE_ID", unique = true, nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "USE_CREATE_DATE", nullable = false)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@NotNull
	@Length(min = 12, max = 13)
	@Column(name = "USE_RUT", unique = true, nullable = false, length = 12)
	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	@NotNull
	@Length(min = 1, max = 128)
	@Column(name = "USE_NAME", nullable = false, length = 128)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "USE_PASSWORD", nullable = false, length = 256)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "USE_EMAIL", nullable = false, length = 256, unique=true)
	@Email
	@NotNull
	@Length(min = 4, max = 256)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "USE_STATUS", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@AssertTrue(message = "Invalid verifying digit in Rut.")
	@Transient
	public boolean isValidRut() {
		boolean validacion = false;
		String rut = this.getRut();

		rut = rut.toUpperCase();
		rut = rut.replace(".", "");
		rut = rut.replace("-", "");

		if (rut.length() < 1) {
			return validacion;
		}

		int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

		char dv = rut.charAt(rut.length() - 1);

		int m = 0, s = 1;
		for (; rutAux != 0; rutAux /= 10) {
			s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
		}
		if (dv == (char) (s != 0 ? s + 47 : 75)) {
			validacion = true;
		}

		return validacion;
	}
	
	public void encryptPassword() {
		PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		this.setPassword(bCryptPasswordEncoder.encode(this.getPassword()));
	}

}

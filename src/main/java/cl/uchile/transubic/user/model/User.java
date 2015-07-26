package cl.uchile.transubic.user.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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

	private String digit;
	private String key;
	private Date keyDate;

	public User() {
	}

	public User(Integer userId, Date creationDate, String rut, String name,
			String password, String email, boolean enabled) {
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

	@Column(name = "USE_EMAIL", nullable = false, length = 256, unique = true)
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "USE_KEY_DATE", nullable = false)
	public Date getKeyDate() {
		return keyDate;
	}

	public void setKeyDate(Date keyDate) {
		this.keyDate = keyDate;
	}
	
	@Column(name = "USE_DIGIT", nullable = false)
	public String getDigit() {
		return digit;
	}

	public void setDigit(String digit) {
		this.digit = digit;
	}

	@Column(name = "USE_KEY", nullable = false)
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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


	@Transient
	private String getStringToEncrypt() {
		return this.getKeyDate().toString() + "" + this.getUserId();
	}

	@Transient
	public String generateCode() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String hashedPassword = passwordEncoder.encode(this
				.getStringToEncrypt());

		String c = this.getDigit();
		String textToInsert = this.getUserId() + c + "";
		hashedPassword = hashedPassword.substring(0, 33) + textToInsert
				+ hashedPassword.substring(33);

		hashedPassword = encodeHashUrl(hashedPassword);

		return hashedPassword;
	}

	@Transient
	public void generateKey() {
		this.setKey(User.encodeHashUrl(this.generateCode()));
	}

	@Transient
	public void generateDigit() {
		Random r = new Random();
		char c = (char) (r.nextInt(26) + 'a');
		this.setDigit(c + "");
	}

	@Transient
	public boolean isValidHash(String hash) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(this.getKeyDate());
		cal.add(Calendar.DAY_OF_YEAR, 1);

		if (cal.getTime().compareTo(new Date()) < 0)
			return false;

		String subString = hash.substring(33);
		String integers = "123456789";

		int i;
		for (i = 0; i < subString.length(); i++) {
			char c = subString.charAt(i);
			if (!integers.contains(c + "")) {
				i++;
				break;
			}
		}
		hash = hash.substring(0, 33) + subString.substring(i);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		return passwordEncoder.matches(this.getStringToEncrypt(), hash);
	}

	@Transient
	public static String encodeHashUrl(String hash) {
		hash = hash.replace('/', '-');
		hash = hash.replace('.', '_');
		hash = hash.replace("$2a$10$", "");
		return hash;
	}

	@Transient
	public static String decodeHashUrl(String hash) {
		hash = "$2a$10$" + hash;
		hash = hash.replace('-', '/');
		hash = hash.replace('_', '.');
		return hash;
	}

}

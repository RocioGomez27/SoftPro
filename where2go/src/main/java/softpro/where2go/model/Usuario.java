package softpro.where2go.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario{
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer UUID;
	private String email;
	
	private String username;
	private String password;
	
	private String Nombre;
	private String Apellido;
	private int Edad;
	private String Avatar;
	
	private Boolean esAdmin;
	private Boolean esModerador;
	
	public Usuario() {

	}
	
	public Integer getUUID() {
		return UUID;
	}


	public void setUUID(Integer UUID) {
		this.UUID = UUID;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getNombre() {
		return Nombre;
	}



	public void setNombre(String nombre) {
		Nombre = nombre;
	}



	public String getApellido() {
		return Apellido;
	}



	public void setApellido(String apellido) {
		Apellido = apellido;
	}



	public int getEdad() {
		return Edad;
	}



	public void setEdad(int edad) {
		Edad = edad;
	}



	public int hashCode(Object o) {
		return (int) (((Usuario) o).UUID * 8); // UID is unique.
	}
	
	public boolean compareTo(Object o) {
		return (o instanceof Usuario) && hashCode() == o.hashCode();
	}



	public Boolean getEsModerador() {
		return esModerador;
	}



	public void setEsModerador(Boolean esModerador) {
		this.esModerador = esModerador;
	}

	public Boolean getEsAdmin() {
		return esAdmin;
	}

	public void setEsAdmin(Boolean esAdmin) {
		this.esAdmin = esAdmin;
	}

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		Avatar = avatar;
	}
	
	
}

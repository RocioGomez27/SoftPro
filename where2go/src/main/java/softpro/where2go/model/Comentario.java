package softpro.where2go.model;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


@Entity
public class Comentario {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer CUID;
	private Integer valoracion;
	private String texto_Comentario;
	private String usuario;
	@ManyToOne
	private Actividad actividad;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaComentario;
	
	
	@PrePersist
	private void onCreate() {
		fechaComentario = new Date();
	}
		
	
	public Integer getCUID() {
		return CUID;
	}
	public void setCUID(Integer CUID) {
		this.CUID = CUID;
	}
	public String getTexto_Comentario() {
		return texto_Comentario;
	}
	
	public void setTexto_Comentario(String texto_Comentario) {
		this.texto_Comentario = texto_Comentario;
	}

	public Date getFechaComentario() {
		return fechaComentario;
	}
	public void setFechaComentario(Date fechaComentario) {
		this.fechaComentario = fechaComentario;
	}
	public Actividad getActividad() {
		return actividad;
	}
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Integer getValoracion() {
		return valoracion;
	}
	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}
	
	
}

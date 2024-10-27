package softpro.where2go.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Actividad {
	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer AUID;
	private String Nombre = null;
	private String Descripcion = null;
	private Integer    Valoraciones = 0;
	private double Latitud;
	private double Longitud;
	private String ImageUrl = null;
	
	private Integer precio = null;
	
	private Boolean ocio = false;
	private Boolean tienda = false;
	private Boolean restaurante = false;
	private Boolean cultura = false;
	private Boolean transporte = false;
	private Boolean naturaleza = false;


	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaCreacion;
	
	
	@OneToMany (mappedBy = "actividad", cascade= {CascadeType.REMOVE})
	List<Comentario> comentarios;
	
	@PrePersist
	private void onCreate() {
		fechaCreacion = new Date();
	}
		
	public Actividad(){
		
	}
	
	
	public void setAUID(Integer auid) {	
		this.AUID = auid;
	}
	
	public Integer getAUID() {
		return AUID;
	}

	public String getNombre() {
		return Nombre;
	}
	
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	
	public String getDescripcion() {
		return Descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	
	public Integer getValoraciones() {
		return Valoraciones;
	}
	
	public void setValoraciones(Integer valoraciones) {
		Valoraciones = valoraciones;
	}


	public double getLatitud() {
		return Latitud;
	}

	public void setLatitud(double latitud) {
		Latitud = latitud;
	}

	public double getLongitud() {
		return Longitud;
	}

	public void setLongitud(double longitud) {
		Longitud = longitud;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String ImageUrl) {
		this.ImageUrl = ImageUrl;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	public void addComentario(Comentario c) {
		this.comentarios.add(c);
		//media(c.getValoracion());
	}
	
	public void removeComentario(Comentario c) {
		if(!this.comentarios.isEmpty()) {
			this.comentarios.remove(c);
		}
	}

	public Boolean getOcio() {
		return ocio;
	}

	public void setOcio(Boolean ocio) {
		this.ocio = ocio;
	}

	public Boolean getNaturaleza() {
		return naturaleza;
	}

	public void setNaturaleza(Boolean naturaleza) {
		this.naturaleza = naturaleza;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
	
	
	public List<String> obtenerFiltros(){ // Método para obtener una lista con las filtros de la actividad a partir de los atributos booleanos.
		List<String> f = new ArrayList<>();
		if(ocio != null && ocio) f.add("Ocio");
		if(tienda != null && tienda) f.add("Tienda");
		if(restaurante != null && restaurante) f.add("Restaurante");
		if(cultura != null && cultura) f.add("Cultura");
		if(transporte != null && transporte) f.add("Transporte");
		if(naturaleza != null && naturaleza) f.add("Naturaleza");
		
		return f;
	}
	
	public Boolean getTienda() {
		return tienda;
	}

	public void setTienda(Boolean tienda) {
		this.tienda = tienda;
	}

	public Boolean getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Boolean restaurante) {
		this.restaurante = restaurante;
	}

	public Boolean getCultura() {
		return cultura;
	}

	public void setCultura(Boolean cultura) {
		this.cultura = cultura;
	}

	public Boolean getTransporte() {
		return transporte;
	}

	public void setTransporte(Boolean transporte) {
		this.transporte = transporte;
	}
	
}

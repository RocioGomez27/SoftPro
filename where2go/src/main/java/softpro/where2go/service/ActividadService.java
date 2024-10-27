package softpro.where2go.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softpro.where2go.model.Actividad;
import softpro.where2go.repository.ActividadRespository;

@Service
public class ActividadService{
	
	@Autowired
	ActividadRespository actividadRepository;
	
	public List<Actividad> obtenerTodos(){
		return actividadRepository.findAll();
	}
	
	public void save(Actividad a) {
		actividadRepository.saveAndFlush(a);
	}
	
	public void eliminarActividad(Integer id) {
		actividadRepository.deleteById(id);
	}

	public Actividad obtenerPorId(Integer auid) {
		return actividadRepository.getById(auid);
	}
	
	public List<Actividad> buscarActividad(String query){
		List<Actividad> TodasActividades = obtenerTodos();
		List<Actividad> actividadesBusqueda = new ArrayList<>();
		for (Actividad actividad : TodasActividades) {
			if(actividad.getNombre().toLowerCase().replaceAll(" ", "").contains(query.toLowerCase().replaceAll(" ", ""))) {
				actividadesBusqueda.add(actividad);
			}
		}
		
		return actividadesBusqueda;		
	}
	
	//Nos pasan los filtros en un String que los contiene separados por el caracter '&'
	public List<Actividad> filtrar(String filter, Integer pies, Integer precio){
		List<Actividad> actividades = new ArrayList<>();	//Lista de actividades que cumplen todos los filtros
		
		List<String> filtros = new ArrayList<>();	//Lista de Strings en la que cada elemento es un filtro que se exige				
		if(!filter.isEmpty()) {
			String[] f = filter.split("&");
			for(String filtro : f) {
				filtros.add(filtro);
			}
			//Hemos creado la lista de Strings de los filtros que hay que cumplir (filtros = lista de Strings en la que cada elemento es un filtro que se debe cumplir)
			
			for(Actividad a : obtenerTodos()) {		//Hacemos un for each en el que comprobamos si cada actividad cumple todos los filtros o no
				ArrayList<String> filtrosActividad = (ArrayList<String>) a.obtenerFiltros();
				if(filtrosActividad.size() >= filtros.size() && filtrosActividad.containsAll(filtros)) {	//Si la actividad cumple todos, se añade a la lista actividades, la cual contendra todas las actividades que cumplen todos los filtros que se exigen
					actividades.add(a);						
				}
			}
			
			actividades = filtrarPies(actividades,pies);
			actividades = filtrarPrecio(actividades,precio);
			
		}else {
			actividades = obtenerTodos();
			
			actividades = filtrarPies(actividades,pies);
			actividades = filtrarPrecio(actividades,precio);
			
		}
		
		return actividades;
	}

	private List<Actividad> filtrarPrecio(List<Actividad> actividades, int precio) {
		List<Actividad> acts;	//Lista de actividades que cumplen todos los filtros
		if(precio != 0) {
			acts = new ArrayList<>();
			for(Actividad a : actividades) {
				if(a.getPrecio() != null && a.getPrecio() <= precio) {
					acts.add(a);
				}
			}
		} else {
			acts = actividades;
		}
		return acts;
	}

	private List<Actividad> filtrarPies(List<Actividad> actividades, int pies) {
		List<Actividad> acts;	//Lista de actividades que cumplen todos los filtros
		if(pies != 0) {
			acts = new ArrayList<>();
			for(Actividad a : actividades) {
				if(a.getValoraciones() != null && a.getValoraciones() >= pies) {
					acts.add(a);
				}
			}
		} else {
			acts = actividades;
		}
		return acts;
		
	}
	
}

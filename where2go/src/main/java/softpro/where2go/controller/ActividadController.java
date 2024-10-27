package softpro.where2go.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import softpro.where2go.model.Actividad;
import softpro.where2go.service.ActividadService;

@Controller
public class ActividadController {

	@Autowired
	ActividadService actividadService;
	
	private static String[] quitarEspacios(String[] linea) {
		String[] res = new String[linea.length];
		int j = 0;
		for(int i = 0;i < linea.length;i++) {
			if(!linea[i].equals("")) {
				res[j] = linea[i];
				j++;
			}
		}
		return res;
	}
	
	private static List<Actividad> obtenerAPI() throws FileNotFoundException{
		List<Actividad> acts = new ArrayList<>();
		
		
		Scanner sc = new Scanner(new File("datosJSON.txt"));
		boolean nuevo = true;
		int idx = 0;
		while(idx < 270) {
		//while(sc.hasNextLine()) {
			String[] linea;
			String text = null;
			linea = sc.nextLine().split(" ");
			linea = quitarEspacios(linea);
			String[] aux1;
			String[] aux2;
			String aux;
			String atributo = linea[0].substring(1);
			Actividad act = new Actividad();
			while(!atributo.startsWith("vicinity")) {	//Es un lugar nuevo
				if(atributo.startsWith("location")) {	
					//Lee la latitud y longitud
					//System.out.println("----------------NUEVO LUGAR------------");
					aux1 = sc.nextLine().split(" "); //aux1 = {""lat"", ":", "36.71716,"}
					aux1 = quitarEspacios(aux1);
					aux2 = aux1[2].split(",");		//aux2 = {"36.71716",""}
					double lat = Double.parseDouble(aux2[0]);	
					aux1 = sc.nextLine().split(" "); //aux1 = {""lng"", ":", "-4.46298,"}
					aux1 = quitarEspacios(aux1);
					aux2 = aux1[2].split(",");		//aux2 = {"-4.46298",""}
					double longit = Double.parseDouble(aux2[0]);
					//System.out.println(lat);
					//System.out.println(longit);
					act.setLatitud(lat);
					act.setLongitud(longit);
					
				} else if(atributo.startsWith("name")) {
					//---------------------------LEO EL NOMBRE---------------------------
					StringBuilder sb = new StringBuilder();		//linea = {""name"",":",""Taberna","Matahambre","Teatinos"",null,null,...,null}
					String name;
					int i = 2;
					sb.append(linea[i].substring(1)); 	//linea[2] = "Taberna | linea[2].substring(1) = Taberna
					i++;
					while(linea[i+1] != (null)) {		//Si el nombre es solo una palabra no entra en el bucle
						sb.append(" " + linea[i]);
						i++;
					}
					if(linea[i] != null) {		//Falta por añadir la ultima palabra del nombre, la añado fuera del bucle pq lleva comillas al final
						sb.append(" " + linea[i].substring(0, linea[i].length()-2));
					}
					name = sb.toString();
					//System.out.println("El nombre es: " + name);
					
					if(name.endsWith("\",")) {
						name = name.substring(0,name.length()-2);
					}
					
					act.setNombre(name);
				} else if(atributo.startsWith("photo_reference")) {
					//---------------------------LEO LA IMAGEURL---------------------------
					act.setImageUrl(linea[2].substring(1, linea[2].length()-2));
					//System.out.println(act.getImageUrl());
				} else if(atributo.startsWith("price_level")) {
					//Lee el precio (si hay) 
					linea[0] = linea[0].substring(1);
					linea = linea[2].split(",");		//linea = {"2",""};
					double precio = Double.parseDouble(linea[0]);	
					precio = Math.round(precio);
					//System.out.println("Precio: " + precio);
					act.setPrecio((int) precio);
				} else if(atributo.startsWith("rating")) {
					//---------------------------LEO LA VALORACION(SI HAY)---------------------------
					Integer valoraciones = null; 
					//En este caso, linea = {""price_level"", ":", "2,"}
					linea[0] = linea[0].substring(1);
					linea = linea[2].split(",");					//El rating viene dado en un valor de tipo double
					double valorD = Double.parseDouble(linea[0]);	//Pero las valoraciones de nuestra web son de tipo
					valorD = Math.round(valorD);
					//System.out.println("Valoracion: " + valoraciones);
					act.setValoraciones((int) valorD);
				} else if(atributo.startsWith("types")) {
					//---------------------------LEO LOS TIPOS---------------------------
					String[] filtros = new String[20];
					int contF = 0;
					if(linea[3] != null) {	//Formato: todos los tipos en la misma linea
						/*
						for(int i = 0;i < linea.length;i++) {
							System.out.println(linea[i]);
						}
						*/
						int a = 0;
						for(int j = 3;!linea[j+1].equals("],");j++) {		//Inicializo j a 3 pq los 3 primeros elementos de lista son: "types",:,[
							filtros[contF] = linea[j].substring(1,linea[j].length()-2);
							contF++;
							a = j+1;
						}
						filtros[contF] = linea[a].substring(1, linea[a].length()-1);
					} else {	//Formato: diferentes lineas	
						linea = sc.nextLine().split(" ");
						linea = quitarEspacios(linea);
						while(!linea[0].equals("],")) {		//Voy linea por linea añadiendo los tipos en el array filtros
							//System.out.println("Linea: " + linea[0]);
							if(linea[0].charAt(linea[0].length()-1) == ',') {	//No es el ultimo tipo de la lista
								filtros[contF] = linea[0].substring(1,linea[0].length()-2);
							} else {
								filtros[contF] = linea[0].substring(1,linea[0].length()-1);
							}
							contF++;
							linea = sc.nextLine().split(" ");
							linea = quitarEspacios(linea);
						}
					}
					//System.out.print("Filtros: ");
					for(int i = 0;i < contF;i++) {
						//System.out.print(filtros[i] + " ");
					}
					//System.out.println();
					for(int i = 0;i < contF;i++) {	
						if(!act.getRestaurante() && (filtros[i].equals("bar") || filtros[i].equals("cafe") || filtros[i].equals("restaurant")) ) {
							act.setRestaurante(true);
						} else if(!act.getTienda() && (filtros[i].equals("book_store") || filtros[i].equals("supermarket") || filtros[i].equals("gas_station") || filtros[i].equals("meal_takeaway") || filtros[i].equals("clothing_store") || filtros[i].equals("library") || filtros[i].equals("shopping_mall"))) {
								act.setTienda(true);
					} else if(!act.getOcio() && (filtros[i].equals("bowling_alley") || filtros[i].equals("spa"))) {
								act.setOcio(true);
					} else if(!act.getCultura() && (filtros[i].equals("art_gallery") || filtros[i].equals("museum") || filtros[i].equals("movie_theater"))) {
							act.setCultura(true);
						} else if(!act.getTransporte() && (filtros[i].equals("bus_station") || filtros[i].equals("train_station") || filtros[i].equals("airport"))) {
							act.setTransporte(true);
						} else if(!act.getNaturaleza() && (filtros[i].equals("park"))) {
							act.setNaturaleza(true);
						}
					}
					acts.add(act);	//Añado la actividad a la lista porque ya no tengo que mirar mas atributos
				}
				if(sc.hasNextLine()) {
					linea = sc.nextLine().split(" ");
					linea = quitarEspacios(linea);
					atributo = linea[0].substring(1);
				} 
				
			}		
			idx++;
		}
		sc.close();
		return acts;
	}

//	@RequestMapping("/yuste")
//	public String AddSitiosApi(Model modelo) throws FileNotFoundException {
//		List<Actividad> acts = obtenerAPI();
//
//		for(Actividad a : acts) {
//			actividadService.save(a);
//		}
//		
//		modelo.addAttribute("listaActividades",actividadService.obtenerTodos());
//		
//		return "/actividades/panel";
//	}
	
	@RequestMapping("/actividades/panel")
	public String managerActividad(Model modelo) {
		List<Actividad> actividades = actividadService.obtenerTodos();
		
		modelo.addAttribute("listaActividades",actividades);
		
		return "actividades/panel";
	}
	
	@RequestMapping("/actividades/panel/search/{query}")
	public String BusquedaActividades(@PathVariable("query") String query, Model modelo) {
		List<Actividad> actividades;
		if(query.isEmpty() || query.charAt(0) == '?') {
			actividades = actividadService.obtenerTodos();
		}else{
			actividades = actividadService.buscarActividad(query);
		}
		
		modelo.addAttribute("listaActividades",actividades);
		
		return "/actividades/panel";
	}
	@RequestMapping("/actividades/panel/add")
	public String addActividad(Model modelo) throws Exception {
		modelo.addAttribute("actividad",new Actividad());
		//anyadirActividad();
		return "actividades/add";
	}
	
	@PostMapping("/actividades/panel/save")
	public String saveActividad(Actividad a) throws Exception {
		//hayActividad();
		actividadService.save(a);
		return "redirect:/actividades/panel";
	}
	
	
	@RequestMapping("/actividades/panel/editar/{auid}")
	public String editActividad(@PathVariable("auid") Integer auid, Model modelo) {
		Actividad editable = actividadService.obtenerPorId(auid);
		modelo.addAttribute("actividad", editable);
		modelo.addAttribute("FechaDeCreacion", editable.getFechaCreacion());
		return "actividades/add";
	}
	
	@RequestMapping("/actividades/panel/borrar/{auid}")
	public String deleteActividad(@PathVariable("auid") Integer auid) throws Exception {
		actividadService.eliminarActividad(auid);
		//deleteActivity();
		return "redirect:/actividades/panel";
	}
	
	private int actividadCounter=0;
	
	public boolean hayActividad() throws Exception {
		if (actividadCounter==0)
			throw new Exception("No hay actividades");
		else
			return true;
	}

	public Integer getActividadCounter() {
		return actividadCounter;
	}

	public void deleteActivity() throws Exception {
		if (actividadCounter==0)
			throw new Exception("No hay actividades");
		else
			actividadCounter--;
	}

	public void anyadirActividad() throws Exception {
		if (actividadCounter==10)
			throw new Exception("Pasa el limite de actividades");
		else
			actividadCounter++;
	}
	
}

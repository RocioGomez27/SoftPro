package softpro.where2go.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import softpro.where2go.model.Actividad;
import softpro.where2go.model.Comentario;
import softpro.where2go.model.Usuario;
import softpro.where2go.service.ActividadService;
import softpro.where2go.service.ComentarioService;
import softpro.where2go.service.UsuarioService;
@Controller
public class ComentarioController {
	@Autowired
	ComentarioService comentarioService;
	
	@Autowired
	ActividadService actividadService;
	

	@Autowired
	UsuarioService usuarioService;
		
	@RequestMapping("/actividades/comentario/add") 
	public String addComentario(Model modelo) throws Exception {
		modelo.addAttribute("comentarios",new Comentario());
		//anaydirComentario();
		return "actividades/agregar";
	}
	
	@PostMapping("/actividades/comentarios/save")
	public ModelAndView saveComentario(@RequestParam("userUUID") Integer uuid,Comentario comentario, Model modelo) throws Exception {
		
		Actividad a = actividadService.obtenerPorId(comentario.getActividad().getAUID());
		a.addComentario(comentario);
		//guardarComentario(comentario);
		
		actividadService.save(a);
		comentarioService.save(comentario);
		
		Usuario session = usuarioService.obtenerPorUUID(uuid);
		ModelAndView rd = new ModelAndView("redirect:/home/" + a.getAUID());
		
		rd.addObject("usuario", session);
		rd.addObject("actividad",a);
		rd.addObject("newComentario", new Comentario());
		rd.addObject("userUsername", session.getUsername());
		rd.addObject("actividadAUID", a.getAUID());
		
		rd.setViewName("home/actividad");
				
		return rd;
	}
	
	@PostMapping("/actividades/comentarios/borrar/{CUID}")
	public ModelAndView borrarComentario(@PathVariable("CUID") Integer CUID,@RequestParam("userUUID") Integer uuid,Usuario u,Model modelo) throws Exception {
		Comentario c = comentarioService.obtenerPorId(CUID);
		
		Actividad a = actividadService.obtenerPorId(c.getActividad().getAUID());
		
		a.removeComentario(c);
		//deleteComentario();

		comentarioService.eliminarComentario(CUID);
		actividadService.save(a);

		Usuario session = usuarioService.obtenerPorUUID(uuid);
		ModelAndView rd = new ModelAndView("redirect:/home/" + a.getAUID());
		
		rd.addObject("usuario", session);
		rd.addObject("actividad",a);
		rd.addObject("newComentario", new Comentario());
		rd.addObject("userUsername", session.getUsername());
		rd.addObject("actividadAUID", a.getAUID());
		
		rd.setViewName("home/actividad");
		
		return rd;
	}
	
	
	
	
	
	
	
	private int comentarioCounter=0;
	public ArrayList<Comentario> coments= new ArrayList<>();
	
	public Integer getComentarioCounter() {
		return comentarioCounter;
	}
	
	public void anaydirComentario() throws Exception {
		if(comentarioCounter == 50) {
			throw new Exception("Pasa el limite de comentarios");
		}else {
			comentarioCounter++;
		}
	}
	
	public void guardarComentario(Comentario comenta) throws Exception {
		if(coments.size() == 50) {
			throw new Exception("Pasa el limite de comentarios guardados");
		}else {
			coments.add(comenta);
		}
	}
	
	public boolean hayComentario() throws Exception {
		if (comentarioCounter==0)
			throw new Exception("No hay comentarios");
		else
			return true;
	}
	
	public void deleteComentario() throws Exception {
		if (comentarioCounter==0)
			throw new Exception("No hay comentarios que borrar");
		else
			comentarioCounter--;
	}
	
}	

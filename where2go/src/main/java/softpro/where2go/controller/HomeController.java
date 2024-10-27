package softpro.where2go.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import softpro.where2go.model.Actividad;
import softpro.where2go.model.Comentario;
import softpro.where2go.model.Usuario;
import softpro.where2go.service.ActividadService;
import softpro.where2go.service.ComentarioService;
import softpro.where2go.service.UsuarioService;

@Controller
public class HomeController {

	@Autowired
	ActividadService actividadService;

	@Autowired
	ComentarioService comentarioService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@RequestMapping("/")
	public String goHomeScreen(Model model) {
		return "redirect:/home";
	}
	
	@PostMapping("/home/{AUID}")
	public String goActivityPage(@PathVariable("AUID") Integer AUID,Usuario u,Model modelo) {
		Actividad actividad = actividadService.obtenerPorId(AUID);
		Usuario usuario = usuarioService.obtenerPorUUID(u.getUUID());
		
		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("actividad",actividad);
		modelo.addAttribute("newComentario", new Comentario());
		modelo.addAttribute("userUsername",usuario.getUsername());
		modelo.addAttribute("actividadAUID",actividad.getAUID());
		
		return "home/actividad";
		
	}
	
	@PostMapping("/home/profile/{username}")
	public String perfil(@PathVariable("username") String username,Usuario u, Model modelo) {
		Usuario profileUser = usuarioService.obtenerPorUsername(username);
		Usuario usuario = usuarioService.obtenerPorUUID(u.getUUID());

		
		modelo.addAttribute("usuarioPerfil",profileUser);
		modelo.addAttribute("usuario", usuario);
		return "usuarios/perfil";
	}
	
	
	
	@PostMapping("/home/filtros={filtros}")
	public String aplicarFiltros(@PathVariable("filtros") String filtros, @RequestParam("rangoPies") Integer pies, @RequestParam("rangoPrecio") Integer precio, Usuario u,Model modelo) {
		
		modelo.addAttribute("listaActividades", actividadService.filtrar(filtros,pies,precio));
		modelo.addAttribute("usuario",usuarioService.obtenerPorUUID(u.getUUID()));
		modelo.addAttribute("badLogin",false);
		
		return "home/home";
	}
	
}

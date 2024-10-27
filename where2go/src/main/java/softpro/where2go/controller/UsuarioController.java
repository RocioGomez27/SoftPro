package softpro.where2go.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import softpro.where2go.model.Actividad;
import softpro.where2go.model.Usuario;
import softpro.where2go.service.ActividadService;
import softpro.where2go.service.UsuarioService;

@Controller
public class UsuarioController {
	
	
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	ActividadService actividadservice;
	
	@RequestMapping("/logout")
	public String logout(Model model) throws Exception {
		model.addAttribute("usuario",null);
		deleteUsuario();
		return "redirect:/home";
	}
	
	
	@RequestMapping("/home")
	public String moveToLogin(Model model) throws Exception {
		model.addAttribute("usuario", new Usuario());
		anyadirUsuario();
		return "usuarios/login";
	}
	
	@PostMapping("/home")
	public String logToPage(Usuario u,Model model) {
		Usuario bdUser = usuarioService.obtenerPorUsername(u.getUsername());
		if(bdUser != null && bdUser.getPassword().equals(u.getPassword())) {
			model.addAttribute("usuario", bdUser);
			model.addAttribute("badLogin",false);
			if(bdUser.getEsAdmin()) {
				return "redirect:/actividades/panel";
			}else {
				List<Actividad> actividades = actividadservice.obtenerTodos();
				model.addAttribute("listaActividades",actividades);
				return "home/home";
			}
		}else {
			model.addAttribute("badLogin",true);
			return "usuarios/login";
		}
	}
	
	@RequestMapping("/register")
	public String register(Model model) {
		model.addAttribute("usuario",new Usuario());
		return "usuarios/register";
	}
	
	@PostMapping("/register")
	public String saveUser(Usuario u,Model model) {
		Usuario bdUser = usuarioService.obtenerPorUsername(u.getUsername());
		if(bdUser == null) {
			model.addAttribute("badLogin",false);
			
			if(u.getAvatar() == "") {
				u.setAvatar(null);
			}
			u.setEsModerador(false);
			u.setEsAdmin(false);
			usuarioService.save(u);
			return "redirect:/home";
			
		} else {
			model.addAttribute("badLogin",true);
			return "usuarios/register";
		}
	}
	
	
	private int usuarioCounter=0;
	
	public boolean hayUsuario() throws Exception {
		if (usuarioCounter==0)
			throw new Exception("No hay usuarios");
		else
			return true;
	}

	public Integer getUsuarioCounter() {
		return usuarioCounter;
	}

	public void deleteUsuario() throws Exception {
		if (usuarioCounter==0)
			throw new Exception("No hay usuarios");
		else
			usuarioCounter--;
	}
	
	public void anyadirUsuario() throws Exception {
		if (usuarioCounter==100)
			throw new Exception("Pasa el limite de usuarios");
		else
			usuarioCounter++;
	}	
}
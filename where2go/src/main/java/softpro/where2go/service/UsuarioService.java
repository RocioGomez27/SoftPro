package softpro.where2go.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softpro.where2go.model.Usuario;
import softpro.where2go.repository.UsuarioRepository;

@Service
public class UsuarioService{
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public List<Usuario> obtenerTodos(){
		return usuarioRepository.findAll();
	}
	
	public void save(Usuario a) {
		usuarioRepository.saveAndFlush(a);
	}
	
	public void eliminarUsuario(Integer id) {
		usuarioRepository.deleteById(id);
	}

	public Usuario obtenerPorUUID(Integer Id) {
		return usuarioRepository.getById(Id);
	}
	
	public Usuario obtenerPorUsername(String username) {
		Usuario u = null;
		List<Usuario> todos = obtenerTodos();
		for(Usuario user : todos) {
			if(user.getUsername().equalsIgnoreCase(username)) {
				u = user;
				break;
			}
		}
		return u;
	}
	
}

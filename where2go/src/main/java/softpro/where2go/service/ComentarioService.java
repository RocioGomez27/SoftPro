package softpro.where2go.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softpro.where2go.model.Comentario;
import softpro.where2go.repository.ComentarioRepository;

@Service
public class ComentarioService {
	@Autowired
	ComentarioRepository comentarioRepository;

	public List<Comentario> obtenerTodos() {
		return comentarioRepository.findAll();
	}

	public void save(Comentario a) {
		comentarioRepository.saveAndFlush(a);
	}

	public void eliminarComentario(Integer id) {
		comentarioRepository.deleteById(id);
	}

	public Comentario obtenerPorId(Integer CUID) {
		return comentarioRepository.getById(CUID);
	}

}

package softpro.where2go;
import softpro.where2go.controller.*;
import softpro.where2go.model.Comentario;

import org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.*;

class Where2goApplicationTests {

	UsuarioController user;
	ComentarioController comment;
	ActividadController activity;
	
	@BeforeEach
	public void init()
	{
		user = new UsuarioController();
		comment = new ComentarioController();
		activity = new ActividadController();
	}
	
	@AfterEach
	public void terminated()
	{
		user = null;
		comment = null;
		activity = null;
	}

	@Test
	public void SiSeEliminaUnaActividadHabiendoCeroLanzaExcepcion()
	{
		Exception ex = assertThrows(Exception.class, ()->activity.deleteActivity());
		assertEquals("No hay actividades", ex.getMessage());
	}
	
	@Test
	public void SiSeAnyadeUnaActividadTeniendoMaxElevaExcepion() throws Exception
	{
		int x = 10;
		for (int i=0; i<x; i++)
		{
			activity.anyadirActividad();
		}
		assertEquals(activity.getActividadCounter(),x);
		Exception ex = assertThrows(Exception.class, ()->activity.anyadirActividad());
		assertEquals("Pasa el limite de actividades", ex.getMessage());
	}
	
	@Test
	public void SiNoHayActividadSeElevaExcepcion()
	{
		Exception ex = assertThrows(Exception.class, ()->activity.hayActividad());
		assertEquals("No hay actividades", ex.getMessage());
	}
	
	@Test
	public void InicialmenteElNumeroDeActividadesEsCero()
	{
		assertEquals(activity.getActividadCounter(), 0, "Mensaje error");
	}
	
	
	@Test
	public void SiSeEliminaUnUsuarioHabiendoCeroLanzaExcepcion()
	{
		Exception ex = assertThrows(Exception.class, ()->user.deleteUsuario());
		assertEquals("No hay usuarios", ex.getMessage());
	}
	
	@Test
	public void SiNoHayUsuariosSeElevaExcepcion()
	{
		Exception ex = assertThrows(Exception.class, ()->user.hayUsuario());
		assertEquals("No hay usuarios", ex.getMessage());
	}
	
	@Test
	public void InicialmenteElNumeroDeUsuariosEsCero()
	{
		assertEquals(user.getUsuarioCounter(), 0, "Mensaje error");
	}
	
	@Test
	public void SiSeAnyadeUnUsuarioTeniendoMaxElevaExcepion() throws Exception
	{
		int x = 100;
		for (int i=0; i<x; i++)
		{
			user.anyadirUsuario();
		}
		assertEquals(user.getUsuarioCounter(),x);
		Exception ex = assertThrows(Exception.class, ()->user.anyadirUsuario());
		assertEquals("Pasa el limite de usuarios", ex.getMessage());
	
	}
	
	
	@Test
	public void SiSeAnyadeUnComentarioTeniendoMaxElevaExcepion() throws Exception
	{
		int x = 50;
		for (int i=0; i<x; i++)
		{
			comment.anaydirComentario();
		}
		assertEquals(comment.getComentarioCounter(),x);
		Exception ex = assertThrows(Exception.class, ()->comment.anaydirComentario());
		assertEquals("Pasa el limite de comentarios", ex.getMessage());
	
	}
	
	@Test
	public void SiSeGuardaUnComentarioTeniendoMaxElevaExcepion() throws Exception
	{
		int x = 50;
		for (int i=0; i<x; i++)
		{
			Comentario commentMock = mock(Comentario.class);
			comment.guardarComentario(commentMock);
		}
		assertEquals(comment.coments.size(),x);
		
		//creamos un nuevo mock
		Comentario commentMock = mock(Comentario.class);
		
		Exception ex = assertThrows(Exception.class, ()->comment.guardarComentario(commentMock));
		assertEquals("Pasa el limite de comentarios guardados", ex.getMessage());
	
	}
	
	@Test
	public void InicialmenteElNumeroDeComentariosEsCero()
	{
		assertEquals(comment.getComentarioCounter(), 0, "Mensaje error");
	}
	
	public void SiSeEliminaUnComentarioHabiendoCeroLanzaExcepcion()
	{
		Exception ex = assertThrows(Exception.class, ()->comment.deleteComentario());
		assertEquals("No hay comentarios que borrar", ex.getMessage());
	}
}

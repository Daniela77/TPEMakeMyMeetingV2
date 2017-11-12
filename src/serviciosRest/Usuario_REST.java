package serviciosRest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entidades.Usuario;
import login.Secured;
import servicios.DAOUsuario;
import serviciosRest.Mensajes;

@Path("/usuarios")
public class Usuario_REST {
	
	// TODOS LOS USUARIOS
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getUsuarios() {
		return DAOUsuario.getInstance().getUsuarios();
	}

	 // CREAR UN USUARIO
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearUsuario(Usuario usuario) {
	Usuario nw = DAOUsuario.getInstance().crearUsuario(usuario.getNombre(),usuario.getApellido(),usuario.getUserName(),usuario.getPassword());
	if(nw!=null) {
		return Response.status(201).entity(nw).build();
	}
	throw new Mensajes(1,1);
	 /// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste 


	}
	
	
	// TRAE A UN USUARIO EN BASE A SU ID
	@GET
	@Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getUsuario(@PathParam("id") String id) {
	 	int idusuario = Integer.valueOf(id);
	 	Usuario usuario = DAOUsuario.getInstance().getUsuario(idusuario);
	 	if(usuario!=null)
			return usuario;
		else
			throw new Mensajes(2,idusuario);
   	 /// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste 

	}
	
	// MODIFICA A UN USUARIO EN BASE A SU ID

	@PUT
	@Secured
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUsuario(@PathParam("id") String id,Usuario usuario) {
	 	int idusuario = Integer.valueOf(id);
		Usuario result = DAOUsuario.getInstance().updateUsuario(idusuario, usuario.getNombre(),usuario.getApellido());
		if(result!=null) {
			return Response.status(201).entity(result).build();
		}
		throw new Mensajes(2,idusuario);
	   	 /// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste 
	}
	
	// BORRA A UN USUARIO EN BASE A SU ID

	@DELETE
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUsuario(@PathParam("id") String id) {
	 	int idusuario = Integer.valueOf(id);

	boolean resultado= DAOUsuario.getInstance().deleteUsuario(idusuario);
	if(resultado) {
		return Response.status(201).build();
	} 
	else {
		throw new Mensajes(3,idusuario);
	   	 /// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste 
	}	
	}


}
	
	
	
	
	


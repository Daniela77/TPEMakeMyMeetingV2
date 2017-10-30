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
import servicios.DAOUsuario;
import serviciosRest.Mensajes;

@Path("/usuarios")
public class Usuario_REST {
	
	// TODOS LOS USUARIOS
	@GET
	//@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getUsuario() {
		System.out.println("Llamando");
		return DAOUsuario.getInstance().getUsuarios();
	}

	 // CREAR UN USUARIO
	@POST
	//@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearusuario(Usuario usuario) {
	Usuario nw = DAOUsuario.getInstance().crearUsuario(usuario.getNombre(),usuario.getApellido(),usuario.getUserName(),usuario.getPassword());
	if(nw!=null) {
		return Response.status(201).entity(nw).build();
	}
	throw new Mensajes(nw.getId());

	}
	
	
	// TRAE A UN USUARIO EN BASE A SU ID
	@GET
	//@Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getUsuario(@PathParam("id") String id) {
	 	int idusuario = Integer.valueOf(id);
	 	Usuario usuario = DAOUsuario.getInstance().getUsuario(idusuario);
	 	if(usuario!=null)
			return usuario;
		else
			throw new Mensajes(idusuario);
	}
	
	// MODIFICA A UN USUARIO EN BASE A SU ID

	@PUT
	//@Secured
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUsuario(@PathParam("id") int id,Usuario usuario) {
		Usuario result = DAOUsuario.getInstance().updateUsuario(id, usuario.getNombre(),usuario.getApellido());
		if(result!=null) return Response.status(201).entity(result).build();
		throw new Mensajes(id);
	}
	
	// BORRA A UN USUARIO EN BASE A SU ID

	@DELETE
	//@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUsuario(@PathParam("id") int id) {
	boolean resultado= DAOUsuario.getInstance().deleteUsuario(id);
	if(resultado) {
		return Response.status(201).build();
	} 
	else {
		throw new Mensajes(id);
	}	
	}
	
	
	////////
	////////   	Hacer el GET para el login
	////////
	////////
	
	

}
	
	
	
	
	


package servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entidades.Usuario;


@Path("/usuario")
public class Usuario_REST {
	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Perro> getAllPerros() {
//		return PerroDAO.getInstance().findAll();
//	}
//	
	@GET
	@Path("/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario getUsuarioById(@PathParam("idUsuario") String msg) {
		int idUsuario = Integer.valueOf(msg);
		Usuario usuario = DAOUsuario.getInstance().getUsuario(idUsuario);
		if(usuario!=null)
			return usuario;
		else
			throw new RecursoNoExiste(idUsuario);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUsuario(Usuario usuario) {
		Usuario result=  DAOUsuario.getInstance().crearUsuario(usuario.getNombre(), usuario.getApellido(),usuario.getUserName(),usuario.getPassword());
		if(result==null) {
			throw new RecursoDuplicado(usuario.getId());
		}else {
			return Response.status(201).entity(usuario).build();
		}
	}
	
	@DELETE
	@Path("/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePerro(@PathParam("idUsuario") int idB) {
//		throw new UnsupportedOperationException();
		int idUsuario = Integer.valueOf(idB);
		DAOUsuario.getInstance();
		boolean usuario = DAOUsuario.getInstance().deleteUsuario(idUsuario);
		if(!usuario)
			
			return Response.status(201).entity(usuario).build();
		else
			throw new RecursoNoExiste(idUsuario);
	}
	
	@PUT
	@Path("/{idUsuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePerro(@PathParam("idUsuario") int idUsuario,Usuario usuario) {
		throw new UnsupportedOperationException();
	}
	
//	@GET
//	@Path("/findPerrosByEdad")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Perro> findPerrosByEdad(@QueryParam("from") int from,
//			@QueryParam("to") int to) {
//		throw new UnsupportedOperationException();
//	}
//
	public class RecursoDuplicado extends WebApplicationException {
	     public RecursoDuplicado(int id) {
	         super(Response.status(Response.Status.CONFLICT)
	             .entity("El recurso con ID "+id+" ya existe").type(MediaType.TEXT_PLAIN).build());
	     }
	}
	
	public class RecursoNoExiste extends WebApplicationException {
	     public RecursoNoExiste(int id) {
	         super(Response.status(Response.Status.NOT_FOUND)
	             .entity("El recurso con id "+id+" no fue encontrado").type(MediaType.TEXT_PLAIN).build());
	     }
	}
	
	

}
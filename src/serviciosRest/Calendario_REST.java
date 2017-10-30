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

import entidades.Calendario;
import entidades.Usuario;
import servicios.DAOCalendario;
import servicios.DAOUsuario;
import serviciosRest.Mensajes;

@Path("/Calendarios")
public class Calendario_REST {
	
	// TODOS LOS CalendarioS
	@GET
	//@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public List<Calendario> getCalendario() {
		return DAOCalendario.getInstance().getCalendarios();
	}

	 // CREAR UN Calendario
	@SuppressWarnings("null")
	@POST
	//@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearCalendario(Calendario calendario) {
	Usuario duenio = DAOUsuario.getInstance().getUsuario(calendario.getDuenio().getId());	
	Calendario nw = DAOCalendario.getInstance().crearCalendario(calendario.getNombre(),duenio);
	if(nw!=null) {
		return Response.status(201).entity(nw).build();
	}
	throw new Mensajes(nw.getId());

	}
	
	
	// TRAE A UN Calendario EN BASE A SU ID
	@GET
	//@Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Calendario getCalendario(@PathParam("id") String id) {
	 	int idCalendario = Integer.valueOf(id);
	 	Calendario Calendario = DAOCalendario.getInstance().getCalendario(idCalendario);
	 	if(Calendario!=null)
			return Calendario;
		else
			throw new Mensajes(idCalendario);
	}
	
	// MODIFICA A UN Calendario EN BASE A SU ID

	@PUT
	//@Secured
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCalendario(@PathParam("id") int id,Calendario Calendario) {
		Calendario result = DAOCalendario.getInstance().updateCalendario(id, Calendario.getNombre(),Calendario.getDuenio().getId());
		if(result!=null) return Response.status(201).entity(result).build();
		throw new Mensajes(id);
	}
	
	// BORRA A UN Calendario EN BASE A SU ID

	@DELETE
	//@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCalendario(@PathParam("id") int id) {
	boolean resultado= DAOCalendario.getInstance().deleteCalendario(id);
	if(resultado) {
		return Response.status(201).build();
	} 
	else {
		throw new Mensajes(id);
	}	
	}
	

}
	
	
	
	
	


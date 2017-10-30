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

import entidades.Sala;
import servicios.DAOSala;
import serviciosRest.Mensajes;

@Path("/salas")

public class Sala_REST {
	
	
	// TODAS LAS SALAS
	@GET
	//@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sala> getSalas() {
		return DAOSala.getInstance().getSalas();
	}

	// CREAR UNA SALA
	@POST
	//@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearSala(Sala sala) {
		Sala nw= DAOSala.getInstance().crearSala(sala.getNombre(), sala.getDireccion());
		if(nw!=null) {
			return Response.status(201).entity(nw).build();
		}
		throw new Mensajes(nw.getId());
	}
		

	// TRAE A UNA SALA EN BASE A SU ID

	@GET
	//@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Sala getSala(@PathParam("id") String id) {
		int idSala = Integer.valueOf(id);
		Sala sala = DAOSala.getInstance().getSala(idSala);
		if(sala!=null)
			return sala;
		else
			throw new Mensajes(idSala);
	}

	// MODIFICA A UNA SALA EN BASE A SU ID

		@PUT
		//@Secured
		@Path("/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response updateSala(@PathParam("id") int id,Sala sala) {
			Sala result = DAOSala.getInstance().updateSala(id, sala.getNombre(),sala.getDireccion());
			if(result!=null) return Response.status(201).entity(result).build();
			throw new Mensajes(id);
		}
		
		// BORRA A UNA SALA EN BASE A SU ID

		@DELETE
		//@Secured
		@Path("/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response deleteSala(@PathParam("id") int id) {
		boolean resultado= DAOSala.getInstance().deleteSala(id);
		if(resultado) {
			return Response.status(201).build();
		} 
		else {
			throw new Mensajes(id);
		}	
		}
		
		

}

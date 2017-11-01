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
import login.Secured;
import servicios.DAOSala;
import serviciosRest.Mensajes;

@Path("/salas")

public class Sala_REST {
	
	
	// TODAS LAS SALAS
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sala> getSalas() {
		return DAOSala.getInstance().getSalas();
	}

	// CREAR UNA SALA
	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearSala(Sala sala) {
		Sala nw= DAOSala.getInstance().crearSala(sala.getNombre(), sala.getDireccion());
		if(nw!=null) {
			return Response.status(201).entity(nw).build();
		}
		throw  new Mensajes(1,1);
		/// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste

	}
		

	// TRAE A UNA SALA EN BASE A SU ID

	@GET
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Sala getSala(@PathParam("id") String id) {
		int idSala = Integer.valueOf(id);
		Sala sala = DAOSala.getInstance().getSala(idSala);
		if(sala!=null)
			return sala;
		else
			throw new Mensajes(2,idSala);
		/// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste

	}

	// MODIFICA A UNA SALA EN BASE A SU ID

		@PUT
		@Secured
		@Path("/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response updateSala(@PathParam("id") String id,Sala sala) {
			int idSala = Integer.valueOf(id);
			Sala result = DAOSala.getInstance().updateSala(idSala, sala.getNombre(),sala.getDireccion());
			if(result!=null) { 
				return Response.status(201).entity(result).build();
			}
			throw new Mensajes(2,idSala);
			/// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste

		}
		
		// BORRA A UNA SALA EN BASE A SU ID

		@DELETE
		@Secured
		@Path("/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response deleteSala(@PathParam("id") String id) {
		int idSala = Integer.valueOf(id);
		boolean resultado= DAOSala.getInstance().deleteSala(idSala);
		if(resultado) {
			return Response.status(201).build();
		} 
		else {
			throw new Mensajes(3,idSala);
			/// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste

		}	
		}
		
		

}

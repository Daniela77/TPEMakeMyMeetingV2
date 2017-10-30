package serviciosRest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Mensajes extends WebApplicationException{
	
		private static final long serialVersionUID = 2463410567129045955L;
	//  https://stackoverflow.com/questions/583973/jax-rs-jersey-how-to-customize-error-handling
	     public  Mensajes(int id) {
	         super(Response.status(Response.Status.NOT_FOUND)
	             .entity("El recurso con id "+id+" no fue encontrado/creado").type(MediaType.TEXT_PLAIN).build());
	     }
}

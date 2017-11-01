package serviciosRest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Mensajes extends WebApplicationException{
	
		private static final long serialVersionUID = 2463410567129045955L;
		//  https://stackoverflow.com/questions/583973/jax-rs-jersey-how-to-customize-error-handling
	     public  Mensajes(int opcion, int id) {
	    	 /// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste 
	    	 switch (opcion) {
	         case 1:
	        	 new RecursoNoCreado();
	         break;
	  
	         case 2:
	        	 new RecursoNoEncontrado(id);
	         break;
	         
	         case 3:
	        	 new RecursoNoExiste(id);
	        
	         break;
	    	 }
	   }
	    	
		public class RecursoNoCreado extends WebApplicationException {
		    public RecursoNoCreado() {
		        super(Response.status(Response.Status.CONFLICT)
		            .entity("El recurso no se pudo crear por problemas de formato").type(MediaType.TEXT_PLAIN).build());
		    }
		}
		
		public class RecursoNoEncontrado extends WebApplicationException {
		    public RecursoNoEncontrado(int id) {
		        super(Response.status(Response.Status.CONFLICT)
		            .entity("El recurso con el id:"+id+" solicitado no se encontro ").type(MediaType.TEXT_PLAIN).build());
		    }
		}
		public class RecursoNoExiste extends WebApplicationException {
		    public RecursoNoExiste(int id) {
		        super(Response.status(Response.Status.NOT_FOUND)
		            .entity("El recurso con id "+id+" no fue encontrado").type(MediaType.TEXT_PLAIN).build());
		    }
		}
	
}
		
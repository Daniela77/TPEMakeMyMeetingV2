package login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import entidades.Usuario;
import servicios.DAOUsuario;

public class AutenticacionService {
	@POST
    @Produces("application/json")
    @Consumes("application/json")
	
	public Response autenticarUser(Credencial credentials) {

        String username = credentials.getUsername();
        String password = credentials.getPassword();
        try {
   
            autenticar(username, password);
       
            String token = emitirToken(username);
       
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }    	  
	}
	
	private void autenticar(String username, String password) throws Exception {
	     Usuario user = new Usuario();
	     user.setUserName(username);
	     user.setPassword(password);
	     user = DAOUsuario.getInstance().login(user);
	     if(user == null){
	    	 throw new RuntimeException();
	     }
   }

	private String emitirToken(String username) {
	    String token = TokenHelper.generarToken(username);
	 	TokenHelper.setToken(token, username);
	 	return token;
	}

}

package login;

import java.io.Serializable;

public class Credencial implements Serializable {

	private static final long serialVersionUID = 4759607722724330633L;
	private String username;
    private String password;
    
    
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    
    
}

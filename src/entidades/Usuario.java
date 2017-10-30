package entidades;

//import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USUARIO")
public class Usuario /*implements Serializable*/ {

	//private static final long serialVersionUID = -4686073705675341538L;
	@Id 
	@GeneratedValue
	private int idUsuario;
	//@Column(nullable=false)
	private String nombre;
	private String apellido;
	private String userName;
    private String password;
    // Calendario
	@OneToMany(mappedBy = "duenio", cascade = CascadeType.PERSIST)
    private List<Calendario> calendarios;
	// Invitacion
	//@OneToMany
	@OneToMany(mappedBy ="usuario_i", cascade=CascadeType.PERSIST)
	private List<Invitacion> listInvitaciones;
	// Actividad
	@ManyToMany (mappedBy ="invitados", cascade=CascadeType.PERSIST)
	private List<Actividad >actividadesInvitado;
	
	public Usuario() {
	}
	
	public Usuario(String nombre, String apellido) {
		// usuario sin UserName ni pass
		this.nombre = nombre;
		this.apellido = apellido;
		this.userName = null;
		this.password = null;
		this.calendarios =  new ArrayList<Calendario>();
		this.actividadesInvitado =  new ArrayList<Actividad>();
		this.listInvitaciones =  new ArrayList<Invitacion>();

	}

	public Usuario(String nombre, String apellido, String UN, String ps) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.userName = UN;
		this.password = ps;
		this.calendarios =  new ArrayList<Calendario>();
		this.actividadesInvitado =  new ArrayList<Actividad>();
		this.listInvitaciones =  new ArrayList<Invitacion>();
	}
	
	public int getId() {
		return idUsuario;
	}

	public void setId(int id) {
		this.idUsuario = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public List<Invitacion> getListInvitaciones() {
		return listInvitaciones;
	}

	public void setListInvitaciones(List<Invitacion> listInvitaciones) {
		this.listInvitaciones = listInvitaciones;
	}
	public void setInvitacion(Invitacion invitacion) {
		this.listInvitaciones.add(invitacion);
	}

	public List<Actividad> getActividadesInvitado() {
		return actividadesInvitado;
	}

	public void setActividadesInvitado(List<Actividad> actividadesInvitado) {
		this.actividadesInvitado = actividadesInvitado;
	}

	////////////////////////////////// Calendarios
	public List<Calendario> getCalendarios() {
		return calendarios;
	}

	public void setCalendarios(List<Calendario> calendarios) {
		for (int i = 0;i< this.calendarios.size();i++){
			this.setCalendario(this.calendarios.get(i));
		}
	}
	
	public void setCalendario(Calendario calendario) {
		this.calendarios.add(calendario);
	}

	 /// compartir calendarios y aceptar calendarios de otros
	  
	

	public void agregarCalendario(Calendario calendario) {
	        this.calendarios.add(calendario);
	    }
	 
	public Calendario buscarCalendarioID( int id){
		for (int i = 0;i< this.calendarios.size();i++){
			if(	this.calendarios.get(i).getId() == id) {
				return this.calendarios.get(i); }
			}
		return null;
		
	}
	  public void compartirCalendario(Calendario calendario, Usuario usuario) {
		  usuario.agregarCalendario(calendario);
	    }

	  
	/*
	//////////////////////////// Todo lo relacionado con recordar Invitaciones de Actividades pendientes
	*/

	public void recordarInvitacion(Actividad actividad) {
		boolean existe = false;
		for (int i = 0;i< this.actividadesInvitado.size();i++){
			if (actividad.equals(this.actividadesInvitado.get(i))) {
				existe = true;
			}
		}
		if (! existe) {
			this.listInvitaciones.add(new Invitacion(actividad, this));
		}
	}
	
	
	
	////////// equals

	public boolean equals(Object obj) {
        Usuario u = (Usuario) obj;
        return u.idUsuario == idUsuario && u.nombre.equals(nombre)&& u.apellido.equals(apellido) 
        		&& u.userName.equals(userName) && u.password.equals(password);	
    }

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellido=" + apellido + ", userName="
				+ userName + ", password=" + password + ", calendarios=" + calendarios + ", listInvitaciones="
				+ listInvitaciones + ", actividadesInvitado=" + actividadesInvitado + "]";
	}

	
	}
package entidades;

//import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;

@Entity
@Table(name="ACTIVIDAD")
public class Actividad /*implements Serializable*/ {

	//private static final long serialVersionUID = -1269548906097037547L;
	@Id
	@GeneratedValue
	private int id;
	//@Column(nullable=false)
	private String nombre;
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Usuario duenio;
	//@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	//@Temporal(TemporalType.DATE)
	private Date fechaFin;

	@ManyToOne(cascade=CascadeType.PERSIST)
	private Sala lugar;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	private List<Usuario> invitados;
	
	// se saco la idea de usuarios pendientes y que solo quede todo en invitados
	//@ManyToOne -    porque un usuario puede tener muchos calendarios y un calendario muchos usuarios
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Calendario calendario;
		
	public Actividad() {}
	
	public Actividad(String nombre, Usuario duenio, Date fechaInicio, Date fechaFin, Calendario c) {
		this.nombre = nombre;
		this.duenio = duenio;
		this.fechaInicio =  fechaInicio;
		this.fechaFin =  fechaFin;
		this.lugar = null; // el lugar se lo agrega despues
		this.invitados = new ArrayList<Usuario>();
		this.calendario = c;
//		this.pendientes = new ArrayList<Usuario>();
	}
	

	public Calendario getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Usuario getDuenio() {
		return duenio;
	}

	public void setDuenio(Usuario duenio) {
		this.duenio = duenio;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Sala getLugar() {
		return lugar;
	}

	public void setLugar(Sala lugar) {
		if (lugar.hayLugar(this)) {
			this.lugar = lugar;
			this.lugar.setActividad(this);
		}
		//mmdy: habria que ver como indicar en caso de no poder ponerlo en esa sala 
	}
	
	////////////////////////////////////////////////

    public void setInvitado(Usuario usuario) {
   	 this.invitados.add(usuario);
   	 // creamos una invitacion para hace rla asociacion entre usuario y actividad
   	 // V 2.0 del tpe / antes se hacia todo en actividad pero se le quito esta responsabilidad
   	 usuario.setInvitacion(new Invitacion(this, usuario));
   }	
	
	public List<Usuario> getInvitados() {
		return invitados;
	}

	////////////////////////////////////////////////

	
	public boolean compararSuperPosicion(Actividad act) {
		// verifica si hay superposiciones entre las fechas de 2 actividades
		//If the Integer is equal to the argument then 0 is returned.
		//If the Integer is less than the argument then -1 is returned.
		//If the Integer is greater than the argument then 1 is returned.
    	Date acti = act.getFechaInicio();
    	Date actf = act.getFechaFin();
        if (((acti.compareTo(this.fechaFin) <= 0 )&& (actf.compareTo(this.fechaFin) >= 0))  ||
        	((this.fechaInicio.compareTo(acti)<= 0)&& (this.fechaFin.compareTo(actf)>= 0))  	
        		) { return true;}
       return false;
    }
	
	////////////////////////////////////////////////
	
	public void recordarInvitacion() {
		Usuario usuario;
		for (int i = 0;i< this.invitados.size();i++){
			 usuario = this.invitados.get(i);
             usuario.recordarInvitacion(this);
            }
        }
        
	/////////// equals
	 public boolean equals(Object obj) {
	        Actividad act = (Actividad) obj;
	        return act.id == this.id && act.fechaInicio.equals(this.fechaInicio) && act.fechaFin.equals(this.fechaFin);
	    }
	 
	 @Override
		public String toString() {
			return "Actividad [id=" + id + ", nombre=" + nombre + ", duenio=" + duenio + ", fechaInicio=" + fechaInicio
					+ ", fechaFin=" + fechaFin + ", lugar=" + lugar + ", invitados=" + invitados
					 + ", calendario=" + calendario + "]";
		}

	
}
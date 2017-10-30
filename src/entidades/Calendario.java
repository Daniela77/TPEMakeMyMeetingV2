package entidades;

//import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
//import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="CALENDARIO")

public class Calendario /*implements Serializable */{

	//private static final long serialVersionUID = 2384534942958061502L;
	@Id
	@GeneratedValue
	private int id;
	//@Column(nullable=false)
	private String nombre;
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Usuario duenio;

   	@OneToMany(mappedBy="calendario",cascade=CascadeType.PERSIST)
	private List<Actividad> actividades;

	public Calendario(){
		
	}
	
	public Calendario(String nombre, Usuario duenio) {
		this.nombre = nombre;
		this.actividades = new ArrayList<Actividad>();
//		this.usuarioscompartidos = new ArrayList<Usuario>();
		this.duenio = duenio;
	}

	public int getId() {
		return id;
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
	public void setDueño(Usuario duenio) {
		this.duenio = duenio;
	}

	// relacionadas a las actividades
	public List<Actividad> getActividades() {
		return actividades;
	}
	
	public void setActividades(List<Actividad> actividades) {
		for (int i = 0;i< actividades.size();i++){
			this.setActividad(actividades.get(i));
		}
	}
	
	public void setActividad(Actividad actividad) {
		//Al agregar una actividad al calendario, a esta misma se le asocia dicho calendario
		if (this.disponibilidad(actividad)) {
		this.actividades.add(actividad);
		actividad.setCalendario(this);
		}
	}

	
	public boolean disponibilidad(Actividad act) {
	/// busca en su listado de actividades si hay disponibilidad para agregar una nueva	
		boolean valor = true;	
		for (int i = 0;i< this.actividades.size();i++){
			if (valor == true) {
				valor = (this.actividades.get(i).compararSuperPosicion(act));
			}
			else{
				return false;
			}
		}
		return true;	
	}

	public void eliminarActividad(Actividad act) {
		 this.actividades.remove(act);
	}
	
}

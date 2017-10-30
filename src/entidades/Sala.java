package entidades;

//import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

//import javax.persistence.CascadeType;
//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SALA")
public class Sala /*implements Serializable */{

	//private static final long serialVersionUID = -2864708753027261357L;
	@Id
	@GeneratedValue
	private int id;
	//@Column(nullable=false)
	private String nombre;
	private String direccion;

	@OneToMany(mappedBy="lugar")
	private List<Actividad> actividades;
	
	public Sala() {
		
	}
	
	public Sala(String nombre, String direccion) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.actividades = new ArrayList<Actividad>();
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	// actividades
	public List<Actividad> getActividades() {
		return actividades;
	}
	
	public void setActividad(Actividad actividad) {
		if (this.hayLugar(actividad)) {
				this.actividades.add(actividad);
		}
	}
	
	public boolean hayLugar(Actividad act) {
		for(int i=0; i<  actividades.size(); i++) {
			if (this.actividades.get(i).compararSuperPosicion(act)) {
				return false;
				}
		}
		 return true;
	}
	
	
	@Override
	public String toString() {
		return "Sala [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", Actividades=" + actividades
				+ "]";
	}
	
	
}

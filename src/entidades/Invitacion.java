package entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Invitacion {
	
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	private Actividad actividad_i;
	@ManyToOne
	private Usuario usuario_i;
	
	public Invitacion() {}

	public Invitacion(Actividad actividad_i, Usuario usuario_i) {
		super();
		this.actividad_i = actividad_i;
		this.usuario_i = usuario_i;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Actividad getActividad_i() {
		return actividad_i;
	}

	public void setActividad_i(Actividad actividad_i) {
		this.actividad_i = actividad_i;
	}

	public Usuario getUsuario_i() {
		return usuario_i;
	}

	public void setUsuario_i(Usuario usuario_i) {
		this.usuario_i = usuario_i;
	}
	
	

}

package serviciosRest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Obj_Actividad {
	// Objeto Json
			private String nombre;
			private int duenio;
			private String fechaInicio;
			private String fechaFin;
			private int lugar;
			private int calendario;
			@JsonCreator
			public Obj_Actividad(@JsonProperty("nombre")String nombre, @JsonProperty("duenio") int id_duenio,
								 @JsonProperty("fechaInicio")String fechai, @JsonProperty("fechaFin") String fechaf,
								 @JsonProperty("lugar")int sala, @JsonProperty("calendario") int calendario) {
				this.nombre = nombre;
				this.duenio = id_duenio;
				this.fechaInicio = fechai;
				this.fechaFin = fechaf;
				this.lugar = sala;
				this.calendario = calendario;
			}
			public String getNombre() {
				return nombre;
			}
			public void setNombre(String nombre) {
				this.nombre = nombre;
			}
			public int getDuenio() {
				return duenio;
			}
			public void setDuenio(int duenio) {
				this.duenio = duenio;
			}
			public String getFechaInicio() {
				return fechaInicio;
			}
			public void setFechaInicio(String fechaInicio) {
				this.fechaInicio = fechaInicio;
			}
			public String getFechaFin() {
				return fechaFin;
			}
			public void setFechaFin(String fechaFin) {
				this.fechaFin = fechaFin;
			}
			public int getLugar() {
				return lugar;
			}
			public void setLugar(int lugar) {
				this.lugar = lugar;
			}
			public int getCalendario() {
				return calendario;
			}
			public void setCalendario(int calendario) {
				this.calendario = calendario;
			}

		

	}
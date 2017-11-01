package serviciosRest;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Obj_Calendario {
// Objeto Json
		private String nombre;
		private int duenio;

		@JsonCreator
		public Obj_Calendario(@JsonProperty("nombre")String nombre, @JsonProperty("duenio") int id_duenio) {
			this.nombre = nombre;
			this.duenio = id_duenio;
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

}
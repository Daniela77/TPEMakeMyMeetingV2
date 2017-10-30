package serviciosRest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entidades.Calendario;
import entidades.Sala;
import entidades.Usuario;
import login.Secured;
import entidades.Actividad;

import servicios.DAOCalendario;
import servicios.DAOSala;
import servicios.DAOUsuario;
import servicios.DAOActividad;

import serviciosRest.Mensajes;

@Path("/actividades")
public class Actividad_REST {
	
	// TODOS LOS ActividadS
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public List<Actividad> getActividad() {
		return DAOActividad.getInstance().getActividades();
	}

	
	//	String nombre,int idCalendario, int usuario ,Date fechaInicio, Date fechafin ,Sala sala ) {

	 // CREAR UNA Actividad
	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearActividad(Actividad actividad) {
	Usuario duenio = DAOUsuario.getInstance().getUsuario(actividad.getDuenio().getId());
	Calendario calendario = DAOCalendario.getInstance().getCalendario(actividad.getCalendario().getId());
	Sala sala = DAOSala.getInstance().getSala(actividad.getLugar().getId());
	Actividad nw = DAOActividad.getInstance().crearActividad(actividad.getNombre(),calendario.getId(),duenio.getId(),actividad.getFechaInicio(),actividad.getFechaFin(),sala);
	if(nw!=null) {
		return Response.status(201).entity(nw).build();
	}
	throw new Mensajes(nw.getId());

	}
	
	
	// TRAE A UN Actividad EN BASE A SU ID
	@GET
	@Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Actividad getActividad(@PathParam("id") String id) {
	 	int idActividad = Integer.valueOf(id);
	 	Actividad actividad = DAOActividad.getInstance().getActividad(idActividad);
	 	if(actividad!=null)
			return actividad;
		else
			throw new Mensajes(idActividad);
	}
	
	// MODIFICA A UN ACTIVIDAD EN BASE A SU ID

	@PUT
	@Secured
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateActividad(@PathParam("id") int id,Actividad actividad) {
		Actividad result = DAOActividad.getInstance().updateActividad( id, actividad.getNombre(),actividad.getCalendario().getId(),actividad.getDuenio().getId() ,actividad.getFechaInicio(),actividad.getFechaFin() ,actividad.getLugar());
		if(result!=null) return Response.status(201).entity(result).build();
		throw new Mensajes(id);
	}
	
	// BORRA A UN ACTIVIDAD EN BASE A SU ID

	@DELETE
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteActividad(@PathParam("id") int id) {
	boolean resultado= DAOActividad.getInstance().deleteActividad(id);
	if(resultado) {
		return Response.status(201).build();
	} 
	else {
		throw new Mensajes(id);
	}	
	}
	

	
	
	//  //
	//  //    	AGREGAR LAS FUNCIONES PEDIDAS
	//  //
	//  //
	
	@GET
	@Secured
	@Path("/get_actividades_usuario_sobrepuestas")
	@Produces(MediaType.APPLICATION_JSON)
	// es la que trae Actividades si estan sobrepuestas con la fecha de inicio y fin de una nueva 
	public List<Actividad> getActividadesSobrepuestasUsuario(@QueryParam("idUsuario") int idUsuario, @QueryParam("idActividad") int idActividad){
		Actividad nuevaActividad = DAOActividad.getInstance().getActividad(idActividad);
		Usuario usuario = DAOUsuario.getInstance().getUsuario(idUsuario);
		if(nuevaActividad!=null && usuario!=null) {
			List<Actividad> actividadesSuperpuestas = DAOActividad.getInstance().getActividadesSobrepuestasUsuario(usuario.getId(), nuevaActividad.getId());
			if(actividadesSuperpuestas!=null) {
				return actividadesSuperpuestas;
			}		
		}
		throw new Mensajes(idActividad);
		
	}
	
	
	@GET
	@Secured
	@Path("/get_actividades_usuario_fecha")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Actividad> getReunionesUsuarioXFecha(@QueryParam("idUsuario") int idUsuario, @QueryParam("fecha") String fecha) {
		
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy"); // con esto creamos un formato a la fecha que viene como string
		try {  /// tuve que meter un try&catch por ParseException
		Date nfecha = formatoFecha.parse(fecha); // con esto le asignamos e; formato a la fecha que viene como string
		Usuario usuario = DAOUsuario.getInstance().getUsuario(idUsuario);
		List<Actividad> reuniones = DAOActividad.getInstance().getActividadDeUsuarioxFecha(usuario.getId(), nfecha);
		if(reuniones!=null)	
			return reuniones;
			
		}
	  catch (ParseException e) {
            e.printStackTrace();
        }
		 return new ArrayList<Actividad>();
	}


	@GET
	@Secured
	@Path("/actividades_usuario_entre_fechas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Actividad> getReunionesUsuarioEntreFecha(@QueryParam("idUsuario") int idUsuario, @QueryParam("fecha1") String fecha1,@QueryParam("fecha2") String fecha2) {

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy"); // con esto creamos un formato a la fecha que viene como string
		;
		try {
			Date nfecha1 = formatoFecha.parse(fecha1);
			Date nfecha2 = formatoFecha.parse(fecha2);
			Usuario usuario = DAOUsuario.getInstance().getUsuario(idUsuario);
			if((nfecha1!=null && nfecha2!=null) && usuario !=null){
				List<Actividad> actividades = DAOActividad.getInstance().getActividadDeUsuarioEntreDias(usuario.getId(),nfecha1, nfecha2);
				if(actividades!=null)	
					return actividades;
			}
		}	
		catch (ParseException e) {
			e.printStackTrace();
		}


		return new ArrayList<Actividad>();

	}


	
	
	
}


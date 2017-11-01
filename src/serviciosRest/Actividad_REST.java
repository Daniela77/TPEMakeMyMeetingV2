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
import entidades.Actividad;
import login.Secured;


import servicios.DAOCalendario;
import servicios.DAOSala;
import servicios.DAOUsuario;
import servicios.DAOActividad;

import serviciosRest.Obj_Actividad;
import serviciosRest.Mensajes;

@Path("/actividades")
public class Actividad_REST {
	private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy:hh-mm"); // con esto creamos un formato a la fecha que viene como string

	// TODOS LAS ACTIVIDADES
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public List<Actividad> getActividades() {
		return DAOActividad.getInstance().getActividades();
	}

	
	//	String nombre,int idCalendario, int usuario ,Date fechaInicio, Date fechafin ,Sala sala ) {

	 // CREAR UNA Actividad
	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearActividad(Obj_Actividad actividad) {
	final Date fechaInicio;
	final Date fechaFin;
	try {
		  fechaInicio =  formatoFecha.parse(actividad.getFechaInicio()); 
		  fechaFin =  formatoFecha.parse(actividad.getFechaFin());
	      Usuario duenio = DAOUsuario.getInstance().getUsuario(actividad.getDuenio());
		  Calendario calendario = DAOCalendario.getInstance().getCalendario(actividad.getCalendario());
		  Sala sala = DAOSala.getInstance().getSala(actividad.getLugar());
		  Actividad nw = DAOActividad.getInstance().crearActividad(actividad.getNombre(),calendario.getId(),duenio.getId(),fechaInicio,fechaFin,sala);
		  if(nw!=null) {
				return Response.status(201).entity(nw).build();
			}
	
	} catch(Exception e) {
		 throw  new Mensajes(1,1);
		/// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste

	}
	return null;

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
			throw  new Mensajes(2,idActividad);
		/// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste

	}
	
	// MODIFICA A UN ACTIVIDAD EN BASE A SU ID

	@PUT
	@Secured
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateActividad(@PathParam("id") String id,Obj_Actividad actividad) {
	 	int idActividad = Integer.valueOf(id);
		Date fechaInicio;
		Date fechaFin;
		try {
			  	fechaInicio =  formatoFecha.parse(actividad.getFechaInicio()); 
			  	fechaFin =  formatoFecha.parse(actividad.getFechaFin());
				Actividad result = DAOActividad.getInstance().updateActividad( idActividad, actividad.getNombre(),actividad.getCalendario(),actividad.getDuenio() ,fechaInicio,fechaFin,actividad.getLugar());
				if(result!=null) 
					return Response.status(201).entity(result).build();
				}
		catch(Exception e) {
			throw  new Mensajes(2,idActividad);
			/// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste

		}
		return null;
		}
	// BORRA A UN ACTIVIDAD EN BASE A SU ID

	@DELETE
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteActividad(@PathParam("id") String id) {
	int idActividad = Integer.valueOf(id);
	boolean resultado= DAOActividad.getInstance().deleteActividad(idActividad);
	if(resultado) {
		return Response.status(201).build();
	} 
	else {
		throw  new Mensajes(3,idActividad);
		/// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste

	}	
	}
	

	
	
	//  //
	//  //    	AGREGAR LAS FUNCIONES PEDIDAS
	//  //
	//  //
	
	@GET
	@Secured
	@Path("/get_actividades_usuario_superpuestas")
	@Produces(MediaType.APPLICATION_JSON)
	// es la que trae Actividades si estan Superpuestas con la fecha de inicio y fin de una nueva 
	public List<Actividad> getActividadesSuperpuestasUsuario(@QueryParam("idUsuario") String idU, @QueryParam("idActividad") String idA){
		int idActividad = Integer.valueOf(idA);
		int idUsuario = Integer.valueOf(idU);
		Actividad nuevaActividad = DAOActividad.getInstance().getActividad(idActividad);
		Usuario usuario = DAOUsuario.getInstance().getUsuario(idUsuario);
		if(nuevaActividad!=null && usuario!=null) {
			List<Actividad> actividadesSuperpuestas = DAOActividad.getInstance().getActividadesSuperpuestasUsuario(usuario.getId(), nuevaActividad.getId());
			if(actividadesSuperpuestas!=null) {
				return actividadesSuperpuestas;
			}		
		}
		else {
			if (nuevaActividad!=null ){
				new Mensajes(3,idActividad);
			}
			else{ 
			    new Mensajes(3,idActividad);
			  }
		}
		return null;
		
		/// 1 RecursoNoCreado , 2 RecursoNoEncontrado , 3 RecursoNoExiste

		
	}
	
	
	@GET
	@Secured
	@Path("/get_actividades_usuario_fecha")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Actividad> getActividadesUsuarioXFecha(@QueryParam("idUsuario") int idUsuario, @QueryParam("fecha") String fecha) {
		
		try {  /// tuve que meter un try&catch por ParseException
		Date nfecha = formatoFecha.parse(fecha); // con esto le asignamos e; formato a la fecha que viene como string
		Usuario usuario = DAOUsuario.getInstance().getUsuario(idUsuario);
		List<Actividad> actividades = DAOActividad.getInstance().getActividadDeUsuarioxFecha(usuario.getId(), nfecha);
		if(actividades!=null)	
			return actividades;
			
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


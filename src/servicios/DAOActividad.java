package servicios;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;

import entidades.Actividad;
import entidades.Calendario;
import entidades.Sala;
import entidades.Usuario;

import servicios.EMF;


public class DAOActividad {
	
	private static DAOActividad daoactividad;

	private DAOActividad(){}

	public static DAOActividad getInstance() {
		if(daoactividad==null)
			daoactividad=new DAOActividad();
		return daoactividad;
	}

	public Actividad crearActividad(String nombre,int idCalendario, int usuario ,Date fechaInicio, Date fechafin ,Sala sala ) {
		EntityManager em=EMF.createEntityManager();	
		em.getTransaction().begin();
		Actividad na1 = null; 
		/// Busqueda via ID de Usuario,Sala y Calendario
		Usuario u= DAOUsuario.getInstance().getUsuario(usuario);
		Sala s= DAOSala.getInstance().getSala(sala.getId());
		Calendario c =DAOCalendario.getInstance().getCalendario(idCalendario);
		na1 = new Actividad(nombre,u,fechaInicio,fechafin,c);
		na1.setLugar(s);
		em.persist(na1);
		em.getTransaction().commit();
		em.close();
		return na1;
	}
		
	public List<Actividad> getActividades() {
		EntityManager em=EMF.createEntityManager();	

		String jpql = "Select a From Actividad a";
		Query query = em.createQuery(jpql); 
		List<Actividad> resultados = query.getResultList(); 
		return resultados;
	}
	
	public  Actividad getActividad(int idActividad) {
		EntityManager em=EMF.createEntityManager();
		String jpql = "Select u From Actividad u where u.id =?1";
		Query query = em.createQuery(jpql); 
		query.setParameter(1, idActividad);
		em.close();
		return (Actividad) query.getSingleResult();
	}	
	
		
	public List<Actividad> getActividadesUsuario(int usuario) {
		EntityManager em=EMF.createEntityManager();	
		String jpql = "SELECT a FROM Actividad a,actividad_usuario au WHERE ((au.actividad_id = a.id)" // 
		+ "AND (au.invitados_idUsuario =1?))"  // sea un invitado
		+ "OR (a.duenio_idUsuario=1?)"; // sea duenio
		Query query = em.createQuery(jpql); 
		query.setParameter(1, usuario);
		List<Actividad> resultados = query.getResultList(); 
		return resultados;
	}	
	
	/// Referentes a Sobreposicion
	
	public List<Actividad> getActividadesSobrepuestasUsuario(int usuario, int actividad) {
		EntityManager em=EMF.createEntityManager();	
		String jpql = "SELECT a1 FROM Actividad a1 , Actividad a2 "
				+ "WHERE a.duenio_idUsuario = ?1"
				+ " AND a1.id != ?2" /// Sean distintas actividades
				+ " AND a2.id = ?2" /// la a con a2
				+ " AND (a1.fechaInicio <= a2.fechaFin" + " AND a2.fechaInicio < a1.fechaFin"
				+ " OR a1.fechaInicio <= a2.fechaFin" + " AND a2.fechaInicio <= a1.fechaInicio)";
		Query query = em.createQuery(jpql); 
		query.setParameter(1, usuario);
		query.setParameter(2, actividad);
		List<Actividad> resultados = query.getResultList(); 
		return resultados;
		}
	
	/// TIEMPO LIBRE PARA AGREGAR UNA NUEVA ACTIVIDAD
		public  boolean tiempoLibreUsuario(int usuario, Date fechaInicio, Date fechafin) {
			// Optimizar para los usuarios invitados
			EntityManager em=EMF.createEntityManager();	
			String jpql = "SELECT a1 FROM Actividad a1"
					+ "WHERE a.duenio_idUsuario = ?1"
					+ " AND (a1.fechaInicio < ?2" + " AND ?2 < a1.fechaFin"
					+ " OR a1.fechaInicio <= ?3" + " AND ?3 <= a1.fechaInicio)";
			Query query = em.createQuery(jpql); 
			query.setParameter(1, usuario);
			query.setParameter(2, fechaInicio);
			query.setParameter(3, fechafin);
			List<Actividad> resultados = query.getResultList();
			em.close();
			return (resultados == null);
		}

	/// ACTIVIDADES DE UN USUARIO EN UNA FECHA DADA

	public  List<Actividad> getActividadDeUsuarioxFecha(int idUsuario,Date fecha) {
		EntityManager em=EMF.createEntityManager();
		Calendar calendario = Calendar.getInstance(); /// https://stackoverflow.com/questions/2619691/extract-day-from-date
		calendario.setTime(fecha);
		String jpql = "Select a From Actividad a where (a.duenio.id=?1) and ("
			// en base a la fecha de inicio
			+ "(extract(day from a.fechaInicio )= ?2"
			+ " and extract(month from a.fechaInicio) = ?3"
			+ " and extract(year from a.fechaInicio) = ?4) OR "
			// en base a la fecha de fin
			+ "(extract(day from a.fechaFin )= ?2" 
			+ " and extract(month from a.fechaFin) = ?3"
			+ " and extract(year from a.fechaFin) = ?4))"; 
		Query query = em.createQuery(jpql);
		query.setParameter(1, idUsuario);
		query.setParameter(2, calendario.get(Calendar.DAY_OF_MONTH));
		query.setParameter(3, calendario.get(Calendar.MONTH) + 1);
		query.setParameter(4, calendario.get(Calendar.YEAR));
		List<Actividad> resultados = query.getResultList();
		em.close();
		return resultados;
	}
	
	/// ACTIVIDADES DE UN USUARIO ENTRE 2 FECHAS DADAS

	public  List<Actividad> getActividadDeUsuarioEntreDias(int idUsuario, Date fecha1, Date fecha2) {
		EntityManager em=EMF.createEntityManager();
		String jpql = "SELECT a FROM Actividad a WHERE (a.duenio.id=?1) AND a.fechaInicio >= ?2  AND a.fechaFin <= ?3 "; 
		Query query = em.createQuery(jpql);
		query.setParameter(1, idUsuario);
		query.setParameter(2, fecha1);
		query.setParameter(3, fecha2);
		List<Actividad> resultados = query.getResultList();
		em.close();	
		return resultados;
	}

	/// UPDATE AND DELETE 

		public  Actividad updateActividad(int id,String nombre,int idCalendario, int idusuario ,Date fechaInicio, Date fechafin ,Sala sala ) {
			Actividad act = getActividad(id);
			EntityManager em=EMF.createEntityManager();
			em.getTransaction().begin();
			Calendario calendario = DAOCalendario.getInstance().getCalendario(idCalendario);
			Sala sala1 = DAOSala.getInstance().getSala(sala.getId());
			Usuario duenio = DAOUsuario.getInstance().getUsuario(idusuario);
			act.setCalendario(calendario);
			act.setDuenio(duenio);
			act.setFechaInicio(fechaInicio);
			act.setFechaFin(fechafin);
			act.setLugar(sala1);
			em.persist(act);
			em.getTransaction().commit();
			em.close();
	        //Actividad nw = getActividad(act.getId());
	        return act;

		}
		
		public  boolean deleteActividad(Integer idActividad) {
			EntityManager em=EMF.createEntityManager();
			em.getTransaction().begin();
			String jpql = "DELETE FROM Actividad a WHERE a.id = ?1"; 
	        Query query = em.createQuery(jpql);
	        query.setParameter(1, idActividad);
	        query.executeUpdate();
	        em.getTransaction().commit();
	        em.close();
	        Actividad actividad = getActividad(idActividad);
			if (actividad == null) {
				return  true;
			}	
			return false;
	       
		}

		
}
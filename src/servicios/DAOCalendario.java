package servicios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Calendario;
import entidades.Usuario;
import servicios.EMF;

public class DAOCalendario {

	private static DAOCalendario daocalendario;
	
	private DAOCalendario(){}

	public static DAOCalendario getInstance() {
		if(daocalendario==null)
			daocalendario=new DAOCalendario();
		return daocalendario;
	}


	public List<Calendario> getCalendarios() {
			EntityManager em=EMF.createEntityManager();
			String jpql = "SELECT c FROM Calendario c"; 
		    Query query = em.createQuery(jpql); 
		    List<Calendario> resultados = query.getResultList();
		    em.close();
		    return resultados;
		    
		}

	public  Calendario crearCalendario(String nombre, Usuario usuario) {
		EntityManager em=EMF.createEntityManager();
		em.getTransaction().begin();
		Calendario nc = new Calendario(nombre, usuario);
		em.persist(nc);
		em.getTransaction().commit();
		em.close();
		return nc;
	}
	
	public  Calendario getCalendario(int idCalendario) {
		EntityManager em=EMF.createEntityManager();
		String jpql = "Select c From Calendario c where c.id =?1";
		Query query = em.createQuery(jpql); 
		query.setParameter(1, idCalendario);
		Calendario calen = (Calendario) query.getSingleResult();
		//System.out.println(calen.toString());
		return calen;
		

}

	public  List<Calendario> getCalendariosXusuario(int idUsuario) {
		EntityManager em=EMF.createEntityManager();
		String jpql = "Select c From Calendario c where (c.duenio_idUsuario = ?1) ";
		Query query = em.createQuery(jpql);
		query.setParameter(1, idUsuario);
		List<Calendario> resultados = query.getResultList(); 
	    return resultados;
	}
	
	
	///UPDATE ADN DELETE

		public  Calendario updateCalendario(int idCalendario,String nombre, int duenio) {
			Usuario usuario = DAOUsuario.getInstance().getUsuario(duenio);
			EntityManager em=EMF.createEntityManager();
			em.getTransaction().begin();		
			String jpql = "UPDATE Calendario c SET c.nombre=?2, c.duenio_idUsuario=?3 WHERE c.id = ?1"; 
	        Query query = em.createQuery(jpql);
	        query.setParameter(1, idCalendario);
	        query.setParameter(2, nombre);
	        query.setParameter(3, usuario.getId());
	        query.executeUpdate();
	        em.getTransaction().commit();
			em.close();
			Calendario calen = getCalendario(idCalendario);
	        return calen;

		}
		
		public  boolean deleteCalendario(Integer idCalendario) {
			EntityManager em=EMF.createEntityManager();
			em.getTransaction().begin();
			em.getTransaction().begin();
			String jpql = "DELETE FROM Calendario c WHERE c.id = ?1"; 
	        Query query = em.createQuery(jpql);
	        query.setParameter(1, idCalendario);
	        query.executeUpdate();
	        em.getTransaction().commit();
			em.close();
			Calendario calen = getCalendario(idCalendario);				
			if (calen == null) {
					return  true;
				}	
				return false;
	       
		}

	
	
	
	
	
}
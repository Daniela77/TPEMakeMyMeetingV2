package servicios;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
	private static EntityManagerFactory emf;


	public static EntityManager createEntityManager() {
		if (emf == null) {
			throw new IllegalStateException("Context is not initialized yet.");
		}
		return emf.createEntityManager();
	}


	public static void initFactory() {
		emf = Persistence.createEntityManagerFactory("my_persistence_unit");
	}


	public static void closeFactory() {
		emf.close();
		emf = null;	
	}
}
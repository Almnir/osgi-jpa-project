package testproject;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static final String PERSISTENCE_UNIT_NAME = "todo";
    private static EntityManagerFactory factory;
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put(PersistenceUnitProperties.CLASSLOADER, Activator.class
		.getClassLoader());
		factory = new PersistenceProvider().createEntityManagerFactory(PERSISTENCE_UNIT_NAME, props);
	    EntityManager em = factory.createEntityManager();
	    // read the existing entries and write to console
	    Query q = em.createQuery("select t from Todo t");
	    List<Todo> todoList = q.getResultList();
	    for (Todo todo : todoList) {
	      System.out.println(todo);
	    }
	    System.out.println("Size: " + todoList.size());

	    // create new todo
	    em.getTransaction().begin();
	    Todo todo = new Todo();
	    todo.setSummary("This is a test");
	    todo.setDescription("This is a test");
	    em.persist(todo);
	    em.getTransaction().commit();

	    em.close();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
	}

}

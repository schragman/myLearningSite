package beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.HauptThema;
import entities.TestEntry;

public class AusgabeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "LearningPU")
	private EntityManager em;

	public List<HauptThema> findThemes() {
		TypedQuery<HauptThema> query = em.createNamedQuery("findAllThemes", HauptThema.class);
		List<HauptThema> resultList = query.getResultList();
		return resultList;
	}

	public TestEntry findTest() {

		return em.find(TestEntry.class, 2);
	}

}

package beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.HauptThema;

public class AusgabeTest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String writeTest;

	@PersistenceContext(unitName = "LearningPU")
	private EntityManager em;

	@PostConstruct
	public void init() {
		writeTest = "Test von der JAR-Bean";
	}

	public String getWriteTest() {
		return writeTest;
	}

	public void setWriteTest(String writeTest) {
		this.writeTest = writeTest;
	}

	public List<HauptThema> findThemes() {
		TypedQuery<HauptThema> query = em.createNamedQuery("findAllThemes", HauptThema.class);
		List<HauptThema> resultList = query.getResultList();
		return resultList;
	}

}

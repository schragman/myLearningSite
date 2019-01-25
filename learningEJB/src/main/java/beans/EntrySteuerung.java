package beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.HauptThema;
import entities.MainEntry;

@Stateless
@LocalBean
public class EntrySteuerung implements EntrySteuerungRemote {

	@PersistenceContext(unitName = "LearningPU")
	private EntityManager em;

	@Override
	public void generateNew(MainEntry mainEntry, HauptThema theme) {
		HauptThema attachedTheme = em.merge(theme);
		attachedTheme.getMainEntries().add(mainEntry);
		em.persist(attachedTheme);
	}

	@Override
	public List<MainEntry> findEntries(HauptThema thema) {
		Query query = em.createNamedQuery("findEntries", MainEntry.class);
		query.setParameter("passedID", thema.getId());

		return query.getResultList();

	}

}

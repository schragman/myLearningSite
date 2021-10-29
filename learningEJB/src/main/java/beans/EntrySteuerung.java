package beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.HauptThema;
import entities.MainEntry;
import entities.OIdSortable;
import entities.Referenz;

@Stateless
@LocalBean
public class EntrySteuerung implements EntrySteuerungRemote {

	@PersistenceContext(unitName = "LearningPU")
	private EntityManager em;

	@Override
	public MainEntry generateNew(MainEntry mainEntry) {
		//HauptThema attachedTheme = em.merge(theme);
		//attachedTheme.getMainEntries().add(mainEntry);
		//em.persist(attachedTheme);
		MainEntry newEntry = em.merge(mainEntry);
		newEntry.setOrderId(newEntry.getHauptThema().getId() + "_" + newEntry.getId());
		return em.merge(newEntry);
	}

	@Override
	public void updEntry(MainEntry mainEntry) {
		em.merge(mainEntry);
	}

	@Override
	public List<MainEntry> findEntries(HauptThema thema) {
		Query query = em.createNamedQuery("findEntriesOrderedByOrderId", MainEntry.class);
		query.setParameter("passedTheme", thema);

		return query.getResultList();
	}

	@Override
	public List<Referenz> findReferences() {
		Query query = em.createNamedQuery("findAllReferences", Referenz.class);

		return query.getResultList();
	}

	@Override
	public void deleteEntry(MainEntry mainEntry) {
		MainEntry toDelete = em.merge(mainEntry);
		em.remove(toDelete);
	}

	@Override
//	public List<String> findBeschreibungen(HauptThema thema) {
//		Query query = em.createNamedQuery("findEntries", MainEntry.class);
//		query.setParameter("passedID", thema.getId());
//
//		List<MainEntry> lvResultList = query.getResultList();
//		List<String> lvResult = new ArrayList<>();
//
//		for (MainEntry me : lvResultList) {
//			if (null != me)
//				lvResult.add(me.getKurzEintrag());
//		}
//
//		return lvResult;
//	}

	public List<String> findBeschreibungen(HauptThema thema) {
		Query query = em.createNamedQuery("findKBeschreibungen", String.class);
		query.setParameter("passedTheme", thema);

		return query.getResultList();
	}

	public MainEntry findById(long id) {
		return em.find(MainEntry.class, id);
	}

	public List<MainEntry> getEntryList(HauptThema theme) {
		Query query = em.createNamedQuery("findEntriesOrderedById", MainEntry.class);
		query.setParameter("passedTheme", theme);
		return query.getResultList();
	}

	public void updateSortedEntries(List<? extends OIdSortable> entries) {
		for (OIdSortable entry : entries) {
			MainEntry mainEntry = (MainEntry) entry;
			MainEntry toUpdate = em.merge(mainEntry);
		}
	}

}

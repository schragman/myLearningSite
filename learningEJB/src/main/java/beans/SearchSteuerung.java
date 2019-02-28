package beans;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.HauptThema;
import entities.MainEntry;
import entities.Referenz;

@Stateless
@LocalBean
public class SearchSteuerung implements SearchSteuerungRemote {

	@PersistenceContext(unitName = "LearningPU")
	private EntityManager em;

	@Override
	public Collection<String> findSearchEntries(HauptThema theme) {

		Set<String> result = new HashSet<>();
		Query quRef = em.createNamedQuery("findReferences", Referenz.class);
		quRef.setParameter("passedTheme", theme);
		Query quBesch = em.createNamedQuery("findEntries", MainEntry.class);
		quBesch.setParameter("passedTheme", theme);

		List<Referenz> refList = quRef.getResultList();
		List<MainEntry> beschList = quBesch.getResultList();

		for (Referenz referenz : refList) {
			result.add(referenz.getuRefferenz1());
			result.add(referenz.getuRefferenz2());
		}

		for (MainEntry mEntry : beschList) {
			result.add(mEntry.getKurzEintrag());
			result.add(mEntry.getLangEintrag());
		}

		return result;
	}

}

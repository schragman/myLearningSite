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
import utils.SearchContainer;
import utils.SearchItems;

@Stateless
@LocalBean
public class SearchSteuerung implements SearchSteuerungRemote {

	@PersistenceContext(unitName = "LearningPU")
	private EntityManager em;

	// Fuer die Vorschlagsliste im Suchfeld
	@Override
	public Collection<SearchContainer> findSearchEntries(HauptThema theme) {

		Set<SearchContainer> result = new HashSet<>();
		Query quRef = em.createNamedQuery("findReferences", Referenz.class);
		quRef.setParameter("passedTheme", theme);
		Query quBesch = em.createNamedQuery("findEntries", MainEntry.class);
		quBesch.setParameter("passedID", theme.getId());

		List<Referenz> refList = quRef.getResultList();
		List<MainEntry> beschList = quBesch.getResultList();

		for (Referenz referenz : refList) {
			if (null!=referenz) {
				SearchContainer sContainer = new SearchContainer(referenz.getuRefferenz1(), SearchItems.REFERENZ, referenz.getId());
				result.add(sContainer);
				sContainer = new SearchContainer(referenz.getuRefferenz2(), SearchItems.UNTERREFERENZ, referenz.getId());
				result.add(sContainer);
			}
		}

		for (MainEntry mEntry : beschList) {
			if (null!=mEntry) {
        SearchContainer sContainer = new SearchContainer(mEntry.getKurzEintrag(), SearchItems.KURZEINTRAG, mEntry.getId());
        result.add(sContainer);
        sContainer = new SearchContainer(mEntry.getLangEintrag(), SearchItems.LANGEINTRAG, mEntry.getId());
        result.add(sContainer);
      }
		}

		return result;
	}
	
	//Die Suchergebnisse
	//public Collection<MainEntry> findFinalSearchResults(HauptThema theme, String SearchItem) {
		
		
	//}

}

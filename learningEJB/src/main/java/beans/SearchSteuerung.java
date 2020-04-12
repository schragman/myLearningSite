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
import utils.*;

@Stateless
@LocalBean
public class SearchSteuerung implements SearchSteuerungRemote {

  @PersistenceContext(unitName = "LearningPU")
  private EntityManager em;

  // Fuer die Vorschlagsliste im Suchfeld
  @Override
  public Collection<SearchContainer> findSearchEntries(HauptThema theme) {

    Set<SearchContainer> result = new HashSet<>();

    return generateSearchEntries(result, theme, (a,b,c) -> new SearchContVorschlag(a, b, c));

  }

  // Feur das Suchfenster
  // Wird nicht mehr gebraucht, aber schönes Beispiel mit den Lambda-Funktionen
  /*@Override
  public Collection<SearchContainer> findSearchList(HauptThema theme) {

    Set<SearchContainer> result = new HashSet<>();

    return generateSearchEntries(result, theme, (a,b,c) -> new SearchContList(a, b, c));

  }*/


  private Collection<SearchContainer> generateSearchEntries(Collection<SearchContainer> container,
                                                            HauptThema theme,
                                                            SearchContainerFactory searchContainerFactory) {
    Query quRef = em.createNamedQuery("findReferences", Referenz.class);
    quRef.setParameter("passedTheme", theme);
    Query quBesch = em.createNamedQuery("findEntries", MainEntry.class);
    quBesch.setParameter("passedID", theme.getId());

    List<Referenz> refList = quRef.getResultList();
    List<MainEntry> beschList = quBesch.getResultList();

    for (Referenz referenz : refList) {
      if (null != referenz) {
        SearchContainer sContainer = searchContainerFactory.getSearchContainer(referenz.getuRefferenz1(), SearchItems.REFERENZ, getMainId(referenz));
        container.add(sContainer);
        String uRef2 = referenz.getuRefferenz2();
        if (null != uRef2 && !uRef2.isEmpty()) {
          sContainer = searchContainerFactory.getSearchContainer(uRef2, SearchItems.UNTERREFERENZ, getMainId(referenz));
          container.add(sContainer);
        }
      }
    }

    for (MainEntry mEntry : beschList) {
      if (null != mEntry) {
        SearchContainer sContainer = searchContainerFactory.getSearchContainer(mEntry.getKurzEintrag(), SearchItems.KURZEINTRAG, mEntry.getId());
        container.add(sContainer);
        String lEintrag = mEntry.getLangEintrag();
        if (null != lEintrag && !lEintrag.isEmpty()) {
          sContainer = searchContainerFactory.getSearchContainer(mEntry.getLangEintrag(), SearchItems.LANGEINTRAG, mEntry.getId());
          container.add(sContainer);
        }
      }
    }

    return container;


  }

  private long getMainId(Referenz referenz) {

    Query quMainId = em.createNamedQuery("findMainEntryFromRef", Long.class);
    quMainId.setParameter("passedReferenz", referenz);

    /*List<Long> results = quMainId.getResultList();
    long result = results.get(0);*/

    Long result = (Long) quMainId.getSingleResult();

    return result;

  }


  //Die Suchergebnisse
  //public Collection<MainEntry> findFinalSearchResults(HauptThema theme, String SearchItem) {


  //}

}

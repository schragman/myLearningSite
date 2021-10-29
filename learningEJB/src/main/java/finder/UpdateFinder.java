package finder;

import entities.HauptThema;
import entities.MainEntry;
import entities.Referenz;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/*@NamedQueries({
    @NamedQuery(name = "findReallyAllThemes", query = "SELECT t FROM HauptThema t"),
    @NamedQuery(name = "findEntriesByTheme", query = "SELECT m FROM MainEntry m WHERE m.hauptThema = :passedTheme")
})*/

public class UpdateFinder {

  @PersistenceContext(unitName = "LearningPU")
  private EntityManager em;

  public List<HauptThema> findAllThemes() {
    TypedQuery<HauptThema> query = em.createNamedQuery("findReallyAllThemes", HauptThema.class);
    return query.getResultList();
  }

  public List<MainEntry> findEntriesByTheme(HauptThema thema) {
    TypedQuery<MainEntry> query = em.createNamedQuery("findEntriesByTheme", MainEntry.class);
    query.setParameter("passedTheme", thema);

    return query.getResultList();
  }

  public void updateMainEntry(MainEntry mainEntry) {
    MainEntry toUpdate = em.merge(mainEntry);
  }
}

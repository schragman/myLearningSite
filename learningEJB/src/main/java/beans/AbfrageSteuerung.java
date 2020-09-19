package beans;


import entities.Abfrage;
import entities.HauptThema;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@LocalBean
@Stateless
public class AbfrageSteuerung {

  @PersistenceContext(unitName = "LearningPU")
  private EntityManager em;

  public List<Abfrage> getFragen(HauptThema thema) {
    Query query = em.createNamedQuery("findAbfragen", Abfrage.class);
    query.setParameter("passedTheme", thema);

    return query.getResultList();
  }

  public void updAbfrage(Abfrage abfrage) {
    em.merge(abfrage);
  }

}

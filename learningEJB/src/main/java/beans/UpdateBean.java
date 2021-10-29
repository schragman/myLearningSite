package beans;

import entities.HauptThema;
import entities.MainEntry;
import finder.UpdateFinder;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UpdateBean {

  @Inject
  private UpdateFinder updateFinder;

  public void updateMainEntryTable() {
    List<HauptThema> themes = updateFinder.findAllThemes();

    for (HauptThema theme : themes) {
      List<MainEntry> entries = updateFinder.findEntriesByTheme(theme);
      for (MainEntry entry : entries) {
        entry.setOrderId(theme.getId() + "_" + entry.getId());
        updateFinder.updateMainEntry(entry);
      }
    }

  }

}

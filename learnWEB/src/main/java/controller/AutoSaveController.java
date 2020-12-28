package controller;

import beans.EntrySteuerung;
import entities.HauptThema;
import entities.MainEntry;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;

@ApplicationScoped
public class AutoSaveController {

  private static Logger LOG = LoggerFactory.getLogger(AutoSaveController.class);

  private HashMap<Long, SessionEntry> mainEntryList;
  private String validSessionId;

  @EJB
  EntrySteuerung entrySteuerung;

  @PostConstruct
  public void init() {
    mainEntryList = new HashMap<>();
  }

  public void autosave(MainEntry mainEntry, HauptThema thema, String sessionId) {
    if (sessionId.equals(validSessionId)) { // Autosave geht nur mit der aktuellen Session !!!!
      //Im Kurzeintrag muss etwas stehen, sonst kein Autosave
      if (mainEntry.getId() == 0) {
        // Neuer Eintrag
        MainEntry generatedEntry = entrySteuerung.generateNew(mainEntry, thema);
        // Für cancel muss Beschreibung und Beispiel leer sein
        SessionEntry sessionEntry = new SessionEntry(null, sessionId,
            LocalDateTime.now(ZoneId.of("GMT+01")));
        mainEntryList.put(generatedEntry.getId(), sessionEntry);
      } else if (mainEntryList.containsKey(mainEntry.getId())) {
        if (!mainEntryList.get(mainEntry.getId()).getSession().equals(sessionId)) {
          LOG.info("Eintrag einer anderen Session wird überschrieben");
        }
        entrySteuerung.updEntry(mainEntry);
      } else {
        // Erstes mal Autosave
        MainEntry formerEntry = entrySteuerung.findById(mainEntry.getId());
        SessionEntry formerEntryWithSession = new SessionEntry(formerEntry, sessionId,
            LocalDateTime.now(ZoneId.of("GMT+01")));
        mainEntryList.put(mainEntry.getId(), formerEntryWithSession);
        entrySteuerung.updEntry(mainEntry);
      }
    }
  }

  // Wenn auf Cancel geklickt wird.
  public void cancelAutosave(MainEntry mainEntry) {
    // Erstmal prüfen, ob ein Autosave überhaupt schon stattgefunden hat
    if (mainEntryList.containsKey(mainEntry.getId())) {
      MainEntry oldEntry = mainEntryList.get(mainEntry.getId()).getMainEntry();
      if (null == oldEntry) {
        entrySteuerung.deleteEntry(mainEntry);
      } else {
        entrySteuerung.updEntry(oldEntry);
      }
      mainEntryList.remove(mainEntry.getId());
    }
  }

  // Beim regelgerechten Speichern muss der Autosave-Eintrag gelöscht werden
  public void saveAutosave(MainEntry mainEntry) {
    // Erstmal prüfen, ob ein Autosave überhaupt schon stattgefunden hat
    if (mainEntryList.containsKey(mainEntry.getId())) {
      mainEntryList.remove(mainEntry.getId());
    }
  }


  public void setValidSessionId(String validSessionId) {
    this.validSessionId = validSessionId;
  }

  public boolean isEntryAutosaved(MainEntry mainEntry) {
    return mainEntryList.containsKey(mainEntry.getId());
  }

  public LocalDateTime getAutosavedDate(MainEntry mainEntry) {
    return mainEntryList.get(mainEntry.getId()).getDate();
  }

  private class SessionEntry {
    private MainEntry mainEntry;
    private String session;
    private LocalDateTime date;

    public SessionEntry(MainEntry mainEntry, String session, LocalDateTime date) {
      this.mainEntry = mainEntry;
      this.session = session;
      this.date = date;
    }

    public MainEntry getMainEntry() {
      return mainEntry;
    }

    public String getSession() {
      return session;
    }

    public LocalDateTime getDate() {
      return date;
    }
  }

}

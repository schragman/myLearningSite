package controller;

import beans.EntrySteuerung;
import entities.HauptThema;
import entities.MainEntry;
import javafx.util.Pair;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;

@ApplicationScoped
public class AutoSaveController {

  private HashMap<Long, SessionEntry> mainEntryList;

  @EJB
  EntrySteuerung entrySteuerung;

  public void autosave(MainEntry mainEntry, HauptThema thema, String sessionId) {
    if (mainEntry.getId() == 0) {
      // Neuer Eintrag
      MainEntry generatedEntry = entrySteuerung.generateNew(mainEntry, thema);
      // Für cancel muss Beschreibung und Beispiel leer sein
      SessionEntry sessionEntry = new SessionEntry(null, sessionId);
      mainEntryList.put(generatedEntry.getId(), sessionEntry);
    } else if (mainEntryList.containsKey(mainEntry.getId())) {
      if (mainEntryList.get(mainEntry.getId()).getSession().equals(sessionId)) {
        // Autosave aktualisieren
      } else {
        // Fehlerbehandlung: Jemand anders bearbeitet diesen Entry !!!
      }
    } else {
      // Erstes mal Autosave
      MainEntry formerEntry = entrySteuerung.findById(mainEntry.getId());
      SessionEntry formerEntryWithSession = new SessionEntry(formerEntry, sessionId);
      mainEntryList.put(mainEntry.getId(), formerEntryWithSession);
      entrySteuerung.updEntry(mainEntry);
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
    }
  }

  // Beim regelgerechten Speichern muss der Autosave-Eintrag gelöscht werden
  public void saveAutosave(MainEntry mainEntry) {
    // Erstmal prüfen, ob ein Autosave überhaupt schon stattgefunden hat
    if (mainEntryList.containsKey(mainEntry.getId())) {
      mainEntryList.remove(mainEntry.getId());
    }
  }

  private class SessionEntry {
    private MainEntry mainEntry;
    private String session;

    public SessionEntry(MainEntry mainEntry, String session) {
      this.mainEntry = mainEntry;
      this.session = session;
    }

    public MainEntry getMainEntry() {
      return mainEntry;
    }

    public String getSession() {
      return session;
    }
  }

}

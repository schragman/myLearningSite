package controller;

import java.io.IOException;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

import beans.EntrySteuerungRemote;
import entities.Abfrage;
import entities.Antwort;
import entities.MainEntry;
import entities.Referenz;
import org.apache.myfaces.tobago.component.UIPopup;
import util.LoginController;
import util.Selections;
import util.Sites;

/**
 * @Version 1.3
 */

@ViewScoped
@ManagedBean
public class MainEntryForm implements Serializable {

	private static final long serialVersionUID = 13L;
	// Hat momentan keine Bedeutung, da der Timer direkt über das Script Autosave gesetzt wird.
	private static final int AUTOSAVEPERIOD = 600000; // Nach wieviel Millisekunden ein Autosave passiert

	// Nach wieviel Autosaves das Warnfenster für Session-Timeout erscheint
	private static final int WARNINGWINDOWTIME = 2;

	// Sekunden bei dem der Session-Timeout Timer anfängt herunterzuzählen
	private static final int TIMEOUTAFTERWARNING = 10;

	@EJB
	private EntrySteuerungRemote entrySteuerung;
	@Inject
	private Selections selection;
	@Inject
	private MenuForm menuForm;
	@Inject
	private LoginController loginController;
	@Inject
  private AutoSaveController autoSaveController;
	private String kBeschreibung;
	private String lBeschreibung;
	private boolean referenzArt; // Printmedium, Internetquelle
	private String referenz;
	private String labelReferenz;
	private String uReferenz;
	private String labelUReferenz;
	private String frage;
	private String antwort;
	private String beispiel;
	private List<Referenz> referenzen;
	private List<Referenz> alleReferenzen; // Für Vorschlagsliste
	private List<String> aBeschreibungen; // Für Vorschlagsliste
	private List<MainEntry> navigationList; // Zum Navigieren
	private ListIterator<MainEntry> entryIterator;
	private MainEntry precedor; // Vorgaenger
	private MainEntry mainEntry;

	private List<Abfrage> abfragen;
	private boolean neu; // für Buttons
	private boolean bearbeiten; // für Buttons

	private boolean anzeigeSessionTimeoutCounter;
	private int sessionTimeoutCounter;
	private boolean sessionGone;
	private int autosaveFrequency;
	//Nach wieviel Durchläufen Autosave wird der SessionTimeout-Counter angezeigt.
	private int warningWindowCounter;

	private String userInput;
	private List<Referenz> quRes;

	private UIPopup sessionTOPopup;

	public String getUserInput() {
		return userInput;
	}

	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}

	@PostConstruct
	public void init() {

		warningWindowCounter = WARNINGWINDOWTIME;
		sessionTimeoutCounter = TIMEOUTAFTERWARNING;
		anzeigeSessionTimeoutCounter = false;
		sessionGone = false;
		autosaveFrequency = AUTOSAVEPERIOD;
		autoSaveController.setValidSessionId(selection.getSessionId());
		MainEntry entry = selection.getEntry();
		navigationList = entrySteuerung.getEntryList(selection.getThema());
		if (null != navigationList && entry != null) {
		  //int entryIndex = navigationList.indexOf(entry);
		  entryIterator = navigationList.listIterator();
			while (entry.getId() != entryIterator.next().getId()) {
			}
		}
		referenzArt = true;
		labelReferenz = "Buch";
		labelUReferenz = "Seite";
		quRes = entrySteuerung.findReferences();
		aBeschreibungen = entrySteuerung.findBeschreibungen(selection.getThema());
		bearbeiten = false;
		if (null == entry) {
			referenzen = new ArrayList<>();
			abfragen = new ArrayList<>();
			neu = true;
			mainEntry = new MainEntry();
		} else {
			kBeschreibung = entry.getKurzEintrag();
			lBeschreibung = entry.getLangEintrag();
			referenzen = entry.getReferenzen();
			beispiel = entry.getBeispiel();
			abfragen = entry.getAbfragen();
			neu = false;
			mainEntry = entry;
		}
	}


	public void addReference(ActionEvent ae) {
		if (!isNullOrEmpty(this.referenz) || !isNullOrEmpty(this.uReferenz)) {
			addToReference();
			referenzArt = true;
			referenz = "";
			uReferenz = "";
		}
	}

	public void addAbfrage(ActionEvent ae) {
		if (!isNullOrEmpty(this.frage) || !isNullOrEmpty(this.antwort)) {
			addToAbfrage();
			frage = "";
			antwort = "";
		}
	}

	public void removeReference(Referenz toDelRef) {
		if (referenzen.contains(toDelRef))
			referenzen.remove(toDelRef);
	}

	public void removeAbfrage(Abfrage toDelAbf) {
		if (abfragen.contains(toDelAbf))
			abfragen.remove(toDelAbf);
	}

	public String doCreateNewEntry() {
		this.refreshMainEntry();
    mainEntry.setHauptThema(selection.getThema());
    autoSaveController.saveAutosave(mainEntry);
		entrySteuerung.generateNew(mainEntry);

		return Sites.UEBERSICHT;
	}


	public String getConfirmUpdate() {
		String result;
		//this.refreshMainEntry();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
		if (autoSaveController.isEntryAutosaved(this.mainEntry)) {
			result = "Es gibt Autosave-Einträge ab dem "
					+ autoSaveController.getAutosavedDate(this.mainEntry).format(formatter)
					+ ". Mit dem Speichern werden die Autosave-Einträge übernommen. Der Zustand vor dem Autosave ist "
			    + "damit verloren!";
		} else {
			result = "Zustand speichern ?";
		}
		return result;
	}

	public String getConfirmCancel() {
		String result;
		//this.refreshMainEntry();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
		if (autoSaveController.isEntryAutosaved(this.mainEntry)) {
			result = "Der hier sichtbare Zustand beinhaltet Autosave-Einträge ab dem "
				+ autoSaveController.getAutosavedDate(this.mainEntry).format(formatter)	+ ". Cancel löscht "
				+ "auch alle Autosave-Einträge! Wenn Sie das nicht wollen oder unsicher sind, klicken Sie auf "
				+ "Abbrechen und speichern den Zustand zunächst. Damit werden alle Autosave-Einträge übernommen!";
		} else {
			result = "Änderungen zurücksetzen?";
		}
		return result;
	}

  public String doUpdateEntry() {
    this.refreshMainEntry();

    autoSaveController.saveAutosave(mainEntry);
    entrySteuerung.updEntry(mainEntry);

		return Sites.UEBERSICHT;
	}

	// Bei Cancel ohne Bearbeitungsmodus
	public String doReturn() {
		return Sites.UEBERSICHT;
	}

	public String doCancel() {
	  boolean deleted = autoSaveController.cancelAutosave(mainEntry);
		if (deleted) {
			selection.setEntry(null);
		}

	  return Sites.UEBERSICHT;
  }

	private void refreshMainEntry() {
		mainEntry.setKurzEintrag(kBeschreibung);
		mainEntry.setLangEintrag(lBeschreibung);
		this.addReference(null);
		this.addAbfrage(null);
		mainEntry.setReferenzen(referenzen);
		mainEntry.setBeispiel(beispiel);
		mainEntry.setAbfragen(abfragen);
  }

  private void addToReference() {
		Referenz neueReferenz = new Referenz();
		neueReferenz.setArt(referenzArt);
		neueReferenz.setuRefferenz1(referenz);
		neueReferenz.setuRefferenz2(uReferenz);
		referenzen.add(neueReferenz);
	}

	private void addToAbfrage() {
		Abfrage neueAbfrage = new Abfrage();
		neueAbfrage.setFrage(frage);
		Antwort einzelAntwort = new Antwort();
		einzelAntwort.setInhalt(antwort);
		List<Antwort> antwortListe = new ArrayList<>();
		antwortListe.add(einzelAntwort);
		neueAbfrage.setAntworten(antwortListe);
		neueAbfrage.setRepetitionCounter(1);
		neueAbfrage.setRepetitionRate(1);
		abfragen.add(neueAbfrage);
	}

	public void doCreateMenu() {
		menuForm.setSelectedPage(SelectedPage.MAINENTRY);
	}

	public void doEdit() {
		bearbeiten = true;
	}

	public boolean isNeu() {
		return neu;
	}

	public boolean isBearbeiten() {
		return bearbeiten;
	}

	public Collection<Referenz> getAlleReferenzen() {
		Set<Referenz> result = new HashSet<>();
		String validatedUserInput = null == userInput ? "" : userInput;
		for (Referenz ref : quRes) {
			if (ref.getuRefferenz1().toLowerCase().contains(validatedUserInput.toLowerCase()))
//			if (ref.getuRefferenz1().toLowerCase().contains(userInput.toLowerCase()))
				result.add(ref);
		}
		return result;
	}

	// Für Vorschlagsliste von Kurzbeschreibung
	public List<String> getAlleBeschreibungen() {
		List<String> result = new ArrayList<>();
		String userInput = null == kBeschreibung ? "" : kBeschreibung;
		for (String beschreibung : aBeschreibungen)
			if (beschreibung.toLowerCase().contains(userInput.toLowerCase()) && !result.contains(beschreibung))
				result.add(beschreibung);
		return result;
	}

	public void doCreaeteMenu() {
		menuForm.setSelectedPage(SelectedPage.MAINENTRY);
	}

	public boolean isForwardActiv() {
		return (entryIterator != null && entryIterator.hasNext());
	}

	public boolean isBackwardsActiv() {
		return (entryIterator != null && entryIterator.previousIndex() >0);
	}

	public String doMoveForward() {
		MainEntry auswahl = entryIterator.next();
		selection.setEntry(auswahl);
		return Sites.MAINENTRY + Sites.DORELOAD;
	}

  public String doMoveBackwards() {
		entryIterator.previous();
		MainEntry auswahl = entryIterator.previous();
		selection.setEntry(auswahl);
		return Sites.MAINENTRY + Sites.DORELOAD;
	}

	public void setAlleReferenzen(List<Referenz> alleReferenzen) {
		this.alleReferenzen = alleReferenzen;
	}

	public void setBearbeiten(boolean bearbeiten) {
		this.bearbeiten = bearbeiten;
	}

	public List<Referenz> getReferenzen() {
		return referenzen;
	}

	public void setReferenzen(List<Referenz> referenzen) {
		this.referenzen = referenzen;
	}

	public boolean isRefListFilled() {
		return !referenzen.isEmpty();
	}

	public boolean isCheckListFilled() {
		return !abfragen.isEmpty();
	}

	public List<Abfrage> getAbfragen() {
		return abfragen;
	}

	public void setAbfragen(List<Abfrage> fragen) {
		this.abfragen = fragen;
	}

	public String getBeispiel() {
		return beispiel;
	}

	public void setBeispiel(String beispiel) {
		this.beispiel = beispiel;
	}

	public String getLabelReferenz() {
		return labelReferenz;
	}

	public void setLabelReferenz(String labelReferenz) {
		this.labelReferenz = labelReferenz;
	}

	public String getLabelUReferenz() {
		return labelUReferenz;
	}

	public void setLabelUReferenz(String labelUReferenz) {
		this.labelUReferenz = labelUReferenz;
	}

	public String getSelectedElement() {
		return referenzArt ? "book" : "internet";
	}

	public void setSelectedElement(String selectedReference) {
		referenzArt = "book".equalsIgnoreCase(selectedReference);
		if (referenzArt) {
			labelReferenz = "Buch";
			labelUReferenz = "Seite";
		} else {
			labelReferenz = "URL";
			labelUReferenz = "Beschreibung";
		}
	}

	public String getkBeschreibung() {
		return kBeschreibung;
	}

	public void setkBeschreibung(String kBeschreibung) {
		this.kBeschreibung = kBeschreibung;
	}

	public String getlBeschreibung() {
		return lBeschreibung;
	}

	public void setlBeschreibung(String lBeschreibung) {
		this.lBeschreibung = lBeschreibung;
	}

	public boolean isReferenzArt() {
		return referenzArt;
	}

	public void setReferenzArt(boolean referenzArt) {
		this.referenzArt = referenzArt;
	}

	public String getReferenz() {
		return referenz;
	}

	public void setReferenz(String referenz) {
		this.referenz = referenz;
	}

	public String getuReferenz() {
		return uReferenz;
	}

	public void setuReferenz(String uReferenz) {
		this.uReferenz = uReferenz;
	}

	public String getFrage() {
		return frage;
	}

	public void setFrage(String frage) {
		this.frage = frage;
	}

	public String getAntwort() {
		return antwort;
	}

	public void setAntwort(String antwort) {
		this.antwort = antwort;
	}

	public void test() {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Das ist ein Test", "Detail");
		FacesContext.getCurrentInstance().addMessage("page", fm);
		throw new ValidationException("Mal sehen ob das klappt");
	}

  public void doExtendSession() {
	  this.warningWindowCounter = WARNINGWINDOWTIME;
	  autosaveFrequency = AUTOSAVEPERIOD;
	  anzeigeSessionTimeoutCounter = false;
		sessionTimeoutCounter = TIMEOUTAFTERWARNING;
  }

  public void doAutosave() {
		warningWindowCounter--;
		String sessionId = selection.getSessionId();
		this.refreshMainEntry();
		autoSaveController.autosave(mainEntry, selection.getThema(), sessionId);

    if (warningWindowCounter <= 0) {
      //Wenn Session Warncounter == 0, dann kommt der Counter zum Session-Timeout
        anzeigeSessionTimeoutCounter = true;
        sessionTOPopup.setCollapsed(false);
    }
  }

  public String getUpdateSessionAnzeige() {
	  sessionTimeoutCounter--;
	  try {
      if (sessionTimeoutCounter == 0) {
        sessionGone = true;
        anzeigeSessionTimeoutCounter = false;
      } else if (sessionTimeoutCounter < 0) {
        loginController.logout(false);
      }
      return "";
    } catch (IOException e) {
      return "somethings wrong with Autosave!";
    }
  }

  public String getRemainingSessionTime() {
    String lcResult;
    if (sessionTimeoutCounter > 1)
      lcResult = "Session Timeout in " + (sessionTimeoutCounter -1) +" Sekunden";
    else
      lcResult = "The Session is expired";
    return lcResult;
  }

  public UIPopup getSessionTOPopup() {
    return sessionTOPopup;
  }

  public void setSessionTOPopup(UIPopup sessionTOPopup) {
    this.sessionTOPopup = sessionTOPopup;
  }

  public boolean isAnzeigeSessionTimeoutCounter() {
    return anzeigeSessionTimeoutCounter;
  }

  public boolean isSessionGone() {
    return sessionGone;
  }

  public int getAutosaveFrequency() {
    return autosaveFrequency;
  }

  private boolean isNullOrEmpty(String testString) {
    boolean result = null == testString;
    result = result || testString.isEmpty();
    return result;
  }

	public int getWarningWindowCounter() {
		return warningWindowCounter;
	}

	public void setWarningWindowCounter(int warningWindowCounter) {
		this.warningWindowCounter = warningWindowCounter;
	}

}

package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

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
 * @Version 1.1
 */

@ViewScoped
@ManagedBean
public class MainEntryForm implements Serializable {

	private static final long serialVersionUID = 12L;
	private static final int SESSIONPERIOD = 10;
	private static final int WARNINGWINDOWTIME = 3;
	private static final int AUTOSAVEPERIOD = 10000;

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

	private List<Abfrage> abfragen;
	private boolean neu; // für Buttons
	private boolean bearbeiten; // für Buttons

	private boolean testboolean;
	private int testZahl;
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

	private int javaScriptCounter;

	@PostConstruct
	public void init() {

		javaScriptCounter = 3;
		testZahl = SESSIONPERIOD;
		testboolean = false;
		sessionTimeoutCounter = SESSIONPERIOD;
		anzeigeSessionTimeoutCounter = false;
		sessionGone = false;
		warningWindowCounter = WARNINGWINDOWTIME;
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
		} else {
			kBeschreibung = entry.getKurzEintrag();
			lBeschreibung = entry.getLangEintrag();
			referenzen = entry.getReferenzen();
			beispiel = entry.getBeispiel();
			abfragen = entry.getAbfragen();
			neu = false;
		}
	}

	public void addReference(ActionEvent ae) {
		addToReference();
		referenzArt = true;
		referenz = "";
		uReferenz = "";
	}

	public void addAbfrage(ActionEvent ae) {
		addToAbfrage();
		frage = "";
		antwort = "";
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
		MainEntry result = getMainEntry();
    result.setHauptThema(selection.getThema());
    autoSaveController.saveAutosave(result);
		entrySteuerung.generateNew(result, selection.getThema());

		return Sites.UEBERSICHT;
	}

	private MainEntry createNewEntry() {
    MainEntry result = new MainEntry();
    result.setKurzEintrag(kBeschreibung);
    result.setLangEintrag(lBeschreibung);
    if (!(referenz.isEmpty() && uReferenz.isEmpty())) {
      addToReference();
    }
    if (!(frage.isEmpty() && antwort.isEmpty())) {
      addToAbfrage();
    }
    result.setReferenzen(referenzen);
    result.setBeispiel(beispiel);
    result.setAbfragen(abfragen);
    result.setHauptThema(selection.getThema());
    return result;
  }

	public String getConfirmUpdate() {
		String result;
		if (autoSaveController.isEntryAutosaved(this.getMainEntry())) {
			result = "Es gibt Autosave-Einträge. Mit dem Speichern werden die Autosave-Einträge übernommen. Der Zustand vor dem Autosave ist damit verloren!";
		} else {
			result = "Zustand speichern ?";
		}
		return result;
	}

	public String getConfirmCancel() {
		String result;
		if (autoSaveController.isEntryAutosaved(this.getMainEntry())) {
			result = "Der hier sichtbare Zustand beinhaltet Autosave-Einträge. Cancel löscht auch alle Autosave-Einträge! "
				+"Wenn Sie das nicht wollen oder unsicher sind, klicken Sie auf Abbrechen und speichern den Zustand zunächst. "
			  +"Damit werden alle Autosave-Einträge übernommen!";
		} else {
			result = "Änderungen zurücksetzen?";
		}
		return result;
	}

  public String doUpdateEntry() {
    MainEntry result = getMainEntry();

    autoSaveController.saveAutosave(result);
    entrySteuerung.updEntry(result);

		return Sites.UEBERSICHT;
	}

	public String doCancel() {
	  MainEntry result = getMainEntry();
	  autoSaveController.cancelAutosave(result);

	  return Sites.UEBERSICHT;
  }

  private MainEntry getMainEntry() {
	  MainEntry result = neu ? new MainEntry() : selection.getEntry();
    result.setKurzEintrag(kBeschreibung);
    result.setLangEintrag(lBeschreibung);
    if (!(isNullOrEmpty(referenz) && isNullOrEmpty(uReferenz))) {
      addToReference();
    }
    if (!(isNullOrEmpty(frage) && isNullOrEmpty(antwort))) {
      addToAbfrage();
    }
    result.setReferenzen(referenzen);
    result.setBeispiel(beispiel);
    result.setAbfragen(abfragen);
    return result;
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
	}

	public boolean isAutosave() {

		return testboolean;
	}

	/*public void testAutosave() {
	  System.out.println("Test");
  }

	public void doUpdateAutoSave() {
	  testZahl--;
    if (testZahl == 0) {
      testZahl = 10;
      testboolean = false;
      //loginController.logout();
    } else if (testZahl < 5) {
      testboolean = true;
    }
  }*/

  public void doExtendSession() {
	  this.testZahl = SESSIONPERIOD;
	  this.warningWindowCounter = WARNINGWINDOWTIME;
	  autosaveFrequency = AUTOSAVEPERIOD;
	  anzeigeSessionTimeoutCounter = false;
	  javaScriptCounter = 3;
		sessionTimeoutCounter = SESSIONPERIOD;
  }

  public void doAutosave() {
		javaScriptCounter--;
		warningWindowCounter--;
		String sessionId = selection.getSessionId();
		MainEntry autoSaveEntry = getMainEntry();
		autoSaveController.autosave(autoSaveEntry, selection.getThema(), sessionId);

    if (warningWindowCounter <= 0) {
      //Wenn Session Warncounter == 0, dann kommt der Counter zum Session-Timeout
        anzeigeSessionTimeoutCounter = true;
        sessionTOPopup.setCollapsed(false);
        ;
    }
  }

  /*public String getUpdateAutoSave() {
	  if (this.bearbeiten || this.neu) {
      //do Autosave
      String sessionId = selection.getSessionId();
      MainEntry autoSaveEntry = getMainEntry();
      autoSaveController.autosave(autoSaveEntry, selection.getThema(), sessionId);
      sessionWarnCounter--;
      if (sessionWarnCounter == 0) {
        autosaveFrequency = 3600 * 1000;
        sessionTimeoutCounter = SESSIONPERIOD;
        anzeigeSessionTimeoutCounter = true;
        sessionTOPopup.setCollapsed(false);
      }
    }
    return "WarnCounter: " + sessionWarnCounter;
  }*/

  public String getUpdateSessionAnzeige() {
	  sessionTimeoutCounter--;
	  try {
      if (sessionTimeoutCounter == 0) {
        sessionGone = true;
        anzeigeSessionTimeoutCounter = false;
      } else if (sessionTimeoutCounter < 0) {
        loginController.logout();
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

	public int getJavaScriptCounter() {
		return javaScriptCounter;
	}

	public void setJavaScriptCounter(int javaScriptCounter) {
		this.javaScriptCounter = javaScriptCounter;
	}

  /*public void doJustReturn() {
    ;
  }*/

}

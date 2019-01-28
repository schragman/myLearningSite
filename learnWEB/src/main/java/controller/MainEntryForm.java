package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import beans.EntrySteuerungRemote;
import entities.Abfrage;
import entities.Antwort;
import entities.MainEntry;
import entities.Referenz;
import util.Selections;
import util.Sites;

@ViewScoped
@ManagedBean
public class MainEntryForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private EntrySteuerungRemote entrySteuerung;
	@Inject
	private Selections selection;
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

	private List<Abfrage> abfragen;
	private boolean neu; // für Buttons
	private boolean bearbeiten; // für Buttons

	private String userInput;
	private List<Referenz> quRes;

	public String getUserInput() {
		return userInput;
	}

	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}

	@PostConstruct
	public void init() {

		MainEntry entry = selection.getEntry();
		referenzArt = true;
		labelReferenz = "Buch";
		labelUReferenz = "Seite";
		quRes = entrySteuerung.findReferences();
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

		entrySteuerung.generateNew(result, selection.getThema());

		return Sites.UEBERSICHT;
	}

	public String doUpdateEntry() {
		MainEntry result = selection.getEntry();
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

		entrySteuerung.updEntry(result);

		return Sites.UEBERSICHT;
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
		abfragen.add(neueAbfrage);
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

	public List<Referenz> getAlleReferenzen() {
		List<Referenz> result = new ArrayList<>();
		for (Referenz ref : quRes) {

//			if (ref.getuRefferenz1().contains(userInput))
			result.add(ref);
		}
		return result;
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

}

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
import entities.Check;
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
	private List<Check> fragen;

	@PostConstruct
	public void init() {

		MainEntry entry = selection.getEntry();
		referenzArt = true;
		labelReferenz = "Buch";
		labelUReferenz = "Seite";
		if (null == entry) {
			referenzen = new ArrayList<>();
		} else {
			kBeschreibung = entry.getKurzEintrag();
			lBeschreibung = entry.getLangEintrag();
			referenzen = entry.getReferenzen();
			beispiel = entry.getBeispiel();
			fragen = entry.getFragen();

		}
	}

	public void addReference(ActionEvent ae) {
		addToReference();
		referenzArt = true;
		referenz = "";
		uReferenz = "";
	}

	public void removeReference(Referenz toDelRef) {
		if (referenzen.contains(toDelRef))
			referenzen.remove(toDelRef);
	}

	public String doCreateNewEntry() {
		MainEntry result = new MainEntry();
		result.setKurzEintrag(kBeschreibung);
		result.setLangEintrag(lBeschreibung);
		if (!referenz.isEmpty()) {
			addToReference();
		}
		result.setReferenzen(referenzen);
		result.setBeispiel(beispiel);

		entrySteuerung.generateNew(result, selection.getThema());

		return Sites.UEBERSICHT;
	}

	private void addToReference() {
		Referenz neueReferenz = new Referenz();
		neueReferenz.setArt(referenzArt);
		neueReferenz.setuRefferenz1(referenz);
		neueReferenz.setuRefferenz2(uReferenz);
		referenzen.add(neueReferenz);
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

	public List<Check> getFragen() {
		return fragen;
	}

	public void setFragen(List<Check> fragen) {
		this.fragen = fragen;
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
			labelReferenz = "Beschreibung";
			labelUReferenz = "URL";
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

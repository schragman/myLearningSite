package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import entities.Check;
import entities.Referenz;

@SessionScoped
@Named
public class MainEntryForm implements Serializable {

	private static final long serialVersionUID = 1L;

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
	private List<String> beispiele;
	private List<Check> fragen;

	@PostConstruct
	public void init() {
		referenzArt = true;
		labelReferenz = "Buch";
		labelUReferenz = "Seite";
		referenzen = new ArrayList<>();
	}

	public void addReference(ActionEvent ae) {
		Referenz neueReferenz = new Referenz();
		neueReferenz.setArt(referenzArt);
		neueReferenz.setuRefferenz1(referenz);
		neueReferenz.setuRefferenz2(uReferenz);
		referenzen.add(neueReferenz);
		referenzArt = true;
		referenz = "";
		uReferenz = "";
	}

	public void removeReference(ActionEvent ae) {

		Object test = ae.getSource();

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

	public List<String> getBeispiele() {
		return beispiele;
	}

	public void setBeispiele(List<String> beispiele) {
		this.beispiele = beispiele;
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

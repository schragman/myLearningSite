package controller;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import beans.ThemenSteuerungRemote;

@RequestScoped
@Named
public class NewThemeForm {

	@EJB
	private ThemenSteuerungRemote themenSteuerung;

	private String themenName;
	private String themenBeschreibung;

//	public void doCreateNewTheme() {
	public String doCreateNewTheme() {
		themenSteuerung.generateNew(themenName, themenBeschreibung);
		return "home?faces-redirect=true";
	}

	public String getThemenName() {
		return themenName;
	}

	public void setThemenName(String themenName) {
		this.themenName = themenName;
	}

	public String getThemenBeschreibung() {
		return themenBeschreibung;
	}

	public void setThemenBeschreibung(String themenBeschreibung) {
		this.themenBeschreibung = themenBeschreibung;
	}

}

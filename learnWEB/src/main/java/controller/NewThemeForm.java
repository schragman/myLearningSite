package controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import beans.ThemenSteuerungRemote;

@Named
@SessionScoped
public class NewThemeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ThemenSteuerungRemote themenSteuerung;

	private String themenName;
	private String themenBeschreibung;

	public void doCreateNewTheme(ActionEvent av) {
		themenSteuerung.generateNew(themenName, themenBeschreibung);
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

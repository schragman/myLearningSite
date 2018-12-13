package controller;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import beans.ThemenSteuerung;

@Named
public class NewThemeForm {
	@Inject
	private ThemenSteuerung themenSteuerung;

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

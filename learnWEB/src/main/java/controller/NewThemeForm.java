package controller;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import beans.ThemenSteuerungRemote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Sites;

@RequestScoped
@Named
public class NewThemeForm {

	private static Logger LOG = LoggerFactory.getLogger(NewThemeForm.class);

	@EJB
	private ThemenSteuerungRemote themenSteuerung;

	private String themenName;
	private String themenBeschreibung;

	public String doCreateNewTheme() {
		themenSteuerung.generateNew(themenName, themenBeschreibung);
		LOG.info("New topic " + themenName + " is been created!");
		return Sites.HOME + Sites.DORELOAD;
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

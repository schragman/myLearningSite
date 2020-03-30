package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import beans.AusgabeBean;
import beans.EntrySteuerungRemote;
import beans.ThemenSteuerungRemote;
import entities.HauptThema;
import entities.MainEntry;
import util.Selections;
import util.Sites;

@RequestScoped
@Named
public class HomeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<HauptThema> themenListe;

	@Inject
	private AusgabeBean ausgabeBean;

	@Inject
	private MenuForm menuForm;

	@EJB
	private EntrySteuerungRemote entrySteuerung;

	@EJB
	private ThemenSteuerungRemote themenSteuerung;

	@Inject
	private Selections selection;

	@PostConstruct
	public void init() {
		themenListe = ausgabeBean.findThemes();
	}

	public void doCreateMenu() {
		menuForm.setSelectedPage(SelectedPage.HOME);
	}

	public String getTitle() {
		try {
			themenListe.get(0);
		} catch (Exception e) {
			return "Bitte ein Thema angeben";
		}
		return "Hier ist die Liste der Themen";
	}

	public List<HauptThema> getThemenListe() {
		return themenListe;
	}

	public String chooseTheme(HauptThema thema) {
		String result;

		selection.setThema(thema);
		if (null == entrySteuerung.findEntries(thema).get(0)) {
			result = Sites.MAINENTRY + Sites.DORELOAD;
			selection.setEntry(null);
		} else
			result = Sites.UEBERSICHT + Sites.DORELOAD;

		return result;
	}

	public String deleteTheme(HauptThema thema) {
		themenSteuerung.deleteTheme(thema);
		return Sites.HOME + Sites.DORELOAD;
	}

}

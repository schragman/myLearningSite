package controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import beans.AusgabeBean;
import entities.TestEntry;

@SessionScoped
@Named
public class HomeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	// private List<HauptThema> themenListe;
	private TestEntry themenListe;

	@Inject
	private AusgabeBean ausgabeBean;

//	@PostConstruct
//	public void init() {
//		themenListe = ausgabeBean.findThemes();
//	}

	public String getTitle() {
		try {
			// themenListe.get(0);
			return themenListe.getEintrag();
		} catch (Exception e) {
			return "Bitte ein Thema angeben";
		}
		// return "Hier ist die Liste der Themen";
	}

}

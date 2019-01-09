package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import beans.AusgabeBean;
import entities.HauptThema;

@RequestScoped
@Named
public class HomeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<HauptThema> themenListe;

	@Inject
	private AusgabeBean ausgabeBean;

	@PostConstruct
	public void init() {
		themenListe = ausgabeBean.findThemes();
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

}

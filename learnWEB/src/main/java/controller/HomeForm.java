package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import beans.AusgabeBean;
import beans.EntrySteuerungRemote;
import beans.ThemenSteuerungRemote;
import entities.Category;
import entities.HauptThema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Selections;
import util.Sites;

@RequestScoped
@Named
public class HomeForm implements Serializable {

	private static Logger LOG = LoggerFactory.getLogger(HomeForm.class);

	private static final long serialVersionUID = 1L;

	private List<HauptThema> themenListe;
	private List<Long> themeIdsSelected;
	private List<Category> categories;

	private Collection<HauptThema> themesShown;


	@EJB
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
		Category category = selection.getCategory();
		categories = ausgabeBean.findCategories();
		if (category == null) {
			category = categories.get(0);
			if (null == category) {
				throw new IllegalStateException("User ohne Kategorie");
			}
			selection.setCategory(category);
		}
		themenListe = ausgabeBean.findThemes(category);
		themesShown = ausgabeBean.findThemesNotinCat(category);

	}

	public void doCreateMenu() {
		menuForm.setSelectedPage(SelectedPage.HOME);
	}

	public String doAssignThemes() {
	/*	List<HauptThema> selThemes = this.selectedThemes;
		Category cat = selection.getCategory();*/

		Collection<Long> selectedThemes = this.themeIdsSelected;

		this.ausgabeBean.reassignThemes(this.selection.getCategory(), selectedThemes);

		return Sites.HOME + Sites.DORELOAD;
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

	public List<Category> getCategories() {
		return categories;
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

	public void chooseCat(Category category) {
		selection.setCategory(category);
		themenListe = ausgabeBean.findThemes(category);
		themesShown = ausgabeBean.findThemesNotinCat(category);
	}

	public String deleteTheme(HauptThema thema) {
		themenSteuerung.deleteTheme(thema);
		LOG.info(thema.getThema() + " is getting deleted!");
		return Sites.HOME + Sites.DORELOAD;
	}

	public List<Long> getThemeIdsSelected() {
		return themeIdsSelected;
	}

	public void setThemeIdsSelected(List<Long> themeIdsSelected) {
		this.themeIdsSelected = themeIdsSelected;
	}

	public Collection<HauptThema> getThemesShown() {
		return themesShown;
	}

	public void setThemesShown(Collection<HauptThema> themesShown) {
		this.themesShown = themesShown;
	}

	public boolean isReassignmentVisible(Long catId) {
		long selectedCatId = this.selection.getCategory().getId();
		return catId == selectedCatId;
	}

	public boolean isReassignmentDeleteDisabled(Long catId) {
		Optional<Category> selCat = categories.stream()
				.filter(c -> c.getId() == catId)
				.findFirst();
		if (!selCat.isPresent()) {
			throw new IllegalStateException("Kategorie nicht vorhanden!");
		}
		return selCat.get().getName().equalsIgnoreCase("Hauptkategorie");
	}

}

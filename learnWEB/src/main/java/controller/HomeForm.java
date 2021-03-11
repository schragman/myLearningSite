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
import org.apache.myfaces.tobago.model.SheetState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Selections;
import util.Sites;
import utils.Konstanten;

@RequestScoped
@Named
public class HomeForm implements Serializable {

	private static Logger LOG = LoggerFactory.getLogger(HomeForm.class);

	private static final long serialVersionUID = 1L;

	private List<HauptThema> themenListe;
	private List<Long> themeIdsSelected;
	private List<Category> categories;
	private Long categorySelected2Transfer2;

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
		return selCat.get().getName().equalsIgnoreCase(Konstanten.HAUPTKATEGORIE);
	}

	public List<Category> getCategoriesButOne() {
		List<Category> result = new ArrayList<>();
		Category oneCategory = selection.getCategory();
		for (Category category : this.categories) {
			if (category.getId() != oneCategory.getId()) {
				result.add(category);
			}
		}
		return result;
	}

	public void doPrepareDelete(Category category) {
		this.selection.setCategory(category);
	}

	public String doGoHome() {
		this.selection.setHomeThemeSheetState(new SheetState());
		return Sites.HOME + Sites.DORELOAD;
	}

	public boolean isOtherCatVisibleWhenDelete() {
		List<HauptThema> themen = this.ausgabeBean.findThemes(this.selection.getCategory());
		boolean result;
		if (themen == null || themen.size() == 0) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}

	public Long getCategorySelected2Transfer2() {
		return categorySelected2Transfer2;
	}

	public void setCategorySelected2Transfer2(Long categorySelected2Transfer2) {
		this.categorySelected2Transfer2 = categorySelected2Transfer2;
	}

	public String doDeleteCatWTransfer() {
		Long catId = this.categorySelected2Transfer2;
		Category cat2Delete = this.selection.getCategory();
		Category cat2Transfer2 = ausgabeBean.getCatById(catId);
		List<HauptThema> themes2Transfer = ausgabeBean.findThemes(cat2Delete);

		ausgabeBean.reassignThemes(cat2Transfer2, themes2Transfer);
		this.selection.setCategory(ausgabeBean.getHauptCat());
		ausgabeBean.delCat(cat2Delete);
		return Sites.HOME + Sites.DORELOAD;
	}

	public String doDeleteCat() {
		ausgabeBean.delCat(this.selection.getCategory());
		this.selection.setCategory(ausgabeBean.getHauptCat());
		return Sites.HOME + Sites.DORELOAD;
	}

	public String getCategoryNameSelected() {
		return this.selection.getCategory().getName();
	}

	public boolean isCatNameChangeDisabled() {
		String selCatName = this.selection.getCategory().getName();
		boolean result = selCatName.equals(Konstanten.HAUPTKATEGORIE);
		return result;
	}
}

package controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;
import javax.validation.constraints.Size;

import beans.AusgabeBean;
import beans.ThemenSteuerungRemote;
import entities.Category;
import entities.HauptThema;
import org.apache.myfaces.tobago.component.UIPopup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.InternMessages;
import util.Selections;
import util.Sites;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequestScoped
@Named
public class NewThemeForm {

	private static Logger LOG = LoggerFactory.getLogger(NewThemeForm.class);

	@EJB
	private ThemenSteuerungRemote themenSteuerung;

	@Inject
	private HomeForm homeForm;

	@Inject
	private Selections selections;

	@EJB
	private AusgabeBean ausgabeBean;

	private String themenName;
	private String themenBeschreibung;
	//private Category category;
	private List<String> categoryNames;
	private String categoryName;

	private String newThemeName;

	@PostConstruct
	public void init() {
		categoryNames = new ArrayList<>();
		for (Category category : homeForm.getCategories()) {
			categoryNames.add(category.getName());
		}
	}

	public String doCreateNewTheme() {
		Category category = null;
		for (Category cat : homeForm.getCategories()) {
			if (cat.getName().equals(categoryName)) {
				category = cat;
			}
		}
		if (null == category) {
			throw new IllegalStateException("Keine Kategorie zugeordnet");
		}
		try {
			this.checkThemeName(themenName, category);
			themenSteuerung.generateNew(themenName, themenBeschreibung, category);
			LOG.info("New topic " + themenName + " is been created!");
			return Sites.HOME + Sites.DORELOAD;
		} catch (ValidationException vx) {
			Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
			String msg = InternMessages.getMessageForLocaleWParam("noThemeSaved", locale, themenName);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg
					, vx.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage("page:Seitenfehler", fm);
			return null;
		}
	}

	public String doRenameTheme() {
		try {
			this.checkThemeName(this.newThemeName, this.selections.getCategory());
			LOG.info("Thema " + selections.getThema().getThema() + " is been renamed into " + this.newThemeName);
			themenSteuerung.renameTheme(selections.getThema(), this.newThemeName);
			return Sites.HOME + Sites.DORELOAD;
		} catch (ValidationException ex) {
			Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
			String msg = InternMessages.getMessageForLocaleWParam("themeNotRenamed", locale, newThemeName);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg
					, ex.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage("page:Seitenfehler", fm);
			return null;
		}
	}

	public String getSelectedThemeName() {
		List<Integer> index = selections.getHomeThemeSheetState().getSelectedRows();
		if (index.size() != 1) {
			return null;
		}
		List<HauptThema> selectedThemes = homeForm.getThemenListe();
		String selectedTheme = selectedThemes.get(index.get(0)).getThema();
		selections.setThema(selectedThemes.get(index.get(0)));
		return selectedTheme;
	}

	private void checkThemeName(String themeName, Category category) {
		List<HauptThema> themes = ausgabeBean.findThemes(category);
		if (themes == null) {
			return;
		}

		for (HauptThema theme : themes) {
			if (theme.getThema().equalsIgnoreCase(themeName)) {
				LOG.warn("Name of Theme " + themeName + " is already in use");
				Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
				throw new ValidationException(InternMessages
						.getMessageForLocaleWParam("nameAlreadyPresent", locale, themeName));
			}
		}
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

	public String getCategory() {
		return categoryName;
	}

	public void setCategory(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<String> getCategoryNames() {
		return categoryNames;
	}

	@Size(min = 2, message = ">2")
	public String getNewThemeName() {
		return newThemeName;
	}

	public void setNewThemeName(String newThemeName) {
		this.newThemeName = newThemeName;
	}

	public String getBoxMessage() {
		Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
		String themeName = this.getNewThemeName();
		String msg;
		if (themeName == null) {
			msg = InternMessages.getMessageForLocale("noThemeSelected", locale);
		} else {
			msg = InternMessages.getMessageForLocaleWParam("renameThemeInto",
					locale, this.getSelectedThemeName());
		}
		return msg;
	}
}

package controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import beans.ThemenSteuerungRemote;
import entities.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Sites;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Named
public class NewThemeForm {

	private static Logger LOG = LoggerFactory.getLogger(NewThemeForm.class);

	@EJB
	private ThemenSteuerungRemote themenSteuerung;

	@Inject
	private HomeForm homeForm;

	private String themenName;
	private String themenBeschreibung;
	//private Category category;
	private List<String> categoryNames;
	private String categoryName;

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
		themenSteuerung.generateNew(themenName, themenBeschreibung, category);
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

	public String getCategory() {
		return categoryName;
	}

	public void setCategory(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<String> getCategoryNames() {
		return categoryNames;
	}
}

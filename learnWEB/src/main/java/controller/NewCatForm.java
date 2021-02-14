package controller;

import beans.ThemenSteuerungRemote;
import entities.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Sites;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Named
public class NewCatForm {

	private static Logger LOG = LoggerFactory.getLogger(NewCatForm.class);

	@EJB
	private ThemenSteuerungRemote themenSteuerung;

	@Inject
	private HomeForm homeForm;

	private String categoryName;

	public String doCreateNewCat() {

		themenSteuerung.generateNewCat(categoryName);
		LOG.info("New Category " + categoryName + " is been created!");
		return Sites.HOME + Sites.DORELOAD;
	}

	public String getCategory() {
		return categoryName;
	}

	public void setCategory(String categoryName) {
		this.categoryName = categoryName;
	}

}

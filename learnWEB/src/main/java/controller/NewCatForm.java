package controller;

import beans.AusgabeBean;
import beans.ThemenSteuerungRemote;
import entities.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.InternMessages;
import util.Sites;
import util.validation.TestValidation;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequestScoped
@Named
public class NewCatForm {

	private static Logger LOG = LoggerFactory.getLogger(NewCatForm.class);

	@EJB
	private ThemenSteuerungRemote themenSteuerung;

	@EJB
	private AusgabeBean ausgabeBean;

	@Inject
	private HomeForm homeForm;

	private String categoryName;

	public String doCreateNewCat() {

		List<Category> categories = ausgabeBean.findCategories();
		if (categories == null || categories.size() == 0) {
			throw new IllegalStateException("User ohne Kategorie!");
		}

		try {
			for (Category category : categories) {
				if (category.getName().equalsIgnoreCase(categoryName)) {
					LOG.warn("Name of category " + categoryName + " is already in use");
					Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
					throw new ValidationException(InternMessages
							.getMessageForLocaleWParam("noCatSaved", locale, categoryName));
				}
			}
				themenSteuerung.generateNewCat(categoryName);
				LOG.info("New Category " + categoryName + " is been created!");
			return Sites.HOME + Sites.DORELOAD;
		} catch (ValidationException ex) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getLocalizedMessage()
						, ex.getLocalizedMessage());
				FacesContext.getCurrentInstance().addMessage("page:Seitenfehler", fm);
				return null;
		}
	}

	//@TestValidation(min=3, max=10)
	public String getCategory() {
		return categoryName;
	}

	public void setCategory(String categoryName) {
		this.categoryName = categoryName;
	}

}

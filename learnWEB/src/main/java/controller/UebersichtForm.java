package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import beans.EntrySteuerungRemote;
import beans.SearchSteuerungRemote;
import entities.MainEntry;
import util.Selections;
import util.Sites;
import utils.SearchContainer;
import utils.SearchItems;

@RequestScoped
@Named
public class UebersichtForm {

	@Inject
	private Selections selection;

	@Inject
	private MenuForm menuForm;

	private List<MainEntry> entries;

	@EJB
	private EntrySteuerungRemote entrySteuerung;
	
	@EJB
	private SearchSteuerungRemote searchSteuerung;
	
	private String suchAnfrage;
	private String userInput;
	private Collection<SearchContainer> alleSuchErgebnisse;

	
	@PostConstruct
	public void init() {
		alleSuchErgebnisse = searchSteuerung.findSearchEntries(selection.getThema());
	}
	
	public void doCreateMenu() {
		menuForm.setSelectedPage(SelectedPage.UEBERSICHT);
	}

	public List<MainEntry> getEntries() {
		List<MainEntry> entries = entrySteuerung.findEntries(selection.getThema());
	  return entries;
	}

	public String doJumpEntry(MainEntry entry) {
		selection.setEntry(entry);
		return Sites.MAINENTRY + Sites.DORELOAD;
	}

	public String doCreateNewEntry() {
		selection.setEntry(null);
		return Sites.MAINENTRY + Sites.DORELOAD;
	}

	public String deleteEntry(MainEntry entry) {
		entrySteuerung.deleteEntry(entry);
		return Sites.UEBERSICHT + Sites.DORELOAD;
	}


	public List<SearchContainer> getVorschlagsliste() {
		List<SearchContainer> result = new ArrayList<>();
		
		String validatedUserInput = null==userInput ? "" : userInput;
		for (SearchContainer sItem : alleSuchErgebnisse) {
			String itemContent = sItem.getContent();
			if (itemContent.toLowerCase().contains(validatedUserInput.toLowerCase())) {
				if (itemContent.length() > 20) {
					itemContent = itemContent.substring(0,19) + " ...";
					if (itemContent.contains("\n")) {
						String[] items = itemContent.split("\n");
						itemContent = String.join(" ", items);
					}
					sItem.setContent(itemContent);
				}
				result.add(sItem);
			}
		}
		return result;
	}
	
	public String getSuchAnfrage() {
		return suchAnfrage;
	}
	
	public void setSuchAnfrage(String suchAnfrage) {
		this.suchAnfrage = suchAnfrage;
	}
	
	public String getUserInput() {
		return userInput;
	}
	
	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}
}

package controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import beans.EntrySteuerungRemote;
import beans.SearchSteuerungRemote;
import entities.HauptThema;
import entities.MainEntry;
import org.apache.myfaces.tobago.model.SheetState;
import util.Selections;
import util.Sites;
import utils.SearchContList;
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
	private Collection<SearchContainer> suchErgebnisse4Form;
	private SearchContainer dummy;

	
	@PostConstruct
	public void init() {
		alleSuchErgebnisse = searchSteuerung.findSearchEntries(selection.getThema());
		dummy = new SearchContList("Nothing there", SearchItems.KURZEINTRAG, 0);
		if (selection.getSearchItems() == null) {
			initSuchErgebnisse();
		} else {
			suchErgebnisse4Form = selection.getSearchItems();
		}
	}
	
	public void doCreateMenu() {
		selection.setEntry(null);
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
		//Ansonsten ist noch das letzte Entry gesetzt, was zu einem Fehler führt.
		selection.setEntry(null);
		return Sites.MAINENTRY + Sites.DORELOAD;
	}

	public String deleteEntry(MainEntry entry) {
		entrySteuerung.deleteEntry(entry);
		return Sites.UEBERSICHT + Sites.DORELOAD;
	}

	//Wenn man auf den Menüpunkt Übersicht klickt
	public String doGoUebersicht() {
		return Sites.UEBERSICHT;
	}


	public List<SearchContainer> getVorschlagsliste() {
		List<SearchContainer> result = new ArrayList<>();

		String validatedUserInput = null == userInput ? "" : userInput;
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

	public String startSuchanfrage() {
		String suchString = suchAnfrage.endsWith(" ...") ? suchAnfrage.substring(0, suchAnfrage.length() - 4) : suchAnfrage;
		if (suchString.equals("")) {
			return Sites.UEBERSICHT + Sites.DORELOAD;
		}
		suchErgebnisse4Form = alleSuchErgebnisse.stream()
						.filter(item -> item.getContent().toLowerCase().contains(suchString.toLowerCase()))
						.map(item -> new SearchContList(item.getContent(), item.getSearchItem(), item.getId()))
						.collect(Collectors.toSet());

		if (suchErgebnisse4Form == null || suchErgebnisse4Form.size() == 0) {
			initSuchErgebnisse();
			return "";
		}
		else if (suchErgebnisse4Form.size() == 1) {
			SearchContainer result = suchErgebnisse4Form.stream().findFirst().get();
			return doJumpEntry(entrySteuerung.findById(result.getId()));
		} else {
			selection.setSearchItems(suchErgebnisse4Form);
			return "";
		}
	}

	private void initSuchErgebnisse() {
		suchErgebnisse4Form = new HashSet<>();
		suchErgebnisse4Form.add(dummy);
	}

	public void eraseSearchValue() {
		this.suchAnfrage = "";
	}

	public String doJumpSearchItem(SearchContainer searchItemFromForm) {
		return doJumpEntry(entrySteuerung.findById(searchItemFromForm.getId()));
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

	public Collection<SearchContainer> getSuchErgebnisse4Form() {
		return suchErgebnisse4Form;
	}

	public void setSuchErgebnisse4Form(Collection<SearchContainer> suchErgebnisse4Form) {
		this.suchErgebnisse4Form = suchErgebnisse4Form;
	}

	public String isSelected(HauptThema theme) {
		HauptThema selectedTheme = selection.getThema();
		if (theme.getThema().equals(selectedTheme.getThema())) {
			return "bold";
		} else {
			return "";
		}
	}

}

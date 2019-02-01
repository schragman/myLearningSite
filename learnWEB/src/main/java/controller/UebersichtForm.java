package controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import beans.EntrySteuerungRemote;
import entities.MainEntry;
import util.Selections;
import util.Sites;

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

	public void doCreateMenu() {
		menuForm.setSelectedPage(SelectedPage.UEBERSICHT);
	}

	public List<MainEntry> getEntries() {
		return entrySteuerung.findEntries(selection.getThema());
	}

	public String doJumpEntry(MainEntry entry) {
		selection.setEntry(entry);
		return Sites.MAINENTRY + Sites.DORELOAD;
	}

	public String doCreateNewEntry() {
		selection.setEntry(null);
		return Sites.MAINENTRY + Sites.DORELOAD;
	}

}

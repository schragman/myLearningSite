package util;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import entities.HauptThema;
import entities.MainEntry;
import org.apache.myfaces.tobago.model.SheetState;
import utils.SearchContainer;

@SessionScoped
@Named
public class Selections implements Serializable {

	private static final long serialVersionUID = 1L;
	private HauptThema thema;
	private MainEntry entry;
	private String searchEntry;
	private Collection<SearchContainer> searchItems;

	public MainEntry getEntry() {
		return entry;
	}

	public void setEntry(MainEntry entry) {
		this.entry = entry;
	}

	public HauptThema getThema() {
		return thema;
	}

	public void setThema(HauptThema thema) {
		this.thema = thema;
	}

	public String getSearchEntry() {
		return searchEntry;
	}

	public void setSearchEntry(String searchEntry) {
		this.searchEntry = searchEntry;
	}

	public Collection<SearchContainer> getSearchItems() {
		return searchItems;
	}

	public void setSearchItems(Collection<SearchContainer> searchItems) {
		this.searchItems = searchItems;
	}
}

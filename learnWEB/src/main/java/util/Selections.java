package util;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import entities.HauptThema;
import entities.MainEntry;
import org.apache.myfaces.tobago.model.SheetState;
import secEntities.Users;
import utils.SearchContainer;

@SessionScoped
@Named
public class Selections implements Serializable {

	private static final long serialVersionUID = 1L;
	private HauptThema thema;
	private MainEntry entry;
	private String searchEntry;
	private Collection<SearchContainer> searchItems;
	private boolean passwordChangeActive;
	private String selectedUser;

  @Size(min = 5, message = "Mindestens fünf Zeichen")
	private String pw1;

	@PostConstruct
	public void init() {
		this.passwordChangeActive = false;
	}

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

  public String getPw1() {
    return pw1;
  }

  public void setPw1(String pw1) {
    this.pw1 = pw1;
  }

	public boolean isPasswordChangeActive() {
		return passwordChangeActive;
	}

	public void setPasswordChangeActive(boolean passwordChangeActive) {
		this.passwordChangeActive = passwordChangeActive;
	}
}

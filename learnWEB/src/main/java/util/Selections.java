package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import entities.Category;
import entities.HauptThema;
import entities.MainEntry;
import org.apache.myfaces.tobago.model.SheetState;
import utils.SearchContainer;

@SessionScoped
@Named
public class Selections implements Serializable {

	private static final long serialVersionUID = 1L;
	private HauptThema thema;
	private Category category;
	private MainEntry entry;
	private String searchEntry;
	private Collection<SearchContainer> searchItems;
	private boolean passwordChangeActive;
	private boolean okButtonActive;
	private boolean newUser;
	private String selectedUser;
	private String role;
	private Collection<String> userRoles;
	private String sessionId;
	private SheetState homeThemeSheetState;

	@Size(min = 7, message = "Passwort muss mindestens sieben Zeichen haben!")
	private String pw1;

	@Size(min = 8, message = "Username muss mindestens 8 Zeichen haben!")
	private String newUsername;

	@PostConstruct
	public void init() {
		this.passwordChangeActive = false;
		this.okButtonActive = false;
		this.newUser = false;
		this.role = "kunde";
		this.userRoles = new ArrayList<>(Arrays.asList("kunde", "admin"));
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		this.sessionId = session.getId();
	}

	/*@PreDestroy
	public void warn() {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Session Timeout", "Session geht weg");
		FacesContext.getCurrentInstance().addMessage("page", fm);
	}*/


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

  public String getSelectedUser() {
    return selectedUser;
  }

  public void setSelectedUser(String selectedUser) {
    this.selectedUser = selectedUser;
  }

  public boolean isPasswordChangeActive() {
		return passwordChangeActive;
	}

  public boolean isOkButtonActive() {
    return okButtonActive;
  }

  public void setOkButtonActive(boolean okButtonActive) {
    this.okButtonActive = okButtonActive;
  }

  public void setPasswordChangeActive(boolean passwordChangeActive) {
		this.passwordChangeActive = passwordChangeActive;
	}

	public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}

	public String getNewUsername() {
		return newUsername;
	}

	public void setNewUsername(String newUsername) {
		this.newUsername = newUsername;
	}

	public void setRole(String rolle) {
		this.role = rolle;
	}

	public String getRole() {
		return this.role;
	}

	public Collection<String> getUserRoles() {
		return userRoles;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SheetState getHomeThemeSheetState() {
		return homeThemeSheetState;
	}

	public void setHomeThemeSheetState(SheetState homeThemeSheetState) {
		this.homeThemeSheetState = homeThemeSheetState;
	}

}

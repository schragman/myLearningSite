package util;

import beans.UserBean;
import secEntities.Users;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Min;
import java.util.List;

@RequestScoped
@Named
public class UserManagementForm {

  @EJB
  private UserBean userBean;

  private String selectedUserName;

  @Inject
  private Selections selections;

  @Min(value = 5, message ="Mindestens fünf Zeichen")
  private String password1;

  private String confirmation;

  private boolean okActive;

  @PostConstruct
  public void init() {
    this.okActive = false;
  }


  public String getPassword1() {
    return password1;
  }

  public void setPassword1(String password1) {
    this.password1 = password1;
  }

  public String getSelectedUserName() {
    return selectedUserName;
  }

  public void doSetSelectedUserName(String selectedUserName) {
    this.selectedUserName = selectedUserName;
    this.selections.setPasswordChangeActive(true);
  }

  public void setPasswordChangeNotActive() {
    this.selections.setPasswordChangeActive(false);
  }

  public String getCurrentUser() {return userBean.getUsername();}

  public List<Users> getAllUsers() {
    return userBean.findAll();
  }

  public void validateConfirmation() {
    FacesContext fc = FacesContext.getCurrentInstance();
    if (! this.confirmation.equals(selections.getPw1())) {
      FacesMessage lvError =
          new FacesMessage("Passwort und Bestätigung stimmen nicht überein!");
      lvError.setSeverity(FacesMessage.SEVERITY_ERROR);
      fc.addMessage("page:uManagement:cfm", lvError);
    } else {
      this.okActive = true;
    }
  }

  public void doOk() {
    String passwd = this.selections.getPw1();
  }

  public String getConfirmation() {
    return confirmation;
  }

  public void setConfirmation(String confirmation) {
    this.confirmation = confirmation;
  }

  public boolean isOkActive() {
    return okActive;
  }
}


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
import java.util.List;

@RequestScoped
@Named
public class UserManagementForm {

  @EJB
  private UserBean userBean;

  @Inject
  private Selections selections;

  private String confirmation;

  private boolean okActive;

  public void doSetSelectedUserName(String selectedUserName) {
    this.selections.setSelectedUser(selectedUserName);
    this.selections.setPasswordChangeActive(true);
  }

  public void setPasswordChangeNotActive() {
    this.selections.setPasswordChangeActive(false);
    this.selections.setOkButtonActive(false);
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
      this.selections.setOkButtonActive(true);
    }
  }

  public void doChangePassword() {
    if (this.selections.isOkButtonActive()) {
      String passwd = this.selections.getPw1();
      String userName = this.selections.getSelectedUser();
      userBean.changePassword(userName, passwd);
    }
      this.setPasswordChangeNotActive();
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


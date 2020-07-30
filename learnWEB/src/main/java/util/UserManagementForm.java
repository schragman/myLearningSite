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
  private String pwChange;

  public void doSetSelectedUserName(String selectedUserName) {
    this.selections.setSelectedUser(selectedUserName);
    this.selections.setPasswordChangeActive(true);
    this.selections.setOkButtonActive(false);
    this.selections.setNewUser(false);
    this.pwChange = "Reset Password";
  }

  public void setPasswordChangeNotActive() {
    this.selections.setPasswordChangeActive(false);
    this.selections.setOkButtonActive(false);
    this.selections.setNewUser(false);
    this.selections.setRole("kunde");
    this.selections.setNewUsername("");
  }

  public void doCreateNewUser() {
    this.selections.setNewUser(true);
    this.selections.setOkButtonActive(false);
    this.selections.setPasswordChangeActive(true);
    this.pwChange = "New User";
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
      this.selections.
          setOkButtonActive(!(this.selections.isNewUser()&&this.selections.getNewUsername().isEmpty()));
    }
  }

  public void doChangePassword() {
    if (this.selections.isOkButtonActive()) {
      String passwd = this.selections.getPw1();
      if(this.selections.isNewUser()) {
        String rolle = this.selections.getRole();
        String userName = this.selections.getNewUsername();
        userBean.createNewUser(userName, passwd, rolle);
      } else {
        String userName = this.selections.getSelectedUser();
        userBean.changePassword(userName, passwd);
      }
    }
      this.setPasswordChangeNotActive();
  }

  public void doRemoveUser(String userName){
    userBean.deleteUser(userName);
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

  public String getPwChange() {
    return pwChange;
  }
}


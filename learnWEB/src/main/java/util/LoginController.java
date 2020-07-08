package util;

import beans.UserBean;
import secEntities.Users;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Named
@RequestScoped
public class LoginController {

    private String username;
    private String password;

    @EJB
    private UserBean userBean;

    public String login() {
        Users user = userBean.find(username, password);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        if (user == null) {
            context.addMessage(null, new FacesMessage("Unknown login, try again"));
            username = null;
            password = null;
            return null;
        } else {
          try {
            request.login(username, password);
            context.getExternalContext().getSessionMap().put("user", user);
            return "/faces/home.jsf" + Sites.DORELOAD;
          } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed!", null));
            return "login";
          }
        }
    }

    public void logout() throws IOException{
      FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
      //Entweder ein nicht-absoluter Pfad oder über Properties lösen.
      FacesContext.getCurrentInstance().getExternalContext().redirect("home");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
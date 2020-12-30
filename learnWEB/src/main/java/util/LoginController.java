package util;

import beans.UserBean;
import secEntities.Users;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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

    public void logout(boolean redirect) throws IOException{

      ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
      Map<String, Object> cookieMap = ec.getRequestCookieMap();
      Cookie cookie = (Cookie) cookieMap.get("JSESSIONID");
      cookie.setMaxAge(0);
      HttpServletResponse response = (HttpServletResponse) ec.getResponse();
      response.addCookie(cookie);

      ec.invalidateSession();
      //Entweder ein nicht-absoluter Pfad oder über Properties lösen.
      if (redirect) {
        FacesContext.getCurrentInstance().getExternalContext().redirect("home");
      }
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
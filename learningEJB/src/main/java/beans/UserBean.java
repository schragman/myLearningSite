package beans;

import secEntities.Konfiguration;
import secEntities.Users;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@LocalBean
public class UserBean {

    @Resource
    private SessionContext ctx;

    @PersistenceContext(unitName = "LearningPU")
    private EntityManager em;

    public String getUsername() {
        return ctx.getCallerPrincipal().getName();
    }

    public Users find(String username, String password) {
      TypedQuery<Users> query = em.createQuery(
              "SELECT u FROM Users u WHERE u.userName = :passedName AND u.password = :passedPassword",
              Users.class);
      query.setParameter("passedName", username);
      query.setParameter("passedPassword", password);
      Users result;
      try {
        result = query.getSingleResult();
      } catch(NoResultException ex) {
        return null;
      }
      return result;
    }

    public List<Users> findAll() {
      TypedQuery<Users> query = em.createQuery("Select u FROM Users u", Users.class);
      return query.getResultList();
    }

    public void changePassword(String username, String newPassword) {
      Users user = em.find(Users.class, username);
      user.setPassword(newPassword);
      em.merge(user);
    }

    public void createNewUser(String username, String password, String rolle) {
      Users user = new Users();
      user.setUserName(username);
      user.setPassword(password);
      user.setRolle(rolle);
      Konfiguration config = new Konfiguration();
      config.setDefaults();
      user.setConfig(config);
      em.persist(user);
    }

    public void deleteUser(String userName) {
      Users user = em.find(Users.class, userName);
      em.remove(user);
    }

    public void changeNbLines(int nbLines, ConfigType configType) {
      String userName = ctx.getCallerPrincipal().getName();
      Users user = em.find(Users.class, userName);
      Konfiguration konfig = user.getConfig();
      switch (configType) {
        case DESCR_LINES:
          konfig.setDecrRows(nbLines);
          break;
        case SAMPLE_LINES:
          konfig.setSampleRows(nbLines);
          break;
        default:
          konfig.setDefaults();
      }
      em.merge(user);
    }

    public int getNbLines(ConfigType configType) {
      String userName = ctx.getCallerPrincipal().getName();
      /*em.flush();*/
      Users user = em.find(Users.class, userName);
      Konfiguration konfig = user.getConfig();
      int result;
      switch (configType) {
        case DESCR_LINES:
          result = konfig.getDecrRows();
          break;
        case SAMPLE_LINES:
          result = konfig.getSampleRows();
          break;
        default:
          result = 10;
      }
      return result;
    }

    public enum ConfigType {
      DESCR_LINES, SAMPLE_LINES;
    }

}

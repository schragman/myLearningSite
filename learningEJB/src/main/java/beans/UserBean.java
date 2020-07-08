package beans;

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

}

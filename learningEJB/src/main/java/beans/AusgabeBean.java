package beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.HauptThema;
import secEntities.Users;

public class AusgabeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "LearningPU")
	private EntityManager em;
	@EJB
	private UserBean userBean;

	public List<HauptThema> findThemes() {
		TypedQuery<HauptThema> query = em.createNamedQuery("findAllThemes", HauptThema.class);
		String userName = userBean.getUsername();
		Users user = em.find(Users.class, userName);
		query.setParameter("passedUser", user);
		List<HauptThema> resultList = query.getResultList();
		return resultList;
	}

}

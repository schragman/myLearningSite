package beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Category;
import entities.HauptThema;
import secEntities.Users;

@LocalBean
@Stateless
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
	public List<HauptThema> findThemes(Category category) {
		TypedQuery<HauptThema> query = em.createNamedQuery("findThemesCat", HauptThema.class);
		query.setParameter("passedCategory", category);
		List<HauptThema> resultList = query.getResultList();
		return resultList;
	}

	public List<HauptThema> findThemesNotinCat(Category category) {
		TypedQuery<HauptThema> query = em.createNamedQuery("findThemesNotCat", HauptThema.class);
		String userName = userBean.getUsername();
		Users user = em.find(Users.class, userName);
		query.setParameter("passedCategory", category);
		query.setParameter("passedUser", user);
		List<HauptThema> resultList = query.getResultList();
		return resultList;
	}

	public List<Category> findCategories() {
		TypedQuery<Category> query = em.createNamedQuery("findAllCategories", Category.class);
		String userName = userBean.getUsername();
		Users user = em.find(Users.class, userName);
		query.setParameter("passedUser", user);
		List<Category> resultList = query.getResultList();
		return resultList;
	}

	public void reassignThemes(Category category, Collection<Long> themeIds) {
		for (long id : themeIds) {
			HauptThema thema = em.find(HauptThema.class, id);
			thema.setCategory(category);
			em.merge(thema);
		}
	}

	public void reassignThemes(Category zielCat, List<HauptThema> themen) {
		for (HauptThema thema : themen) {
			thema.setCategory(zielCat);
			em.merge(thema);
		}
	}

	public Category getCatById(Long catId) {
		return em.find(Category.class, catId);
	}

	public void delCat(Category cat2Delete) {
		Category cat = em.merge(cat2Delete);
		em.remove(cat);
	}

	public Category getHauptCat() {
		TypedQuery<Category> query = em.createNamedQuery("findMainCategory", Category.class);
		String userName = userBean.getUsername();
		Users user = em.find(Users.class, userName);
		query.setParameter("passedUser", user);
		Category result = query.getSingleResult();
		return result;
	}

}

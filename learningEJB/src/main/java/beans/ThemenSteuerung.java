package beans;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Category;
import entities.HauptThema;
import secEntities.Users;
import utils.Konstanten;

import java.util.List;

@Stateless
@LocalBean
public class ThemenSteuerung implements ThemenSteuerungRemote {

	@PersistenceContext(unitName = "LearningPU")
	private EntityManager em;

	@EJB
	private UserBean userBean;

	@Override
	public void generateNew(String themeName, String themeDesc, Category category) {
		HauptThema newTheme = new HauptThema();
		newTheme.setThema(themeName);
		newTheme.setBeschreibung(themeDesc);
		Users user = em.find(Users.class, userBean.getUsername());
		newTheme.setUser(user);
		newTheme.setCategory(category);
		em.persist(newTheme);

	}

	@Override
	public void generateNewCat(String catName) {
		Category category = new Category();
		category.setName(catName);
		Users user = em.find(Users.class, userBean.getUsername());
		category.setUser(user);
		em.persist(category);
	}

	@Override
	public void deleteTheme(HauptThema hauptThema) {
		/*String jpql = "DELETE FROM " + HauptThema.class.getName() + " h WHERE h = :thema";
		TypedQuery<HauptThema> query = em.createQuery(jpql, HauptThema.class);
		query.setParameter("thema", hauptThema).executeUpdate();*/

		HauptThema toDelete = em.merge(hauptThema);
		em.remove(toDelete);

	}

	@Override
	public void deleteCat(Category category) {
		/*String jpql = "DELETE FROM " + HauptThema.class.getName() + " h WHERE h = :thema";
		TypedQuery<HauptThema> query = em.createQuery(jpql, HauptThema.class);
		query.setParameter("thema", hauptThema).executeUpdate();*/

		/*HauptThema toDelete = em.merge(hauptThema);
		em.remove(toDelete);*/

	}

	@Override
	public void renameCat(Category category, String newCatName) {
		Category cat2Change = em.merge(category);
		cat2Change.setName(newCatName);
		em.merge(cat2Change);
	}

	@Override
	public void renameTheme(HauptThema thema, String newName) {
		HauptThema theme2Change = em.merge(thema);
		theme2Change.setThema(newName);
		em.merge(theme2Change);
	}

	/* Wird momentan nicht gebraucht
	@Override
	public Category findCatByName(String catName) {
		TypedQuery<Category> query = em.createNamedQuery("findSpecialCategory", Category.class);
		String userName = userBean.getUsername();
		Users user = em.find(Users.class, userName);
		query.setParameter("passedUser", user);
		query.setParameter("passedCategory", catName);
		Category result = query.getSingleResult();
		return result;
	}*/

}

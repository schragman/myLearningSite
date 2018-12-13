package beans;

import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.HauptThema;

@Remote
public class ThemenSteuerung {

	@PersistenceContext(unitName = "LearningPU")
	private EntityManager em;

	public void generateNew(String themeName, String themeDesc) {
		HauptThema newTheme = new HauptThema();
		newTheme.setThema(themeName);
		newTheme.setBeschreibung(themeDesc);

		em.persist(newTheme);
	}

}

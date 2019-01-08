package beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.HauptThema;

@Stateless
@LocalBean
public class ThemenSteuerung implements ThemenSteuerungRemote {

	@PersistenceContext(unitName = "LearningPU")
	private EntityManager em;

	@Override
	public void generateNew(String themeName, String themeDesc) {
		HauptThema newTheme = new HauptThema();
		newTheme.setThema(themeName);
		newTheme.setBeschreibung(themeDesc);

		em.persist(newTheme);
	}

}

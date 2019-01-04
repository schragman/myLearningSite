package beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.TestEntry;

@Stateless
@LocalBean
public class ThemenSteuerung implements ThemenSteuerungRemote {

	@PersistenceContext(unitName = "LearningPU")
	private EntityManager em;

	public void generateAllNew(String themeN, String themeD) {
		generateNew(themeN, themeD);
	}

	@Override
	public void generateNew(String themeName, String themeDesc) {
//		HauptThema newTheme = new HauptThema();
//		newTheme.setThema(themeName);
//		newTheme.setBeschreibung(themeDesc);

		TestEntry newTheme = new TestEntry();
		// TestEntry ergebnis;
		newTheme.setEintrag(themeName);
		// newTheme.setId(2);

		em.persist(newTheme);
	}

}

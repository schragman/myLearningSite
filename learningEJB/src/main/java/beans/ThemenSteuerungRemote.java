package beans;

import entities.Category;
import entities.HauptThema;

import javax.ejb.Remote;

@Remote
public interface ThemenSteuerungRemote {

	void generateNew(String themeN, String themeD, Category category);

	void generateNewCat(String catName);

	void deleteTheme(HauptThema hauptThema);

	void deleteCat(Category category);

	void renameCat(Category category, String newName);

	void renameTheme(HauptThema thema, String newName);

	//Category findCatByName(String catName);

}

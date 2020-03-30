package beans;

import entities.HauptThema;

import javax.ejb.Remote;

@Remote
public interface ThemenSteuerungRemote {

	void generateNew(String themeN, String themeD);

	void deleteTheme(HauptThema hauptThema);

}

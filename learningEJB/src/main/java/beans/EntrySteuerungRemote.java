package beans;

import java.util.List;

import javax.ejb.Remote;

import entities.HauptThema;
import entities.MainEntry;
import entities.Referenz;
import sun.applet.Main;

@Remote
public interface EntrySteuerungRemote {

	public void generateNew(MainEntry mainEntry, HauptThema theme);

	public void updEntry(MainEntry mainEntry);

	public List<MainEntry> findEntries(HauptThema thema);

	public List<Referenz> findReferences();

	public List<String> findBeschreibungen(HauptThema theme);

	public void deleteEntry(MainEntry mainEntry);

}

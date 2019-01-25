package beans;

import java.util.List;

import javax.ejb.Remote;

import entities.HauptThema;
import entities.MainEntry;

@Remote
public interface EntrySteuerungRemote {

	public void generateNew(MainEntry mainEntry, HauptThema theme);

	public List<MainEntry> findEntries(HauptThema thema);

}

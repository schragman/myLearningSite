package beans;

import java.util.List;

import javax.ejb.Remote;

import entities.HauptThema;
import entities.MainEntry;
import entities.OIdSortable;
import entities.Referenz;

@Remote
public interface EntrySteuerungRemote {

	public MainEntry generateNew(MainEntry mainEntry);

	public void updEntry(MainEntry mainEntry);

	public List<MainEntry> findEntries(HauptThema thema);

	public List<Referenz> findReferences();

	public List<String> findBeschreibungen(HauptThema theme);

	public void deleteEntry(MainEntry mainEntry);

	public MainEntry findById(long id);

	public List<MainEntry> getEntryList(HauptThema theme);

	public void updateSortedEntries(List<? extends OIdSortable> entries);

}

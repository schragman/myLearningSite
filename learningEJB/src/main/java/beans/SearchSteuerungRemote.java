package beans;

import java.util.Collection;

import javax.ejb.Remote;

import entities.HauptThema;
import utils.SearchContainer;

@Remote
public interface SearchSteuerungRemote {

	public Collection<SearchContainer> findSearchEntries(HauptThema theme);
}

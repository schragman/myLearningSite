package beans;

import java.util.Collection;

import javax.ejb.Remote;

import entities.HauptThema;

@Remote
public interface SearchSteuerungRemote {

	public Collection<String> findSearchEntries(HauptThema theme);
}

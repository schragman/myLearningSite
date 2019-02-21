package beans;

import java.util.Collection;

import javax.ejb.Remote;

@Remote
public interface SearchSteuerungRemote {

	public Collection<String> findSearchEntries();
}

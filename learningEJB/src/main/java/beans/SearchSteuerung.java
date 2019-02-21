package beans;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.MainEntry;
import entities.Referenz;

@Stateless
@LocalBean
public class SearchSteuerung implements SearchSteuerungRemote {

	@PersistenceContext(unitName = "LearningPU")
	private EntityManager em;

	@Override
	public Collection<String> findSearchEntries() {

		Set<String> result = new HashSet<>();
		Query quRef = em.createNamedQuery("findAllReferences", Referenz.class);
		Query quBesch = em.createNamedQuery("findEntries", MainEntry.class);

		return result;
	}

}

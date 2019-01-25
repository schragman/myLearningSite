package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({ @NamedQuery(name = "findAllThemes", query = "SELECT t FROM HauptThema t"),
		@NamedQuery(name = "findEntries", query = "SELECT t.mainEntries FROM HauptThema t WHERE t.id = :passedID") })
public class HauptThema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	@Column(nullable = false)
	private String thema;
	@Column(length = 2000)
	private String beschreibung;

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
	@JoinColumn
	private List<MainEntry> mainEntries;
	private Date lastRecentlyUsed;

	public long getId() {
		return id;
	}

	public String getThema() {
		return thema;
	}

	public void setThema(String thema) {
		this.thema = thema;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public List<MainEntry> getMainEntries() {
		return mainEntries;
	}

	public void setMainEntries(List<MainEntry> mainEntries) {
		this.mainEntries = mainEntries;
	}

	public Date getLastRecentlyUsed() {
		return lastRecentlyUsed;
	}

	public void setLastRecentlyUsed(Date lastRecentlyUsed) {
		this.lastRecentlyUsed = lastRecentlyUsed;
	}

}

package entities;

import secEntities.Users;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({ //
		@NamedQuery(name = "findAllThemes", query = "SELECT t FROM HauptThema t where t.user = :passedUser"),
		@NamedQuery(name = "findThemesCat", query = "SELECT t FROM HauptThema t where t.category = :passedCategory"),
		@NamedQuery(name = "findThemesNotCat", query = "SELECT t FROM HauptThema t where t.category <> :passedCategory AND t.user = :passedUser"),
		@NamedQuery(name = "findReallyAllThemes", query = "SELECT t FROM HauptThema t"),
		@NamedQuery(name = "findEntries", query = "SELECT t.mainEntries FROM HauptThema t WHERE t.id = :passedID")

		// @NamedQuery(name = "findRefs", query = "SELECT r.uRefferenz1 FROM Referenz r
		// WHERE r.Referenzen_ID IN"
		// + "(SELECT m.id FROM MainEntry m WHERE m.mainEntries_ID = :passedID)")
})
public class HauptThema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	@Column(nullable = false)
	private String thema;
	@Column(length = 2000)
	private String beschreibung;

	@OneToMany(mappedBy = "hauptThema", cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
	private List<MainEntry> mainEntries;
	private Date lastRecentlyUsed;

	@ManyToOne
	@JoinColumn(name = "Users_Id")
	private Users user;

	@ManyToOne
	@JoinColumn(name = "Category_Id")
	private Category category;

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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}

package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Referenz implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	// z.B. Buch, URL, Tutorial, Enum vielleicht
	private boolean art; // true: Printmedium, false: Internet
	// Bei Buch Buchtitel für Internetquellen: Beschreibung
	private String uRefferenz1;

	// Bei Buch, Artikel usw. Seite und bei Internet URL
	// Bei Angabe einer Seite soll Art automatisch auf Buch springen,
	// Bei Angabe von http, https oder www. automatisch auf Internet.
	private String uRefferenz2;
	@ManyToMany
	private List<MainEntry> mainEntries;

	public long getId() {
		return id;
	}

	public boolean getArt() {
		return art;
	}

	public void setArt(boolean art) {
		this.art = art;
	}

	public List<MainEntry> getMainEntries() {
		return mainEntries;
	}

	public void setMainEntries(List<MainEntry> mainEntries) {
		this.mainEntries = mainEntries;
	}

	public String getuRefferenz1() {
		return uRefferenz1;
	}

	public void setuRefferenz1(String uRefferenz1) {
		this.uRefferenz1 = uRefferenz1;
	}

	public String getuRefferenz2() {
		return uRefferenz2;
	}

	public void setuRefferenz2(String uRefferenz2) {
		this.uRefferenz2 = uRefferenz2;
	}

}

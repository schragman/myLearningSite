package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Referenz {
	@Id
	@GeneratedValue
	private long id;
	// z.B. Buch, URL, Tutorial, Enum vielleicht
	private String art;
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

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

	public List<MainEntry> getMainEntries() {
		return mainEntries;
	}

	public void setMainEntries(List<MainEntry> mainEntries) {
		this.mainEntries = mainEntries;
	}

}

package entities;

import java.util.List;

//@Entity
public class Referenz {
	// @Id
	// @GeneratedValue
	private long id;
	// z.B. Buch, URL, Tutorial
	private String art;
	// @ManyToMany
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

package entities;

//@Entity
public class Antwort {
	// @Id
	// @GeneratedValue
	private long id;
	private String inhalt;

	public long getId() {
		return id;
	}

	public String getInhalt() {
		return inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}

}

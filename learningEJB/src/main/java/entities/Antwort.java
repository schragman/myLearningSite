package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Antwort {
	@Id
	@GeneratedValue
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

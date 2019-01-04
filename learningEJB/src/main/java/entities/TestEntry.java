package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TestEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue
	private long id;

	private String Eintrag;

	public long getId() {
		return id;
	}

	public String getEintrag() {
		return Eintrag;
	}

	public void setEintrag(String eintrag) {
		Eintrag = eintrag;
	}

}

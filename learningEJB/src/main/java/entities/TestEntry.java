package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String Eintrag;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEintrag() {
		return Eintrag;
	}

	public void setEintrag(String eintrag) {
		Eintrag = eintrag;
	}

}

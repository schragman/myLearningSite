package entities;

import java.util.Date;

//@Entity
public class Historie {
	// @Id
	// @GeneratedValue
	private long id;
	private Date durchlauf;
	private boolean korrekt;

	public long getId() {
		return id;
	}

	public Date getDurchlauf() {
		return durchlauf;
	}

	public void setDurchlauf(Date durchlauf) {
		this.durchlauf = durchlauf;
	}

	public boolean isKorrekt() {
		return korrekt;
	}

	public void setKorrekt(boolean korrekt) {
		this.korrekt = korrekt;
	}

}

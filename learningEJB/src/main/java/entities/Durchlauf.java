package entities;

import java.util.Date;

//Durchlauf von Fragen
//@Entity
public class Durchlauf {
	// @Id
	// @GeneratedValue
	private long id;
	private Date letzterDurchlauf;

	public long getId() {
		return id;
	}

	public Date getLetzterDurchlauf() {
		return letzterDurchlauf;
	}

	public void setLetzterDurchlauf(Date letzterDurchlauf) {
		this.letzterDurchlauf = letzterDurchlauf;
	}

}

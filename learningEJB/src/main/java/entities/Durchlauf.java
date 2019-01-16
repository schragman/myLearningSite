package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//Durchlauf von Fragen
@Entity
public class Durchlauf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
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

package entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Check {

	@Id
	@GeneratedValue
	private long id;
	private String frage;
	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
	@JoinColumn
	private List<Antwort> antworten;
	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
	@JoinColumn
	private List<Historie> historien;
	private Date letztesMalGestellt;

	public long getId() {
		return id;
	}

	public String getFrage() {
		return frage;
	}

	public void setFrage(String frage) {
		this.frage = frage;
	}

	public List<Antwort> getAntworten() {
		return antworten;
	}

	public void setAntworten(List<Antwort> antworten) {
		this.antworten = antworten;
	}

	public Date getLetztesMalGestellt() {
		return letztesMalGestellt;
	}

	public void setLetztesMalGestellt(Date letztesMalGestellt) {
		this.letztesMalGestellt = letztesMalGestellt;
	}

	public Date getLetztesMalRichtig() {
		return letztesMalRichtig;
	}

	public void setLetztesMalRichtig(Date letztesMalRichtig) {
		this.letztesMalRichtig = letztesMalRichtig;
	}

	private Date letztesMalRichtig;

}

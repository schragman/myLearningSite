package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Abfrage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	private String frage;
	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn
	private List<Antwort> antworten;

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
	@JoinColumn
	private List<Historie> historien;

	private Date letztesMalGestellt;
	private Date letztesMalRichtig;

	public List<Historie> getHistorien() {
		return historien;
	}

	public void setHistorien(List<Historie> historien) {
		this.historien = historien;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getFirstAntwort() {
		String lvResult = "";
		if (null != antworten && null != antworten.get(0))
			lvResult = antworten.get(0).getInhalt();
		return lvResult;
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

}

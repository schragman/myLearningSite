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
	@OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE},
			orphanRemoval = true,
			fetch = FetchType.EAGER)
	@JoinColumn
	private List<Antwort> antworten;

	//Gibt an, wie oft die Frage verwendet wird
	//1 -> jedes mal, 5 -> jedes 5. mal, 25 -> jedes 25. mal
	private int repetitionRate;

	//Gibt an, ob die Frage beim nächsten Lauf verwendet wird, zählt vom
	//repitionsrate herunter auf 1. Bei 1 wird sie verwendet
	private int repetitionCounter;

	/*@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
	@JoinColumn
	private List<Historie> historien;*/

	/*private Date letztesMalGestellt;
	private Date letztesMalRichtig;*/

	/*public List<Historie> getHistorien() {
		return historien;
	}

	public void setHistorien(List<Historie> historien) {
		this.historien = historien;
	}*/

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

	public int getRepetitionRate() {
		return repetitionRate;
	}

	public void setRepetitionRate(int repetitionRate) {
		this.repetitionRate = repetitionRate;
	}

	public int getRepetitionCounter() {
		return repetitionCounter;
	}

	public void setRepetitionCounter(int repetitionCounter) {
		this.repetitionCounter = repetitionCounter;
	}

	/*public Date getLetztesMalGestellt() {
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
	}*/

}

package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "findAllReferences", query = "SELECT r FROM Referenz r") // ,
})
public class Referenz implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	// z.B. Buch, URL, Tutorial, Enum vielleicht
	private boolean art; // true: Printmedium, false: Internet

	// Bei Buch Buchtitel für Internetquellen: URL
	// Bei Angabe von http, https oder www. soll art automatisch auf Internet.
	private String uRefferenz1;

	// Bei Buch, Artikel usw. Seite und bei Internet Beschreibung oder Abschnitt
	private String uRefferenz2;

	public long getId() {
		return id;
	}

	public boolean getArt() {
		return art;
	}

	public void setArt(boolean art) {
		this.art = art;
	}

	public String getuRefferenz1() {
		return uRefferenz1;
	}

	public void setuRefferenz1(String uRefferenz1) {
		this.uRefferenz1 = uRefferenz1;
	}

	public String getuRefferenz2() {
		return uRefferenz2;
	}

	public void setuRefferenz2(String uRefferenz2) {
		this.uRefferenz2 = uRefferenz2;
	}

	public int hashCode() {
		return uRefferenz1.hashCode();
	}

	public boolean equals(Object anObject) {
		String stObject = ((Referenz) anObject).getuRefferenz1();

		return uRefferenz1.equalsIgnoreCase(stObject);
	}

}

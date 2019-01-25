package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
//@NamedQuery(name = "findEntries", query = "SELECT e FROM MainEntry e WHERE HauptThema = :passedTheme")
public class MainEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	@Column(nullable = false)
	private String kurzEintrag;
	@Column(length = 20000)
	private String langEintrag;
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private List<Referenz> referenzen;
	// private String unterReferenz;
	// @OneToMany // (cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	// @JoinColumn
	private String beispiel;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn
	private List<Check> fragen;

	public List<Check> getFragen() {
		return fragen;
	}

	public void setFragen(List<Check> fragen) {
		this.fragen = fragen;
	}

	public long getId() {
		return id;
	}

	public String getKurzEintrag() {
		return kurzEintrag;
	}

	public void setKurzEintrag(String kurzEintrag) {
		this.kurzEintrag = kurzEintrag;
	}

	public String getLangEintrag() {
		return langEintrag;
	}

	public void setLangEintrag(String langEintrag) {
		this.langEintrag = langEintrag;
	}

	public List<Referenz> getReferenzen() {
		return referenzen;
	}

	public void setReferenzen(List<Referenz> referenzen) {
		this.referenzen = referenzen;
	}

//	public String getUnterReferenz() {
//		return unterReferenz;
//	}
//
//	public void setUnterReferenz(String unterReferenz) {
//		this.unterReferenz = unterReferenz;
//	}

	public String getBeispiel() {
		return beispiel;
	}

	public void setBeispiel(String beispiel) {
		this.beispiel = beispiel;
	}

}

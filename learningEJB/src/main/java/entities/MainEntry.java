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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
		@NamedQuery(name = "findKBeschreibungen", query = "SELECT m.kurzEintrag FROM MainEntry m WHERE m.hauptThema = :passedTheme"),
		@NamedQuery(name = "findBeschreibungen", query = "SELECT m FROM MainEntry m WHERE m.hauptThema = :passedTheme"),
		@NamedQuery(name = "findReferences", query = "SELECT m.referenzen FROM MainEntry m WHERE m.hauptThema = :passedTheme")//
})
public class MainEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	//@Column(nullable = false)
	private String kurzEintrag;
	@Column(length = 8000)
	private String langEintrag;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn
	private List<Referenz> referenzen;
	@Column(length = 80000)
	private String beispiel;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn
	private List<Abfrage> abfragen;

	@ManyToOne
	@JoinColumn(name = "HauptThema_ID")
	private HauptThema hauptThema;

	public HauptThema getHauptThema() {
		return hauptThema;
	}

	public void setHauptThema(HauptThema hauptThema) {
		this.hauptThema = hauptThema;
	}

	public List<Abfrage> getAbfragen() {
		return abfragen;
	}

	public void setAbfragen(List<Abfrage> fragen) {
		this.abfragen = fragen;
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
	
//	public int hashCode() {
//		return Long.hashCode(id);
//	}
//	
//	public boolean equals(Object anObject) {
//		long objectId = ((MainEntry) anObject).getId();
//		
//		return id == objectId;
//	}

}

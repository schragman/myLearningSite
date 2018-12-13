package entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class MainEntry {

	@Id
	@GeneratedValue
	private long id;
	@Column(nullable = false)
	private String kurzEintrag;
	@Column(length = 20000)
	private String langEintrag;
	@ManyToMany
	private List<Referenz> referenzen;
	private String unterReferenz;
	private List<String> beispiele;

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

	public String getUnterReferenz() {
		return unterReferenz;
	}

	public void setUnterReferenz(String unterReferenz) {
		this.unterReferenz = unterReferenz;
	}

	public List<String> getBeispiele() {
		return beispiele;
	}

	public void setBeispiele(List<String> beispiele) {
		this.beispiele = beispiele;
	}

}

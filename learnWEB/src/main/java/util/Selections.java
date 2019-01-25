package util;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import entities.HauptThema;
import entities.MainEntry;

@SessionScoped
@Named
public class Selections implements Serializable {

	private static final long serialVersionUID = 1L;
	private HauptThema thema;
	private MainEntry entry;

	public MainEntry getEntry() {
		return entry;
	}

	public void setEntry(MainEntry entry) {
		this.entry = entry;
	}

	public HauptThema getThema() {
		return thema;
	}

	public void setThema(HauptThema thema) {
		this.thema = thema;
	}

}

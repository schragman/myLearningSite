package controller;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import beans.AusgabeTest;

@Named
public class HomeForm {

	private String ausgabe;

	@Inject
	private AusgabeTest ausgabeTest;

	@PostConstruct
	public void init() {
		ausgabe = "Nur ein Beispieltext";
	}

	public String getAusgabe() {
		return ausgabeTest.getWriteTest();
	}

	public void setAusgabe(String ausgabe) {
		this.ausgabe = ausgabe;
	}

}

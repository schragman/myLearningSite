package controller;


import beans.AbfrageSteuerung;
import entities.Abfrage;
import entities.HauptThema;
import util.Selections;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@ManagedBean
public class QueryScanForm implements Serializable {

  private static final long serialVersionUID = 1L;

  @EJB
  private AbfrageSteuerung abfrageSteuerung;

  @Inject
  private Selections selections;

  private boolean questionsPresent;

  private List<Abfrage> currentQuestions;

  private Abfrage currentAbfrage;
  private String userAnswer;

  @PostConstruct
  public void init() {
    this.questionsPresent=true;
  }


  public void doStart() {
    HauptThema theme = this.selections.getThema();
    List<Abfrage> fragen = abfrageSteuerung.getFragen(theme);
    if (null == fragen) {
      this.questionsPresent = false;
    } else {
      fragen.stream()
          .filter(frage -> frage.getRepetitionCounter() > 1)
          .forEach(frage -> {
              frage.setRepetitionCounter(frage.getRepetitionCounter() - 1);
              abfrageSteuerung.updAbfrage(frage);
            }
          );
      currentQuestions = fragen.stream()
          .filter(f -> f.getRepetitionCounter() == 1)
          .collect(Collectors.toList());

      currentAbfrage = currentQuestions.get(5);
      currentQuestions.remove(5);

      System.out.println("Debug");
    }
  }

  public boolean isQuestionsPresent() {
    return questionsPresent;
  }

  public void getNextQuestion() {

  }

  public String getCurrentQuestion() {
    return null == currentAbfrage ? "" : currentAbfrage.getFrage();
  }

  public String getCurrentAnswer() {
    return null == currentAbfrage ? "" : currentAbfrage.getFirstAntwort();
  }

  public String getUserAnswer() {
    return userAnswer;
  }

  public void setUserAnswer(String userAnswer) {
    this.userAnswer = userAnswer;
  }
}

package controller;


import beans.AbfrageSteuerung;
import entities.Abfrage;
import entities.Antwort;
import util.InternMessages;
import util.Selections;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Locale;

@RequestScoped
@Named
public class QuestionForm {

  @Inject
  private Selections selections;

  @EJB
  private AbfrageSteuerung abfrageSteuerung;

  private Abfrage currentQuestion;
  private String newQestion;

  @PostConstruct
  public void init() {
    currentQuestion = selections.getSelectedQuestion();
  }

  public void doOk() {
    if (newQestion != null && !newQestion.isEmpty()) {
      currentQuestion.setFrage(newQestion);
    }
    abfrageSteuerung.updAbfrage(this.currentQuestion);
    /*Abfrage ergebnis = this.currentQuestion;
    List<Antwort> antworten = ergebnis.getAntworten();*/
  }

  public String getHeading() {
    if (null == currentQuestion) {
      return null;
    }
    Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    String result = InternMessages.getMessageForLocaleWParam("quDetails", locale, currentQuestion.getFrage());
    return result;
  }

  public boolean isAnswerPresent() {
    if (currentQuestion == null) {
      return false;
    }
    List<Antwort> antworten = currentQuestion.getAntworten();
    if (antworten == null || antworten.size() == 0) {
      return false;
    }
    return true;
  }

  public String getCurrentQuestionContent() {
    return currentQuestion.getFrage();
  }

  public String getNewQestion() {
    return newQestion;
  }

  public void setNewQestion(String newQestion) {
    this.newQestion = newQestion;
  }

  public List<Antwort> getAnswers() {
    return null == currentQuestion ? null : currentQuestion.getAntworten();
  }

}

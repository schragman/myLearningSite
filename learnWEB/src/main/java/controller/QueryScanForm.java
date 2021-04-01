package controller;


import beans.AbfrageSteuerung;
import entities.Abfrage;
import entities.Antwort;
import entities.HauptThema;
import util.InternMessages;
import util.Selections;
import utils.Konstanten;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
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
  private boolean walkthroughInitial;
  private boolean possibleAnswersShown;
  private boolean walkthroughEnded;

  private List<Abfrage> currentQuestions, fragen;

  private Abfrage currentAbfrage;
  private String userAnswer;

  @PostConstruct
  public void init() {
    HauptThema theme = this.selections.getThema();
    fragen = abfrageSteuerung.getFragen(theme);
    walkthroughInitial = true;
    walkthroughEnded = false;
    possibleAnswersShown = false;

    if (null == fragen || fragen.size() == 0) {
      this.questionsPresent = false;
    } else {
      this.questionsPresent = true;
      currentQuestions = fragen.stream()
          .filter(f -> f.getRepetitionCounter() == 1)
          .collect(Collectors.toList());
    }
  }

  private void resetQuestions() {

    fragen.stream()
        .filter(frage -> frage.getRepetitionCounter() > 1)
        .forEach(frage -> {
          frage.setRepetitionCounter(frage.getRepetitionCounter() - 1);
          abfrageSteuerung.updAbfrage(frage);
        });
  }

  public void doOk() {
    resetQuestions();
    currentQuestions = fragen.stream()
        .filter(f -> f.getRepetitionCounter() == 1)
        .collect(Collectors.toList());
  }

  public String doStart() {
    currentQuestions = fragen.stream()
        .filter(f -> f.getRepetitionCounter() == 1)
        .collect(Collectors.toList());

    resetQuestions();
    this.walkthroughInitial = false;
    getNextQuestion();
    System.out.println("Debug");
    return null;
  }

  //Ist die Antwort ok, kommt die Frage nicht mehr so schnell
  private void doAnswerOk() {
    int currentRepRate = currentAbfrage.getRepetitionRate();
    currentRepRate *= Konstanten.REPRATEMULITPLY;
    currentAbfrage.setRepetitionRate(currentRepRate);
    currentAbfrage.setRepetitionCounter(currentRepRate);
    abfrageSteuerung.updAbfrage(currentAbfrage);
    userAnswer = "";
    getNextQuestion();
  }

  private void getNextQuestion() {
    long seed = LocalDateTime.now().getNano() / 1000;
    Random zufall = new Random(seed);
    int anzahlFragen = currentQuestions.size();
    int zufallszahl;
    if (anzahlFragen == 0) {
      walkthroughEnded = true;
      walkthroughInitial = true;
    } else {
      if (anzahlFragen == 1) {
        zufallszahl = 0;
      } else {
        zufallszahl = zufall.nextInt(anzahlFragen - 1);
      }
      currentAbfrage = currentQuestions.get(zufallszahl);
      currentQuestions.remove(zufallszahl);
    }
  }

  public boolean isQuestionsPresent() {
    return questionsPresent;
  }

  //Antwort eingeben
  public String doAnswer() {
    /*Vergleich muss so laufen, dass Leerzeichen, Kommata, Punkte, Hochkommata, Ausrufezeichen und Fragezeichen
    unberücksichtigt bleiben*/
    String evaluateUserAnswer = userAnswer.replaceAll("[,.'?!\" ]", "").trim().toLowerCase();
    for (Antwort possAnswer : currentAbfrage.getAntworten()) {
      String possAnswerBereinigt = possAnswer.getInhalt().replaceAll("[,.'!?\" ]", "").trim().toLowerCase();
      if (evaluateUserAnswer.equals(possAnswerBereinigt)) {
        doAnswerOk();
        return null;
      }
    }
    possibleAnswersShown = true;
    return null;
  }

  public String doYes() {
    List<Antwort> oldAnwsers = currentAbfrage.getAntworten();
    Antwort newAntwort = new Antwort();
    newAntwort.setInhalt(userAnswer);
    oldAnwsers.add(newAntwort);
    doAnswerOk();
    this.possibleAnswersShown = false;
    return null;
  }

  public String doNo() {
    int currentRepRate = currentAbfrage.getRepetitionRate();
    currentRepRate /= Konstanten.REPRATEMULITPLY;
    currentAbfrage.setRepetitionRate(currentRepRate);
    currentAbfrage.setRepetitionCounter(1);
    abfrageSteuerung.updAbfrage(currentAbfrage);
    userAnswer = "";
    getNextQuestion();
    this.possibleAnswersShown = false;
    return null;
  }

  public void doQuit() {
    this.walkthroughEnded = false;
  }

  public int getNumberOfQuestions() {
    if (!questionsPresent) {
      return 0;
    }
    return currentQuestions.size();
  }

  public String getCurrentQuestion() {
    return null == currentAbfrage ? "" : currentAbfrage.getFrage();
  }

  public List<Antwort> getCurrentAnswers() {
    return null == currentAbfrage ? null : currentAbfrage.getAntworten();
  }

  //Für die Nummer-Anzeige im Sheet
  public int getRowNumber(Antwort seletedAntwort) {
    int result = 1;
    if (currentAbfrage == null) {
      return 0;
    }
    for (Antwort antwort : currentAbfrage.getAntworten()) {
      if (antwort.getId() == seletedAntwort.getId()) {
        break;
      } else {
        result++;
      }
    }
    return result;
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

  public boolean isWalkthroughInitial() {
    return this.walkthroughInitial;
  }

  public boolean isPossibleAnswersShown() {
    return possibleAnswersShown;
  }

  public String getMsgQuestions() {
    String result;
    Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    if (this.questionsPresent) {
      if (walkthroughEnded) {
        result = InternMessages.getMessageForLocale("walkthroughEndedNormally", locale);
      } else {
        Integer nbQuestions = this.getNumberOfQuestions();
        if (nbQuestions == 0) {
          result = InternMessages.getMessageForLocale("noCurrentQuestions", locale);
        } else {
          result = InternMessages.getMessageForLocaleWParam("nbCurrentQuestions", locale, nbQuestions.toString());
        }
      }
    } else {
      result = InternMessages.getMessageForLocale("noQuestions", locale);
    }
    return result;
  }

  public boolean isWalkthroughEnded() {
    return walkthroughEnded;
  }
}

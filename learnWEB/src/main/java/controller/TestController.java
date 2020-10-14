package controller;


import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class TestController {

  private String testOutput;
  private String testInput;

  @PostConstruct
  public void init() {
    this.testOutput = "Noch nichts";
  }

  public void doUebertrag() {
    this.testOutput = this.testInput;
  }

  public String getTestOutput() {
    return testOutput;
  }

  public String getTestInput() {
    return testInput;
  }

  public void setTestInput(String testInput) {
    this.testInput = testInput;
  }
}

package controller;

import beans.UpdateBean;
import util.Sites;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class Update {

  private boolean updatable;
  private String updateText;
  private static final String NO_UPDATES = "No Updates awailabe";
  @EJB
  private UpdateBean updateBean;

  @PostConstruct
  public void init() {
    updateText = "Update MainEntry-Table";
    updatable = !updateText.equals(NO_UPDATES);
  }

  public boolean isUpdatable() {
    return updatable;
  }

  public String getUpdateText() {
    return updateText;
  }

  public void doUpdate() {
    updateBean.updateMainEntryTable();
  }

  public String doGotoUpdate() {
    return Sites.UPDATEPAGE;
  }

}

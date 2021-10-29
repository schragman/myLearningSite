package controller;

import beans.EntrySteuerung;
import entities.MainEntry;
import util.Selections;
import util.Sites;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named
public class MainEntryOrderFormBean {

  @Inject
  private Selections selections;

  @Inject
  private OrderView orderView;

  @EJB
  private EntrySteuerung entrySteuerung;


  public void doOrderEntries() {
    List<MainEntry> entries = entrySteuerung.findEntries(selections.getThema());
    orderView.setOrderedList(entries, Sites.UEBERSICHT);
    //selections.setSortables(entries);

  }

  public String doOk() {
    orderView.fixOrder(selections.getThema().getId() + "_");
    entrySteuerung.updateSortedEntries(orderView.getOrderedList());
    return orderView.endConversation();
  }

  public String doCancel() {
    return orderView.endConversation();
  }
}

package controller;

import entities.MainEntry;
import entities.OIdSortable;
import org.apache.myfaces.tobago.component.UIPopup;
import util.Selections;
import util.Sites;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ConversationScoped
@Named
public class OrderView implements Serializable {

  private List<? extends OIdSortable> orderedList;
  private static final long serialVersionUID = 5L;
  @Inject
  private Conversation conversation;
  private String returnValue;
  private UIPopup orderViewPopup;

  public void setOrderedList(List<? extends OIdSortable> orderedList, String returnValue) {
    conversation.begin();
    this.orderedList = orderedList;
    this.returnValue = returnValue + Sites.DORELOAD;
  }

  public List<? extends OIdSortable> getOrderedList() {
    return orderedList;
  }

  public String endConversation() {
    this.conversation.end();
    //orderViewPopup.setCollapsed(true);
    return Sites.UEBERSICHT + Sites.DORELOAD;
  }

  public boolean hasPrevious(OIdSortable entry) {
    int position = orderedList.indexOf(entry);
    return position != 0;
  }

  public boolean hasSuccessor(OIdSortable entry) {
    int position = orderedList.indexOf(entry);
    return position < orderedList.size() - 1;
  }

  public void doGoUp(OIdSortable entry) {
    int position = orderedList.indexOf(entry);
    Collections.swap(orderedList, position - 1, position);
  }

  public void doGoDown(OIdSortable entry) {
    int position = orderedList.indexOf(entry);
    Collections.swap(orderedList, position + 1, position);
  }

  public void fixOrder(String superId) {
    for (OIdSortable entry : orderedList) {
      entry.setOrderId(superId + String.format("%05d", orderedList.indexOf(entry)));
    }
  }

  public UIPopup getOrderViewPopup() {
    return orderViewPopup;
  }

  public void setOrderViewPopup(UIPopup orderViewPopup) {
    this.orderViewPopup = orderViewPopup;
  }
}

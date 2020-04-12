package ejb.beans;

import beans.AusgabeBean;
import entities.HauptThema;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import util.TestBaseClass;

import javax.inject.Inject;
import java.util.List;

//@RunWith(Arquillian.class)
public class AusgabeBeanTest extends TestBaseClass {

  @Inject
  private AusgabeBean ausgabeBean;

  /*@Test
  public void testFindThemes() {

    List<HauptThema> themes = ausgabeBean.findThemes();

  }*/

}

package secEntities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Konfiguration implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private long id;

  // Anzahl Zeilen im Beschreibungsfeld
  private int decrRows;

  // Anzahl Zeilen im Beispielfeld
  private int sampleRows;

  public int getDecrRows() {
    return decrRows;
  }

  public void setDecrRows(int decrRows) {
    this.decrRows = decrRows;
  }

  public int getSampleRows() {
    return sampleRows;
  }

  public void setSampleRows(int sampleRows) {
    this.sampleRows = sampleRows;
  }

  public void setDefaults(){
    this.sampleRows = 10;
    this.decrRows = 10;
  }
}

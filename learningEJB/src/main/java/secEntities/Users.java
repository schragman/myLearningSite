package secEntities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Users implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(length = 255)
  private String userName;

  @Column(length = 30)
  private String password;

  @Column(length = 30)
  private String rolle;

  @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
  @JoinColumn(name = "Config_Id")
  private Konfiguration config;

  @Column(name = "LABEL_ID")
  private long labelId;

  public int hashCode() {
    return (this.userName == null) ? 0 : this.userName.hashCode();
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Users other = (Users) obj;
    if (this.userName == null) {
      return other.userName == null;
    }
    return this.userName.equalsIgnoreCase(other.userName);
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRolle() {
    return rolle;
  }

  public void setRolle(String rolle) {
    this.rolle = rolle;
  }

  public Konfiguration getConfig() {
    return config;
  }

  public void setConfig(Konfiguration config) {
    this.config = config;
  }

  public long getLabelId() {
    return labelId;
  }

  public void setLabelId(long labelId) {
    this.labelId = labelId;
  }
}

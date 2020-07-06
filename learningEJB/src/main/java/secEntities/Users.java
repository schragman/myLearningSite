package secEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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


}

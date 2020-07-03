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
  @GeneratedValue
  private int userId;

  @Column(length = 255)
  private String userName;

  @Column(length = 30)
  private String password;

  @Column(length = 30)
  private String rolle;


}

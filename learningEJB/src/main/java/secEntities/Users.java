package secEntities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Users {

  @Id
  @GeneratedValue
  private int userId;

  private String userName;

  private String password;

  private String rolle;


}

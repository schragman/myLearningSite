package entities;

import secEntities.Users;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({ //
		@NamedQuery(name = "findAllCategories", query = "SELECT c FROM Category c where c.user = :passedUser")
})
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue

	private long id;
	@Column(nullable = false)
	private String name;

	/*@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "Category_Id")
	private List<HauptThema> themen;*/

	@ManyToOne
	@JoinColumn(name = "Users_Id")
	private Users user;


	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public List<HauptThema> getThemen() {
		return themen;
	}

	public void setThemen(List<HauptThema> themen) {
		this.themen = themen;
	}*/

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}

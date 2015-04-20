package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the manager database table.
 * 
 */
@Entity
@Table(name="manager")
public class Manager implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idManager;
	private String name;
	private String password;

	public Manager() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	public int getIdManager() {
		return this.idManager;
	}

	public void setIdManager(int idManager) {
		this.idManager = idManager;
	}


	@Column(length=45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(length=45)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the place database table.
 * 
 */
@Entity
@Table(name="place")
public class Place implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idPlace;

	@Column(length=45)
	private String availability;

	private int seatNumber;
	
	private String name;

	//bi-directional many-to-one association to Play
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idPlay")
	private Play play;

	//bi-directional many-to-one association to User
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idUser")
	private User user;

	public Place() {
		if(user!=null)
			setName(user.getName());
	}

	public int getIdPlace() {
		return this.idPlace;
	}

	public void setIdPlace(int idPlace) {
		this.idPlace = idPlace;
	}

	public String getAvailability() {
		return this.availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public int getSeatNumber() {
		return this.seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Play getPlay() {
		return this.play;
	}

	public void setPlay(Play play) {
		this.play = play;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
		if(user!=null)
		setName(user.getName());
		else
	    setName(null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

}
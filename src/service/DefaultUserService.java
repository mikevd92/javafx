package service;

import javafx.collections.ObservableList;
import model.Place;
import model.Play;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import aspects.ControlAspect;
import aspects.ObserverPattern.Observer;
import aspects.ObserverPattern.Subject;
import aspects.SubjectChanged;
import exceptions.AppException;
import persistence.PlaceDAO;
import persistence.PlayDAO;
import persistence.UserDAO;

@Service("userService")
public class DefaultUserService implements
		UserService {

	@Autowired private UserDAO userDAO;
	@Autowired private PlayDAO userPlayDAO;
	@Autowired private PlaceDAO userPlaceDAO;
	
	@Override
	@Transactional
	public boolean checkUser(String name, String pass) throws AppException {
		return userDAO.findUserByNameAndPass(name, pass)!=null;
	}
	
	@Override
	@Transactional
	public void addUser(String name, String pass, String address, String dob) throws AppException {
		userDAO.createUser(name, pass, address, dob);
	}

	@Override
	@Transactional
	public ObservableList<Place> findByShowID(int index) throws AppException {
		ObservableList<Place> places=userPlaceDAO.findByShowID(index);
		return places;
	}

	@Override
	@Transactional
	public void changeAvailability(int index, String availability,String name)
			throws AppException {
		if(!availability.equals("available"))
			throw new AppException("Service Exception: Unavailable");
		availability=new String("unavailable");
		User user=(User)this.userDAO.findUserByName(name);
		this.userPlaceDAO.changeAvailability(index, availability,user);
		notifyObservers(this);
	}
	@Override
	@Transactional
	public void cancelAvailability(int index, String availability)
			throws AppException {
		if(!availability.equals("unavailable"))
			throw new AppException("Service Exception: Can not cancel Available");
		availability=new String("available");
		this.userPlaceDAO.changeAvailability(index, availability);
		notifyObservers(this);
	}
	
	@Override
	@Transactional
	public ObservableList<Play> displayPlays() throws AppException {
		ObservableList<Play> plays=userPlayDAO.displayPlays();
		return plays;
	}

	@Override
	public void AddObserver(Observer o){
		ControlAspect.aspectOf().addObserver(this, o);
	}
	@Override
	public void update(Subject s) {
	
	}

	@Override
	@SubjectChanged
	public void notifyObservers(UserService service) {
		
	}

	
}

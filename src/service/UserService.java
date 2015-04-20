package service;

import aspects.ObserverPattern.Observer;
import aspects.ObserverPattern.Subject;
import javafx.collections.ObservableList;
import model.Place;
import model.Play;
import exceptions.AppException;

public interface UserService extends Subject {
	public boolean checkUser(String name,String pass) throws AppException;
	public void addUser(String name, String pass,String address,String dob) throws AppException;
	ObservableList<Place> findByShowID(int index) throws AppException;
	void AddObserver(Observer o);
	void update(Subject s);
	void notifyObservers(UserService service);
	ObservableList<Play> displayPlays() throws AppException;
	void changeAvailability(int index, String availability, String name)
			throws AppException;
	void cancelAvailability(int index, String availability)
			throws AppException;
}

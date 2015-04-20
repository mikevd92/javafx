package service;

import aspects.ObserverPattern.Observer;
import aspects.ObserverPattern.Subject;
import javafx.collections.ObservableList;
import model.Play;
import exceptions.AppException;

public interface ManagerService extends Subject {
	public boolean checkManager(String name,String pass)throws AppException;
	void addPlay(String playName, String startDate, String startTime,String endTime, String ticketPrice) throws AppException;
	void removePlay(int index) throws AppException;
	void updatePlay(int index, String playName, String startDate,String startTime, String endTime, String ticketPrice)throws AppException;
	ObservableList<Play> displayPlays() throws AppException;
	void AddObserver(Observer o);
	void update(Subject s);
	void notifyObservers(ManagerService service);
}

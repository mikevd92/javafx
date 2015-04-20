package persistence;

import javafx.collections.ObservableList;
import exceptions.AppException;
import model.Place;
import model.Play;
import model.User;

public interface PlaceDAO {
	public void addPlaces(int amount,Play play) throws AppException;

	void changeAvailability(int index, String availability, User user)
			throws AppException;

	ObservableList<Place> findByShowID(int index) throws AppException;

	void changeAvailability(int index, String availability) throws AppException;
}

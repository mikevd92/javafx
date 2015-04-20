package persistence;

import javafx.collections.ObservableList;
import model.Play;
import exceptions.AppException;

public interface PlayDAO {	
public void addPlay(String playName,String startDate,String startTime,String endTime,String ticketPrice) throws AppException;
public void removePlay(int index) throws AppException;
public void updatePlay(int index,String playName,String startDate,String startTime,String endTime,String ticketPrice) throws AppException;
public ObservableList<Play> displayPlays() throws AppException;
public boolean checkDateTime(String startDate,String startTime,String endTime) throws AppException;
}

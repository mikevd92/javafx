package service;

import javafx.collections.ObservableList;

import model.Play;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aspects.ControlAspect;
import aspects.ObserverPattern.Observer;
import aspects.ObserverPattern.Subject;
import aspects.SubjectChanged;
import exceptions.AppException;
import persistence.ManagerDAO;
import persistence.PlayDAO;

@Service("mngService")
public class DefaultManagerService implements ManagerService {
	
	@Autowired private ManagerDAO mngDAO;
	@Autowired private PlayDAO mngPlayDAO;
	
	@Override
	@Transactional
	public boolean checkManager(String name, String pass) throws AppException {
		return mngDAO.findManagerByNameAndPass(name, pass)!=null;
	}
	@Override
	@Transactional
	public void addPlay(String playName, String startDate,
			String startTime, String endTime, String ticketPrice) throws AppException {
		mngPlayDAO.addPlay(playName, startDate, startTime, endTime, ticketPrice);
		this.notifyObservers(this);
	}

	@Override
	@Transactional
	public void removePlay(int index) throws AppException {
		mngPlayDAO.removePlay(index);
		this.notifyObservers(this);
	}

	@Override
	@Transactional
	public void updatePlay(int index, String playName,String startDate, String startTime, String endTime,String ticketPrice)
			throws AppException {
		mngPlayDAO.updatePlay(index, playName, startDate, startTime, endTime, ticketPrice);
		this.notifyObservers(this);
	}

	@Override
	@Transactional
	public ObservableList<Play> displayPlays() throws AppException {
		ObservableList<Play> plays=mngPlayDAO.displayPlays();
		return plays;
	}
	@Override
	public void AddObserver(Observer o){
		ControlAspect.aspectOf().addObserver(this,o);
	}
	@Override
	public void update(Subject s) {
	}

	@Override
	@SubjectChanged
	public void notifyObservers(ManagerService service) {
		
	}
}

package persistence;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import exceptions.AppException;
import model.Place;
import model.Play;
import model.User;
@Repository("mngPlaceDAO")
public class JpaPlaceDAO extends JpaDAO<Place,Integer> implements PlaceDAO {
	private ArrayList<Place> places;
	private Place place;
	public JpaPlaceDAO() throws AppException {
		super();
	}
	@Override
	@Transactional
	@CacheEvict(value="places",allEntries=true)
	public void addPlaces(int amount, Play play) throws AppException {
		places=new ArrayList<Place>();
		for(int i=1;i<=amount;i++)
		{
			Place place=new Place();
			place.setPlay(play);
			place.setSeatNumber(i);
			place.setAvailability("available");
			this.save(place);
			places.add(place);
		}
		play.setPlaces(places);
	}
	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value="places",key="index")
	public ObservableList<Place> findByShowID(int index) throws AppException {
		Query q;
		try{
		Play play=entityManager.find(Play.class,index);
		q=entityManager.createQuery("SELECT X FROM "+entityClass.getName()+" X WHERE X.play=:index");
		q.setParameter("index", play);
		}catch(Exception ex){
			throw new AppException("DB Exception: "+ex.getMessage());
		}
		return FXCollections.observableArrayList(q.getResultList());
	}
	@Override
	@Transactional
	public void changeAvailability(int index, String availability,User user) throws AppException {
		place=this.findById(index);
		place.setAvailability(availability);
		place.setUser(user);
	}
	@Override
	@Transactional
	public void changeAvailability(int index, String availability) throws AppException {
		place=this.findById(index);
		place.setAvailability(availability);
		place.setUser(null);	
	}
}

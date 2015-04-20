package persistence;

import java.sql.Date;
import java.sql.Time;
import javafx.collections.ObservableList;
import javax.persistence.Query;
import model.Play;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import exceptions.AppException;

@Repository("mngPlayDAO")
public class JpaPlayDAO extends JpaDAO<Play, Integer> implements
		PlayDAO {

	@Autowired
	PlaceDAO mngPlaceDAO;

	public JpaPlayDAO() throws AppException {
		super();
	}

	@Override
	@Transactional
	@CacheEvict(value="plays",key="index")
	public void removePlay(int index) throws AppException {
		Play play = this.findById(index);
		try{
		this.remove(play);
		
		}catch(Exception ex){
			throw new AppException("DB Exception: "+ex.getMessage());
		}
	}

	@Override
	@Transactional
	public void updatePlay(int index, String playName, String startDate,
			String startTime, String endTime, String ticketPrice)
			throws AppException {
		int priceTicket = 0;
		Play play = this.findById(index);
		if(playName!=null&&!playName.equals(""))
		play.setPlayName(playName);
		String date;
		String sTime;
		String eTime;
		if(startDate!=null&&!startDate.equals(""))
			date=startDate;
		else
			date=play.getStartDate().toString();
		if(startTime!=null&&!startTime.equals(""))
			sTime=startTime;
		else
			sTime=play.getStartTime().toString();
		if(endTime!=null&&!endTime.equals(""))
			eTime=endTime;
		else
			eTime=play.getEndTime().toString();
		if(((!sTime.equals(play.getStartTime().toString())&&(!eTime.equals(play.getEndTime().toString()))||(!date.equals(play.getStartDate().toString()))))){
		if (!checkDateTime(date, sTime, eTime))
			throw new AppException("DB Exception : OverImposed Play Schedule");
		}
		try {
			if(startDate!=null&&!startDate.equals("")){
			Date start = Date.valueOf(startDate);
			play.setStartDate(start);
			}
		} catch (Exception e) {
			throw new AppException("DB Exception : Invalid Date Format");
		}
		try {
			if(startTime!=null&&!startTime.equals("")){
			Time timeStart = Time.valueOf(startTime);
			play.setStartTime(timeStart);
			}
		} catch (Exception e) {
			throw new AppException("DB Exception : Invalid Time Format");
		}
		try {
			if(endTime!=null&&!endTime.equals("")){
			Time timeEnd = Time.valueOf(endTime);
			play.setEndTime(timeEnd);
			}
		} catch (Exception e) {
			throw new AppException("DB Exception : Invalid Time Format");
		}
			if(ticketPrice!=null&&!ticketPrice.equals("")){
			priceTicket=Integer.parseInt(ticketPrice);
			if(priceTicket>=0)
			play.setTicketPrice(priceTicket);
			else
				throw new AppException("DB Exception : Negative Ticket");
			}
	}

	@Override
	@Transactional
	@CacheEvict(value="plays",key="playName")
	public void addPlay(String playName, String startDate, String startTime,
			String endTime, String ticketPrice) throws AppException {
		Play play = new Play();
		play.setPlayName(playName);
		if (!checkDateTime(startDate, startTime, endTime))
			throw new AppException("DB Exception : OverImposed Play Schedule");
		try {
			Date start = Date.valueOf(startDate);
			play.setStartDate(start);
		} catch (Exception e) {
			throw new AppException("DB Exception : Invalid Date Format");
		}
		try {
			Time timeStart = Time.valueOf(startTime);
			play.setStartTime(timeStart);
		} catch (Exception e) {
			throw new AppException("DB Exception : Invalid Time Format");
		}
		try {
			Time timeEnd = Time.valueOf(endTime);
			play.setEndTime(timeEnd);
		} catch (Exception e) {
			throw new AppException("DB Exception : Invalid Time Format");
		}
		if(ticketPrice!=null && !ticketPrice.equals("")){
			int priceTicket=Integer.parseInt(ticketPrice);
			if(priceTicket>0)
			play.setTicketPrice(priceTicket);
			else
				throw new AppException("DB Exception : Negative Ticket");
			}
			this.save(play);
			mngPlaceDAO.addPlaces(200, play);
	}

	@Override
	@Transactional
	@Cacheable(value="plays")
	public ObservableList<Play> displayPlays() throws AppException {
		ObservableList<Play> plays = this.findAll();
		return plays;
	}

	@Override
	@Transactional
	public boolean checkDateTime(String dateStart, String timeStart,
			String timeEnd) throws AppException {
		Long result = new Long(0);
		Date ds = Date.valueOf(dateStart);
		Time ts = Time.valueOf(timeStart);
		Time te = Time.valueOf(timeEnd);
		try {
			Query query = entityManager
					.createQuery("SELECT COUNT(X) FROM "
							+ entityClass.getName()
							+ " X WHERE X.startDate=:ds AND ( (X.startTime<=:ts OR x.endTime<=:te) AND (X.startTime>=:ts OR x.endTime>=:te) ) )");
			query.setParameter("ds", ds);
			query.setParameter("ts", ts);
			query.setParameter("te", te);
			result = (Long) query.getSingleResult();
			if (result.intValue() == 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			throw new AppException("DB Exception: " + ex.getMessage());
		}
	}
}

package service;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

import exceptions.AppException;
@Service("playValidator")
public class DefaultPlayValidator implements PlayValidator {
    
	@Override
	public void validate(String playName, String startDate, String startTime,
			String endTime,String price) throws AppException {
		String message=new String("Validator Exception:\n\n");
		if(playName.equals(""))
			message+=new String("Name can't be null\n");
		if(startTime.equals(""))
			message+=new String("Start Time can't be null\n");
		if(endTime.equals(""))
			message+=new String("End Time can't be null\n");
		if(startDate.equals(""))
			message+=new String("Start Date can't be null\n");
		try{
			if(!startTime.equals("")&&startTime!=null)
			Time.valueOf(startTime);
			if(!endTime.equals("")&&endTime!=null)
			Time.valueOf(endTime);
		}catch(java.lang.IllegalArgumentException ex){
			message+=new String("Invalid time format\n");
		}
		try{
			if(!startDate.equals("")&&startDate!=null){
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.parse(startDate);
			}
		}catch(ParseException ex){
			message+=new String("Invalid start date format\n");
		}
		if(price!=null && !price.equals("")){
			int priceTicket=Integer.parseInt(price);
			if(priceTicket<0)
				message+=new String("Negative Ticket\n");
			}
		if(!message.equals("Validator Exception:\n\n"))
		throw new AppException(message);
	}

}

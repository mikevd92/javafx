package service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Service;
import exceptions.AppException;
@Service("userValidator")
public class DefaultUserValidator implements UserValidator {

	@Override
	public void validate(String name, String pass, String address, String dob)
			throws AppException {
		String message=new String("Validator Exception:\n\n");
		if(name.equals("") || name==null)
			message+=new String("Name can't be null\n");
		if(pass.equals("")|| pass==null)
			message+=new String("Password can't be null\n");
		if(address.equals("") || address==null)
			message+=new String("Address can't be null\n");
		if(dob.equals("")|| dob==null)
			message+=new String("DOB can't be null\n");
		try{
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.parse(dob);
		}catch(ParseException ex){
			message+=new String("Invalid birth date format\n");
		}
		if(!message.equals("Validator Exception:\n\n"))
		throw new AppException(message);
	}

	@Override
	public void validate(String name, String pass) throws AppException {
			// TODO Auto-generated method stub
		String message=new String("Validator Exception:\n\n");
			if(name.equals("") || name==null)
				message+=new String("Name can't be null");
			if(pass.equals("")|| pass==null)
				message+=new String("Password can't be null");
			if(!message.equals("Validator Exception:\n\n"))
				throw new AppException(message);
	}

}

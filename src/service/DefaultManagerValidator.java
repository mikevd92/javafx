package service;

import org.springframework.stereotype.Service;
import exceptions.AppException;

@Service("mngValidator")
public class DefaultManagerValidator implements ManagerValidator {

	@Override
	public void validate(String name, String pass) throws AppException {
		String message=new String("Validator Exception:\n\n");
		if(name.equals("") || name==null)
			message+=new String("Name can't be null\n");
		if(pass.equals("")|| pass==null)
			message+=new String("Password can't be null\n");
		if(!message.equals("Validator Exception:\n\n"))
		throw new AppException(message);
	}
}

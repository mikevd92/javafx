package persistence;

import exceptions.AppException;
import model.User;

public interface UserDAO {
	User findUserByNameAndPass(String name,String pass) throws AppException;
	void createUser(String name,String pass,String address,String dbo) throws AppException;
	User findUserByName(String name) throws AppException;
}

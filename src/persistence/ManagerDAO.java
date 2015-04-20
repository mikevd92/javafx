package persistence;

import exceptions.AppException;
import model.Manager;

public interface ManagerDAO {
	public Manager findManagerByNameAndPass(String name,String pass) throws AppException;
}

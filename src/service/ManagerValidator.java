package service;

import exceptions.AppException;

public interface ManagerValidator {
	public void validate(String name,String pass)throws AppException;
}

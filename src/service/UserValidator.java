package service;

import exceptions.AppException;

public interface UserValidator {
	public void validate(String name, String pass, String address, String dob) throws AppException;
	public void validate(String name,String pass)throws AppException;
}

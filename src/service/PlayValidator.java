package service;

import exceptions.AppException;
public interface PlayValidator {
	public void validate(String playName, String startDate,String startTime, String endTime,String ticketPrice) throws AppException;
}

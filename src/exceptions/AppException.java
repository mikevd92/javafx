package exceptions;

public class AppException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	public AppException(String message){
		this.message=message;
	}
	public String getMessage(){
		return this.message;
	}
}

package presentation;

import org.springframework.beans.factory.annotation.Autowired;





import exceptions.AppException;
import service.UserService;
import service.UserValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
/**
 * FXML Controller class
 *
 * @author Mihai
 */
public class DefaultSignUpController extends ImpControlledScreen  {

	@Autowired private UserService userService;
	@Autowired private UserValidator userValidator;
	@FXML private PasswordField passwordField;
	@FXML private TextField userNameField;
	@FXML private TextField addressField;
	@FXML private TextField DOBField;
	
    /**
     * Initializes the controller class.
     */
    @FXML
   public void signUp(ActionEvent event){
    	String name=userNameField.getText();
    	String pass=passwordField.getText();
    	String address=addressField.getText();
    	String dob=DOBField.getText();
		try {
			userValidator.validate(name, pass, address, dob);
			userService.addUser(name, pass, address, dob);
			this.screensController.setScreen(FXApp.LoginID);
		} catch (AppException e) {
			Alert alert=new Alert(AlertType.ERROR);
			alert.setHeaderText("Application Exception");
			alert.setContentText(e.getMessage());
			alert.show();
		}
    }
    @FXML
    public void logIn(ActionEvent event){
    	stage.setTitle("Login");
        stage.setHeight(200);
        stage.setWidth(400);
    	this.screensController.setScreen(FXApp.LoginID);
    }
}

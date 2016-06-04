package FXPresentation;

import org.springframework.beans.factory.annotation.Autowired;

import exceptions.AppException;
import service.ManagerService;
import service.ManagerValidator;
import service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * FXML Controller class
 *
 * @author Angie
 */
public class DefaultLoginController extends ImpControlledScreen  {

    /**
     * Initializes the controller class.
     */
	@Autowired private UserService userService;
	@Autowired private ManagerService mngService;
	@Autowired private ManagerValidator mngValidator;
	@FXML private PasswordField passwordField;
	@FXML private TextField userField;	
	@FXML
    public void signIn(ActionEvent event){
    	String password = passwordField.getText();
		String name = userField.getText();
		boolean type = false;
		try {
			this.mngValidator.validate(name, password);
			type = this.mngService.checkManager(name, password);
			if (type == true) {
				stage.setTitle("Manager: "+name);
				stage.setHeight(540);
				stage.setWidth(900);
				this.screensController.setScreen(ScreenLoader.ManagerID);
				this.screensController.unloadScreen(ScreenLoader.LoginID);
				this.screensController.unloadScreen(ScreenLoader.SignUpID);
				this.screensController.unloadScreen(ScreenLoader.UserID);
			}
			} catch (AppException e) {
				try {
					this.mngValidator.validate(name, password);
					type=this.userService.checkUser(name, password);
					if (type == true) {
						stage.setTitle("User: "+name);
						stage.setHeight(540);
						stage.setWidth(1130);
						this.screensController.setScreen(ScreenLoader.UserID);
						DefaultUserController userController=(DefaultUserController)this.screensController.getController(ScreenLoader.UserID);
						userController.setName(name);
						this.screensController.unloadScreen(ScreenLoader.LoginID);
						this.screensController.unloadScreen(ScreenLoader.ManagerID);
						this.screensController.unloadScreen(ScreenLoader.SignUpID);
					}
				} catch (AppException e1) {
					Alert alert=new Alert(AlertType.ERROR);
					alert.setHeaderText("Unable to Login");
					alert.setContentText(e1.getMessage());
					alert.show();
					stage.setTitle("Sign Up");
					stage.setHeight(270);
					stage.setWidth(430);
					this.screensController.setScreen(ScreenLoader.SignUpID);
				}
		}
		userField.setText("");
		passwordField.setText("");
    } 
}

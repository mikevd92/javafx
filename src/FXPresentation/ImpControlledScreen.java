package FXPresentation;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;

public class ImpControlledScreen implements ControlledScreen {

	protected ScreensController screensController;
	protected Stage stage;
	public void setScreenParent(ScreensController screenParent) {
	    this.screensController=screenParent;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	public Stage getStage(){
		return stage;
	}
	public void setStage(Stage stage){
		this.stage=stage;
	}
}

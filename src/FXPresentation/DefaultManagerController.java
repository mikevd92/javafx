package FXPresentation;

import model.Play;
import org.springframework.beans.factory.annotation.Autowired;
import exceptions.AppException;
import service.ManagerService;
import service.PlayValidator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;

/**
 * FXML Controller class
 *
 * @author Mihai
 */
public class DefaultManagerController extends ImpControlledScreen implements ManagerController{
	
	@Autowired private ManagerService mngService;
	@Autowired private PlayValidator playValidator;
    @FXML private TextField playNameField; 
    @FXML private TextField startDateField; 
    @FXML private TextField startTimeField; 
    @FXML private TextField endTimeField;
    @FXML private TextField ticketPriceField; 
    @FXML private TableView<Play> managerView;
   
    /**
     * Initializes the controller class.
     */
    @FXML public void addPlay(ActionEvent e){
		try {
			String playName=playNameField.getText();
	    	String startDate=startDateField.getText();
	    	String startTime=startTimeField.getText();
	    	String endTime=endTimeField.getText();
	    	String ticketPrice=ticketPriceField.getText();
			this.playValidator.validate(playName, startDate, startTime, endTime,ticketPrice);	
			this.mngService.addPlay(playName, startDate, startTime, endTime, ticketPrice);
		} catch (AppException e1) {
			Dialogs.showErrorDialog(stage, e1.getMessage());
		}
    }
    @FXML public void removePlay(ActionEvent e){
    	    int dbIndex;
			try {
				TableViewSelectionModel<Play> selectionModel = managerView.getSelectionModel();
				if(selectionModel.getSelectedItem()!=null){
					Play play=selectionModel.getSelectedItem();
		        dbIndex=play.getIdPlay();
				this.mngService.removePlay(dbIndex);
				}else
		          throw new AppException("Controller Exception: No Play selected");  
			}catch (AppException e1) {
				Dialogs.showErrorDialog(stage, e1.getMessage());
			}
    }
    @FXML public void updatePlay(ActionEvent e){
    	try {
    		String playName=playNameField.getText();
        	String startDate=startDateField.getText();
        	String startTime=startTimeField.getText();
        	String endTime=endTimeField.getText();
        	String ticketPrice=ticketPriceField.getText();
    		TableViewSelectionModel<Play> selectionModel = managerView.getSelectionModel();
    		if(selectionModel.getSelectedItem()!=null){
	        int dbIndex = selectionModel.getSelectedItem().getIdPlay();
			this.playValidator.validate(playName, startDate, startTime, endTime,ticketPrice);
			this.mngService.updatePlay(dbIndex,playName, startDate, startTime, endTime, ticketPrice);
			refresh();
        	}else
             throw new AppException("Controller Exception: No Play selected");  
		} catch (AppException e1) {
			Dialogs.showErrorDialog(stage, e1.getMessage());
		}
    }
	public ObservableList<Play> displayMPlays() {
		try {
			return this.mngService.displayPlays();
		} catch (AppException e) {
			Dialogs.showErrorDialog(stage, e.getMessage());
		}
		return null;
	}
	public ObservableList<Play> getItems(){
		return managerView.getItems();
	}
	public ObservableList<Play> refresh() {
		managerView.setItems(displayMPlays());
		return managerView.getItems();
	}
}

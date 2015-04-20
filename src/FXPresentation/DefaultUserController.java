package FXPresentation;

import model.Place;
import model.Play;

import org.springframework.beans.factory.annotation.Autowired;

import exceptions.AppException;
import service.UserService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;

/**
 * FXML Controller class
 *
 * @author Mihai
 */
public class DefaultUserController extends ImpControlledScreen implements UserController {
  
	@Autowired private UserService userService;
	@FXML private TableView<Place> placeView;
	@FXML private TableView<Play> playView;
	private String name;
	private int playIndex;
    /**
     * Initializes the controller class.
     */
    @FXML
	public void makeReservation(ActionEvent e){	
    	String available;
			try {
				TableViewSelectionModel<Place> selectionModel = placeView.getSelectionModel();
				if(selectionModel.getSelectedItem()!=null){
				playIndex = selectionModel.getSelectedItem().getPlay().getIdPlay();
		        int dbIndex = selectionModel.getSelectedItem().getIdPlace();
		        available=placeView.getSelectionModel().getSelectedItem().getAvailability();
		        userService.changeAvailability(dbIndex, available,name);
				}else
		             throw new AppException("Controller Exception: No Seat selected");  
			} catch (AppException e1) {
				Dialogs.showErrorDialog(stage, e1.getMessage());
			}
    }
    @FXML
	public void cancelReservation(ActionEvent e){	
    	String available;
    	String userName;
			try {
				TableViewSelectionModel<Place> selectionModel = placeView.getSelectionModel();
				if(selectionModel.getSelectedItem()!=null){
				playIndex = selectionModel.getSelectedItem().getPlay().getIdPlay();
		        int dbIndex = selectionModel.getSelectedItem().getIdPlace();
		        available=placeView.getSelectionModel().getSelectedItem().getAvailability();
		        userName=placeView.getSelectionModel().getSelectedItem().getName();
		        if(userName!=null&&!userName.equals(name))
		        	throw new AppException("ControllerException: Not your seat");
		        userService.cancelAvailability(dbIndex, available);
				}else
		             throw new AppException("Controller Exception: No Seat selected");  
			} catch (AppException e1) {
				Dialogs.showErrorDialog(stage, e1.getMessage());
			}
    }
	public void refreshSeats() {
		placeView.setItems(findByShowID(playIndex));	 
    }
    public void refreshPlays(ObservableList<Play> data){
    	playView.setItems(data);
    }
	public ObservableList<Place> findByShowID(int index) {
		ObservableList<Place> places = null;
		try {
			playIndex=index;
			places = this.userService.findByShowID(index);
		} catch (AppException e) {
			Dialogs.showErrorDialog(stage, e.getMessage());
		}
		return places;
	}
  	public ObservableList<Play> displayUPlays()  {
  		try {
			return this.userService.displayPlays();
		} catch (AppException e) {
			Dialogs.showErrorDialog(stage, e.getMessage());
		}
		return null;
  	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

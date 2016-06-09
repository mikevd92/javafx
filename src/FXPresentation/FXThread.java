package FXPresentation;

import java.util.Stack;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Place;
import model.Play;

public class FXThread implements Runnable {

	private Integer pos;
	private SpringFxmlLoader loader;
	private static String LoginID = "LoginView.fxml";
	private static String ManagerID = "ManagerView.fxml";
	private static String SignUpID = "SignUpView.fxml";
	private static String UserID = "UserView.fxml"; 
	private Stack<ManagerController> mngControllers;
	private Stack<UserController> userControllers;
	public FXThread(SpringFxmlLoader loader,Stack<ManagerController> mngControllers,Stack<UserController> userControllers,Integer pos){
		this.loader=loader;
		this.pos=pos;
		this.mngControllers=mngControllers;
		this.userControllers=userControllers;
	}
	 @Override
       public void run() {
           Stage stage = new Stage();
           stage.setX(pos);
           stage.setY(pos);

	final ScreensController mainContainer = new ScreensController(); 
	
	ScrollPane screen = (ScrollPane) loader.load(LoginID);
	screen.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	screen.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	
	Pane loadScreen = (Pane) screen.getContent();
	ControlledScreen controler = loader.getController();
	controler.setStage(stage);
	mainContainer.loadScreen(LoginID, loadScreen,controler);
	
	screen =(ScrollPane) loader.load(ManagerID);
	screen.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	screen.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	
	loadScreen = (Pane) screen.getContent();
	final Pane scrn = (Pane) screen.getContent();
	DefaultManagerController mngController=(DefaultManagerController) loader.getController();
	mngControllers.add((ManagerController) mngController);
	
	@SuppressWarnings("unchecked")
	final
	TableView<Play> mngView=(TableView<Play>)loadScreen.getChildren().get(1);
	try {
	mngView.setItems(mngController.displayMPlays());
	} catch (Exception e1) {
	e1.printStackTrace();
	}
	mngView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
		@Override
		public void changed(ObservableValue<? extends Object> arg0,
				Object arg1, Object arg2) {	
		  TableViewSelectionModel<Play> selectionModel = mngView.getSelectionModel();
		  Play play=selectionModel.getSelectedItem();
		  VBox box=(VBox)scrn.getChildren().get(2);
		  if(play!=null){
		  TextField name=(TextField) box.getChildren().get(0);
		  name.setText(play.getPlayName());
		  TextField date=(TextField) box.getChildren().get(1);
		  date.setText(play.getStartDate().toString());
		  TextField stime=(TextField) box.getChildren().get(2);
		  stime.setText(play.getStartTime().toString());
		  TextField etime=(TextField) box.getChildren().get(3);
		  etime.setText(play.getEndTime().toString());
		  TextField price=(TextField) box.getChildren().get(4);
		  price.setText(Integer.toString(play.getTicketPrice()));
		  }
		}
	});    
	mainContainer.loadScreen(ManagerID,loadScreen,controler);
	screen = (ScrollPane) loader.load(SignUpID);
	screen.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	screen.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	loadScreen=(Pane)screen.getContent();
	controler = loader.getController();   
	controler.setStage(stage);
	mainContainer.loadScreen(FXApp.SignUpID,loadScreen,controler);
	
	screen = (ScrollPane) loader.load(UserID);
	screen.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	screen.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	loadScreen=(Pane)screen.getContent();
	
	final ControlledScreen contr = loader.getController();
	DefaultUserController userController=(DefaultUserController) loader.getController();
	userControllers.add(userController);
	@SuppressWarnings("unchecked")
	final
	TableView<Play> userView=(TableView<Play>)loadScreen.getChildren().get(1);
	
	try {
		userView.setItems(userController.displayUPlays());
	} catch (Exception e) {
		e.printStackTrace();
	}
	@SuppressWarnings("unchecked")    
	final
	TableView<Place> view=(TableView<Place>)loadScreen.getChildren().get(3);
	userView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
		@Override
		public void changed(ObservableValue<? extends Object> arg0,
				Object arg1, Object arg2) {	
		  TableViewSelectionModel<Play> selectionModel = userView.getSelectionModel();
			try {
				if(selectionModel.getSelectedItem()!=null)
				view.setItems(((DefaultUserController) contr).findByShowID(selectionModel.getSelectedItem().getIdPlay()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});     
	mainContainer.loadScreen(FXApp.UserID,loadScreen,contr);
	mainContainer.setScreen(FXApp.LoginID);
	// Group root = new Group();
	//root.getChildren().addAll(mainContainer);
	Scene scene = new Scene(mainContainer);
	scene.getStylesheets().add("FXPresentation/stylesheet.css");
	stage.setHeight(200);
	stage.setWidth(400);
	stage.setTitle("Login");
	stage.setResizable(false);
	stage.setScene(scene);
	stage.show();
	} 
}

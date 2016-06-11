package presentation;

import java.util.Stack;
import model.Play;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.ManagerService;
import service.UserService;
import aspects.ObserverPattern.Observer;
import aspects.ObserverPattern.Subject;
import configuration.ControllerConfig;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.application.Platform;
/**
 *
 * @author Mihai
 */
public class FXApp extends Application {
    public static String LoginID = "LoginView.fxml";
    public static String ManagerID = "ManagerView.fxml";
    public static String SignUpID = "SignUpView.fxml";
    public static String UserID = "UserView.fxml"; 
	private static Integer c=0;
	private static ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ControllerConfig.class);
    private static SpringFxmlLoader loader=new SpringFxmlLoader(applicationContext);
    private final Stack<ManagerController> mngControllers=new Stack<ManagerController>();
	private final Stack<UserController> userControllers=new Stack<UserController>();
	private ManagerPlayListener playListener=new ManagerPlayListener();
	private UserPlaceListener placeListener=new UserPlaceListener();
	private static UserService userService=applicationContext.getBean(UserService.class);
	private static ManagerService mngService=applicationContext.getBean(ManagerService.class);
	
	protected class ManagerPlayListener implements Observer{
		
	    private ObservableList<Play> data;
	    private int size=-1;
		@Override
		public void update(Subject subject) {
			for(ManagerController mngController : mngControllers){
				mngController.refresh();
			}
			data=mngControllers.peek().getItems();
			if(size!=-1&&size>data.size()){
				size=data.size();
				for(UserController userController : userControllers){
					userController.refreshPlays(data);
					userController.refreshSeats();
				}
			}else{
				size=data.size();
				for(UserController userController : userControllers){
					userController.refreshPlays(data);
				}
			}
		}	
	}
	protected class UserPlaceListener implements Observer{
		@Override
		public void update(Subject subject) {
			for(UserController userController : userControllers){
				userController.refreshSeats();
			}
		}	
	}
    @Override
    public void start(Stage primaryStage) {
    	mngService.AddObserver(playListener);
    	userService.AddObserver(placeListener);
    	Button start=new Button("Start");
    	Platform.setImplicitExit(false);
    	start.addEventFilter(
                MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {	
                      c++;     
                   	 Platform.runLater(new FXThread(loader,mngControllers,userControllers,30*c));
                    }}
                    );
    	start.setAlignment(Pos.CENTER);
    	Button reset=new Button("Reset");
    	reset.addEventFilter(
                MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
					   c=0;  
					}
    });
    	VBox box=new VBox();
    	box.setSpacing(10);
    	box.setPadding(new Insets(20, 20, 20, 70)); 
    	box.getChildren().add(start);
    	box.getChildren().add(reset);
        StackPane myPane = new StackPane();
        myPane.getChildren().add(box);
        Scene myScene = new Scene(myPane);
        myScene.getStylesheets().add("presentation/stylesheet.css");
        primaryStage.setY(600);
        primaryStage.setX(600);
        primaryStage.setHeight(90);
        primaryStage.setWidth(200);
        primaryStage.setTitle("RMS");
        primaryStage.setResizable(false);
		primaryStage.setScene(myScene); 
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
        	@Override
        	public void handle(final WindowEvent arg0){
        		Platform.exit();
        	}
        });
        primaryStage.show();
   }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

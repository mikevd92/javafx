package presentation;

import java.io.IOException;

import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import org.springframework.context.ApplicationContext;

public class SpringFxmlLoader {
 private FXMLLoader loader=new FXMLLoader();
 private ApplicationContext applicationContext;
 public SpringFxmlLoader(ApplicationContext applicationContext){
	 this.applicationContext=applicationContext ;
 }
 public Object load(String url) {
  try (InputStream fxmlStream = SpringFxmlLoader.class
    .getResourceAsStream(url)) {
   System.err.println(SpringFxmlLoader.class
    .getResourceAsStream(url));
   loader=new FXMLLoader();
   loader.setControllerFactory(new Callback<Class<?>, Object>() {
	    @Override
	    public Object call(Class<?> clazz) {
	     return applicationContext.getBean(clazz);
	    }
	   }); 
   return loader.load(fxmlStream);
  }catch(IOException ioException) {
   throw new RuntimeException(ioException);
  }
 }
 public ControlledScreen getController(){
	return (ControlledScreen) loader.getController();
 }
}
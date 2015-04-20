package FXPresentation;

import javafx.collections.ObservableList;
import model.Play;

public interface UserController {
	public void refreshSeats();
	public void refreshPlays(ObservableList<Play> data);
}

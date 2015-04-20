package FXPresentation;

import javafx.collections.ObservableList;
import model.Play;

public interface ManagerController {
	public ObservableList<Play> getItems();
	public ObservableList<Play> refresh();
}

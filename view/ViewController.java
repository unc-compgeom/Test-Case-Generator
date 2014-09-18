package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TabPane;

public class ViewController {

	@FXML
	// ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML
	// URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML
	// fx:id="types"
	private SplitMenuButton types; // Value injected by FXMLLoader

	@FXML
	// fx:id="resultTabPane"
	private TabPane resultTabPane; // Value injected by FXMLLoader

	@FXML
	void generate(ActionEvent event) {
		System.out.println("generate ");
	}

	@FXML
	// This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert types != null : "fx:id=\"types\" was not injected: check your FXML file 'view.fxml'.";
		assert resultTabPane != null : "fx:id=\"resultTabPane\" was not injected: check your FXML file 'view.fxml'.";

	}
}

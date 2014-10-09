package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class ViewController {

    @FXML
    // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML
    // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    // fx:id="form_points"
    private TextField form_points; // Value injected by FXMLLoader

    @FXML
    // fx:id="form_min"
    private TextField form_min; // Value injected by FXMLLoader

    @FXML
    // fx:id="form_max"
    private TextField form_max; // Value injected by FXMLLoader

    @FXML
    // fx:id="form_types"
    private ChoiceBox<String> form_types; // Value injected by FXMLLoader

    @FXML
    // fx:id="resultTabPane"
    private TabPane resultTabPane; // Value injected by FXMLLoader

    @FXML
    void form_generate(ActionEvent event) {
        int numPoints = Integer.parseInt(form_points.getText());
        int min = Integer.parseInt(form_min.getText());
        int max = Integer.parseInt(form_max.getText());

        // TODO fork a child thread to do this

        String type = form_types.getValue();

        Tab tab = new Tab("Results " + type);
        ListView<String> results = new ListView<String>();
        tab.setContent(results);
        ObservableList<String> resultContent = FXCollections
                .observableArrayList();
        resultTabPane.getTabs().add(tab);
        resultTabPane.getSelectionModel().select(tab);

        // populate the result list
        results.setItems(resultContent);
        if (type.equals("RED BLUE")) {
            redBlue.Generate.generate(resultContent, numPoints, min, max);
            try {
                redBlue.Writer.write("redblue_" + numPoints + ".txt",
                        resultContent);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (type.equals("RED BLUE POLYGON")) {
            redBluePolygon.Generate.generate(resultContent, numPoints, min, max);
            try {
                redBluePolygon.Writer.write("redbluepolygon_" + numPoints + ".txt",
                        resultContent);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            random.Generate.random(resultContent, numPoints, min, max);
        }
        System.out.println("done");
    }

    @FXML
    void save(ActionEvent event) {
        // TODO
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert form_points != null : "fx:id=\"form_points\" was not injected: check your FXML file 'view.fxml'.";
        assert form_min != null : "fx:id=\"form_min\" was not injected: check your FXML file 'view.fxml'.";
        assert form_max != null : "fx:id=\"form_max\" was not injected: check your FXML file 'view.fxml'.";
        assert form_types != null : "fx:id=\"form_types\" was not injected: check your FXML file 'view.fxml'.";
        assert resultTabPane != null : "fx:id=\"resultTabPane\" was not injected: check your FXML file 'view.fxml'.";

        // TODO enum for implemented types
        String[] types = {"RED BLUE", "RANDOM", "RED BLUE POLYGON"};
        form_types.getItems().addAll(types);
    }
}
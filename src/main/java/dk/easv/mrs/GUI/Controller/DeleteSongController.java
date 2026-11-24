package dk.easv.mrs.GUI.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeleteSongController {

    @FXML
    private TextField SearchToDelete;

    @FXML
    private ListView<String> lstSongs;

    @FXML
    private Button btnConfirm, btnCancel;


    @FXML
    public void initialize() {

    }

    @FXML
    private void handleConfirm(ActionEvent event) {

    }


}
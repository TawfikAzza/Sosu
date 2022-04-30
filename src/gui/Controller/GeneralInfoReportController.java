package gui.Controller;

import be.GeneralInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralInfoReportController implements Initializable {

    @FXML
    private ListView<GeneralInfo> generalInfoList;

    @FXML
    private void selectGeneralInfo(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

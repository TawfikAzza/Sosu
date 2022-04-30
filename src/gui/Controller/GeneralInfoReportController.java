package gui.Controller;

import be.InfoCategory;
import bll.exceptions.CitizenException;
import gui.Model.GInfoModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GeneralInfoReportController implements Initializable {

    @FXML
    private ListView<InfoCategory> generalInfoList;

    GInfoModel generalInfoModel;

    public GeneralInfoReportController() {
        try {
            generalInfoModel = new GInfoModel();
        } catch (CitizenException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectGeneralInfo(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCategories();
    }

    private void loadCategories() {
        try {
            List<InfoCategory> categoryList = generalInfoModel.getGInfoCategories();
            generalInfoList.setItems(FXCollections.observableArrayList(categoryList));
        } catch (CitizenException e) {
            e.printStackTrace();
        }
    }
}

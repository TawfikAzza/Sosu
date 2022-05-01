package gui.Controller;

import be.Ability;
import be.AbilityCategory;
import be.Citizen;
import bll.exceptions.CitizenReportException;
import bll.exceptions.HealthCategoryException;
import gui.Model.ReportModel;
import gui.utils.DisplayMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class FunctionalReportViewController implements Initializable {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBoxContent;

    private ReportModel reportModel;
    Citizen currentCitizen;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reportModel = new ReportModel();
        } catch (HealthCategoryException e) {
            DisplayMessage.displayError(e);
        }
    }

    public void setCurrentCitizen(Citizen currentCitizen) {
        this.currentCitizen = currentCitizen;
        displayCitizenReport();
    }
    public void displayCitizenReport() {
        HashMap<Integer, List<Pair<AbilityCategory, Ability>>> hashMap = null;
        HashMap<Integer,AbilityCategory> categoryHashMap = new HashMap<>();
        try {
            hashMap = reportModel.getAbilitiesFromCitizen(currentCitizen);
            categoryHashMap = reportModel.getAllFAMainCategories();
        } catch (CitizenReportException e) {
            DisplayMessage.displayError(e);
        }


        for (Map.Entry<Integer, List<Pair<AbilityCategory, Ability>>> entry : hashMap.entrySet()) {
            Integer sid = (Integer) entry.getKey();
            List<Pair<AbilityCategory,Ability>> list = (List<Pair<AbilityCategory,Ability>>)entry.getValue();
            VBox vBox = new VBox();
            Label mainCat = new Label();
            mainCat.setStyle("-fx-font-weight: bold");
            vBox.getChildren().add(mainCat);
            mainCat.setText(categoryHashMap.get(sid).getName());
            for (Pair<AbilityCategory,Ability> pair : list){
                Label subCat = new Label();
                subCat.setText(pair.getKey().getName()+" "+pair.getValue().getScore());
                vBox.getChildren().add(subCat);
            }
            vBoxContent.getChildren().add(vBox);
        }
        scrollPane.setContent(vBoxContent);
    }
}

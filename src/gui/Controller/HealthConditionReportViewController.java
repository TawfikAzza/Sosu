package gui.Controller;

import be.*;
import bll.exceptions.CitizenReportException;
import bll.exceptions.HealthCategoryException;
import gui.Model.ReportModel;
import gui.utils.DisplayMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class HealthConditionReportViewController implements Initializable {
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
        HashMap<Integer, List<Pair<HealthCategory, Condition>>> hashMap = null;
        HashMap<Integer,HealthCategory> categoryHashMap = new HashMap<>();
        try {
            hashMap = reportModel.getConditionsFromCitizen(currentCitizen);
            categoryHashMap = reportModel.getAllConditionsMainCategories();
        } catch (CitizenReportException e) {
            DisplayMessage.displayError(e);
        }


        for (Map.Entry<Integer, List<Pair<HealthCategory, Condition>>> entry : hashMap.entrySet()) {
            Integer sid = (Integer) entry.getKey();
            List<Pair<HealthCategory,Condition>> list = (List<Pair<HealthCategory,Condition>>)entry.getValue();
            VBox vBox = new VBox();
            Label mainCat = new Label();
            mainCat.setStyle("-fx-font-weight: bold");
            vBox.getChildren().add(mainCat);
            mainCat.setText(categoryHashMap.get(sid).getName());
            for (Pair<HealthCategory,Condition> pair : list){
                TextFlow textFlow;
                Text text = new Text();
                text.setText(pair.getKey().getName()+": \n "
                        +pair.getValue().getDescription()
                        +"\n "+pair.getValue().getFreeText()
                        +"\n "+pair.getValue().getGoal()
                        +"\n Status : "+pair.getValue().getStatus());


               // vBox.getChildren().add(subCat);
                textFlow = new TextFlow(text);
                vBox.getChildren().add(textFlow);
                System.out.println(categoryHashMap.get(sid)+" "+pair.getKey().getName()+" "+pair.getValue().getDescription());
            }
            vBoxContent.getChildren().add(vBox);
        }
        scrollPane.setContent(vBoxContent);
    }
}
